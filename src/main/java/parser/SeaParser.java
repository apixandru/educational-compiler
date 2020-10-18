package parser;

import exceptions.*;
import generator.Prelude;
import intermediate.*;
import intermediate.expression.*;

import java.io.IOException;
import java.util.ArrayList;

import persistent.*;
import lexer.*;

public class SeaParser extends ControlStat {

	public SeaParser(String s) throws IOException, MismatchException, OverrideException, MissingResourceException, UnexpectedException{
		SeaLexer.setInput(s);
		FunTable.reset();
		Scope.reset();
		Statements.clear();
		parse();
		
	}
	
	private void parse() throws MismatchException, IOException, OverrideException, MissingResourceException, UnexpectedException {
		consume();
		pushScope();
		definitions();
		main();
		EOF();
	}
	
	private void definition() throws MismatchException, UnexpectedException, IOException, OverrideException, MissingResourceException {
	    String type = type();
	    Token name = identifier();
	    if (isLBrack())
	        function( type, name );
	    else
	        variable( type, name );
	}
	
	private void definitions() throws MismatchException, IOException, OverrideException, MissingResourceException, UnexpectedException{
		while (isType())
		    definition();
	}
	
	private void variable(String type, Token t) throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException{
        if (!type.equals("string") && !type.equals("int"))
            throw new MismatchException(lookahead(),"Unsupported return type. Supported Types are 'string' and 'int'.");
        String value = "0";
        String alias;
        if (type.equals("string")){
            match("=");
            value = lookahead().value();
            consume();
            alias = Prelude.addString(value);
            Scope.pushGlobalVar(alias, t, type);
        } else {
            alias = Prelude.addInt(value);
            Scope.pushGlobalVar(alias, t, type);
            if (is("="))
                assign(t);
        }
    }
	
	private void function(String type, Token t) throws MismatchException, IOException, OverrideException, MissingResourceException, UnexpectedException{
       if (!type.equals("void") && !type.equals("int"))
            throw new MismatchException(lookahead(),"Unsupported return type. Supported Types are 'void' and 'int'.");
        pushScope();
        Statements.push();
        ArrayList<ExprNode> args = getDefArgs();
        begin();
        block();
        end();
        popScope(args.size());
        Statements.add(new Function(t, type, args));        
    }
	
	private void assign(Token t) throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException {
		match("=");
		if ( isNum() || isAddOp() || isLBrack() || isID() || isString() )
			Statements.add(createUnk(t));
		else
			throw new UnexpectedException(lookahead, "Unexpected token recieved.");
	}
	
	

	private void EOF() throws MismatchException, IOException, UnexpectedException, MissingResourceException {
		Statements.add(new Call(new Token("exit", null, 0, 0), new ArrayList<ExprNode>()));
		match(Type.EOF);
		if (FunTable.undefined() > 0)
			throw new MissingResourceException("Call to undefined functions:\n" + FunTable.functions);
		else if (FunTable.uncalled() > 0)
			warning("Uncalled functions:\n" + FunTable.functions);
	}

	private void main() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException {
		match("run");
		if ( !isID() )
			throw new MismatchException(lookahead(), "can only run functions");
		Token t = identifier();
		ArrayList<ExprNode> args = getArgs();
		Statements.add(new Run(t, args));
	}

	protected ArrayList<ExprNode> getArgs() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException{
		lBrack();
		ArrayList<ExprNode> args = new ArrayList<ExprNode>();
		
		while ( isNum() || isID() || isAddOp()  || isString() ){
			if ( isNum() || isID() || isAddOp() ){
				args.add(getExpression());
			} else {
				String value = Prelude.addString(lookahead().value());
//				pushVariable(lookahead(), "string", value);
				args.add(new Unknown(value, "string"));
				match(Type.STRING);
			}
			if (!isRBrack())
				match(Type.COMMA);
		}
		rBrack();
		return args;
	}
	
	private ArrayList<ExprNode> getDefArgs() throws MismatchException, IOException, UnexpectedException, OverrideException{
		lBrack();
		int num	 = 2;
		ArrayList<ExprNode> args = new ArrayList<ExprNode>();
		ArrayList<Token> t = new ArrayList<Token>();
		ArrayList<String> ty = new ArrayList<String>();
		ArrayList<Integer> in = new ArrayList<Integer>();
		
		while ( isType() || isComma() ){
			if (args.size() > 0)
				comma();
			String type = type();
			t.add(lookahead());
			ty.add(type);
			in.add(0, num++);
			args.add(new Unknown(identifier().value(), type));			
		}
		num -= 2;
		for (int i = 0; i < num; i++){
			pushVariable(t.get(i), ty.get(i), in.get(i));
		}
//		if (!isRBrack())
//			throw new MismatchException(lookahead, "invalid argument '" + lookahead.value() + "'.");
		rBrack();
		return args;
	}
	
	private void call(Token t) throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException{
		ArrayList<ExprNode> args = getArgs();
		Statements.add(new Call(t, args));
	}

	protected void block() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException {
		Scope.push();
		while ( !is("}") && !is("else"))
			singleStatement();
		Scope.pop();
	}

	protected void singleStatement() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException {
		switch (lookahead().value()) {
		case "if":
			iff();
			break;
		case "while":
			whilef();
			break;
		case "for":
			forf();
			break;
		case "do":
			dof();
			break;
		case "repeat":
			repeatf();
			break;
		case "break":
			breakf();
			break;
		case "loop":
			loop();
			break;
		case "return":
			returnf();
		case "}":
			break;
		case "{":
			consume();
			block();
			end();
			break;
		default:
			if ( isID() )
				asOrAd();
			else if( isType() )
				defType();
			else
				throw new UnexpectedException(lookahead(), "Unexpected token recieved.");
		}
	}
	
	private void defType() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException{
		if (!Scope.allows())
			throw new MismatchException(lookahead, "Can not declare variables inside loops");
		String s = lookahead().value();
		switch(s){
		case "void": 
			throw new MismatchException(lookahead(), "Cannot declare variable of type void.");
		case "string": 
			defString(); 
			break;
		case "int": 
			defInt(); 
			break;
		}		
	}
	
	private void defString() throws MismatchException, IOException, UnexpectedException, MissingResourceException, OverrideException{
		match("string");
		Token var = lookahead();
		match(Type.IDENTIFIER);
		match("=");
		String string = lookahead().value();
		match(Type.STRING);
		String value = Prelude.addString(string);
		pushVariable(var, "string");
		Statements.add(new Assign(getUnk(var), new Unknown(value, "string")));
	}

	private void defInt() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException{
		Token t = lookahead();
		match(Type.TYPE);
		pushVariable(lookahead(), t.value());
		t = lookahead();
		match(Type.IDENTIFIER);
		if (is("=")){
			assign(t);
		}
	}
	
	protected BaseNode createUnk(Token name) throws MismatchException, MissingResourceException, IOException, UnexpectedException, OverrideException{
		return new Assign(getUnk(name), getExpression());
	}
	
	
	protected Unknown getUnk(Token t) throws MissingResourceException{
		return new Unknown(Scope.aliasOf(t), Scope.typeOf(t));
	}

	private void asOrAd() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException {
		Token u = identifier();
		String sign = lookahead.value();
		switch (sign) {
		case "=":
			assign(u);
			return;
		case "+=":
		case "-=":
			consume();
			if ( isNum() || isAddOp() || isLBrack() || isID() ) {
				if (sign.equals("+=")) 
					Statements.add(new AddTo(getUnk(u), getExpression())); 
				else
					Statements.add(new SubFrom(getUnk(u), getExpression()));
			} else
				break;
			return;
		case "+":
			match("+");
			match("+");
			inc(u);
			return;
		case "-":
			match("-");
			match("-");
			dec(u);
				return;
		case "(": 
			call(u);
			return;
		}
		throw new UnexpectedException(lookahead, "Unexpected token recieved.");
	}
	
	private void addStat(Unknown name, ExprNode t, String s) throws MismatchException, MissingResourceException{
		Assign n;
		if (s.equals("+"))
			n = new AddTo(name, t);
		else
			n = new SubFrom(name, t);
		Statements.add(n);
	}
	
	protected void forAsOrAd(Unknown name) throws MismatchException, IOException, UnexpectedException, MissingResourceException {
		String sign = lookahead().value();
		consume();
		if (is(sign)){
			match(sign);
			BaseNode n;
			if (sign.equals("+"))
				n = new Increment(name);
			else
				n = new Decrement(name);
			Statements.add(n);
			return;
		} else if ( isID() ){
			Unknown t = getUnk(lookahead());
			match(Type.IDENTIFIER);
			addStat(name, t, sign);
			return;
		} else if ( isNum() ){
			Constant t = new Constant(lookahead().value());
			match(Type.NUMBER);
			addStat(name, t, sign);
			return;
		}
		throw new UnexpectedException(lookahead, "Unexpected token recieved.");
	}
	
	private void inc(Token t) throws MissingResourceException {
		Statements.add(new Increment(getUnk(t)));
	}

	private void dec(Token t) throws MissingResourceException {
		Statements.add(new Decrement(getUnk(t)));
	}

	private void pushScope(){
		Scope.push();
	}
	
	private void popScope(int size){
		Scope.pop(size);
	}
	
	protected void pushVariable(Token t, String type) throws OverrideException, MissingResourceException{
		Scope.pushVar(t, type);
	}
	
	private void pushVariable(Token t, String type, int alias) throws OverrideException{
		Scope.pushVariable(t, type, alias*4+"(%ebp)");
	}
	
//	private void pushVariable(Token t, String type, String alias) throws OverrideException{
//		
//		Scope.pushVariable(t, type, alias);
//	}
//	
}
