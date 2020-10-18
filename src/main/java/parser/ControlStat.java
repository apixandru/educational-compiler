package parser;

import intermediate.*;
import intermediate.control.*;
import intermediate.expression.*;

import java.io.IOException;
import java.util.ArrayList;
import persistent.*;
import lexer.*;
import exceptions.*;

public abstract class ControlStat extends BooleanExpression{

	protected void forf() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException {
		match("for");
		lBrack();
		Token variable = lookahead();
		match(Type.IDENTIFIER);
		Unknown name = getUnk(variable);
		match("=");
		ExprNode n = getExpression();
		Assign begin = new Assign(name, n);
		match("to");
		ExprNode end = getExpression();
		Scope.push();
		Unknown condition = new Unknown(Scope.plusOne(), "int");
		Statements.add(new Assign(condition, end));
		match(Type.COMMA);
		forAsOrAd(name);
		AddNode change = Statements.getChange();
		rBrack();
		Statements.push();
		stats();
		ArrayList<BaseNode> forStats = Statements.pop();
		Statements.add(new For(begin, condition, change, forStats));
		Scope.pop();
	}
	
	

	protected void loop() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException {
		match("loop");
		Statements.push();
		stats();
		ArrayList<BaseNode> loopStats = Statements.pop(); 
		Statements.add(new Loop(loopStats));
	}
	
	protected void dof() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException {
		match("do");
		Unknown condition = new Unknown(Scope.plusOne(), "int");
		try{
			Integer.parseInt(lookahead().value());
		} catch (NumberFormatException e){
			throw new MismatchException(lookahead, "Do requires a positive number");
		}
		ExprNode n = new Constant(lookahead().value());
		Assign change = new Assign(condition, n);
		match(lookahead().value());
		Statements.push();
		stats();
		Statements.add(new Decrement(condition));
		ArrayList<BaseNode> doStats = Statements.pop();
		Statements.add(new Do(change, doStats));
	}
	
	protected void whilef() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException {
		Token t = lookahead();
		match("while");
		lBrack();
		ExprNode n = getBoolean();
		rBrack();
		Statements.push();
		stats();
		ArrayList<BaseNode> whileStats = Statements.pop();
		try {
			int temp = Integer.parseInt(n.toString());
			if(temp == 1){
				warning(t, "'While' test condition always resolves to true, converting statment from while to loop");
				Statements.add(new Loop(whileStats));
			} else 
				warning(t, "'While' test condition always resolves to false, removing the while statment."); 
		} catch(Exception e){
			Statements.add(new While(n, whileStats));
		}
	}
	
	protected void iff() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException {
		ArrayList<BaseNode> ifStats = new ArrayList<BaseNode>();
		ArrayList<BaseNode> elseStats = new ArrayList<BaseNode>();
		Token t = lookahead();
		match("if");
		lBrack();
		ExprNode n = getBoolean();
		rBrack();
		Statements.push();
		stats();
		ifStats = Statements.pop();
		if (is("else")){
			Statements.push();
			match("else");
			stats();
			elseStats = Statements.pop();
		}
		try {
			int temp = Integer.parseInt(n.toString());
			if(temp == 1){
				warning(t, "'If' test condition always resolves to true, removing the unnecesairy test condition.");
				Statements.addAll(ifStats);
			}
			else{
				warning(t, "'If' test condition always resolves to false, removing the unnecesairy test condition and the if body.");
				Statements.addAll(elseStats);
			}
		} catch(Exception e){
			Statements.add(new If(n, ifStats, elseStats));
		}
	}
	
	
	protected void repeatf() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException {
		Token t = lookahead();
		match("repeat"); 	
		Statements.push();
		stats();
		ArrayList<BaseNode> repeatStats = Statements.pop();
		match("until");
		lBrack();
		ExprNode n = getBoolean();
		rBrack();
		try {
			int temp = Integer.parseInt(n.toString());
			if(temp == 1){
				warning(t, "'Repeat' test condition always resolves to true, converting statment from repeat to loop");
				Statements.add(new Loop(repeatStats));
			} else {
				warning(t, "'Repeat' test condition always resolves to false, removing the test condition.");
				Statements.addAll(repeatStats);
			}
		} catch(Exception e){
			Statements.add(new Repeat(n, repeatStats));
		}
	}
	
	private void stats() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException {
		if (lookahead().type() == Type.BEGINBLOCK) {
			match(Type.BEGINBLOCK);
			Scope.banLocal();
			block();
			Scope.allowLocal();
			match(Type.ENDBLOCK);
		} else 
			singleStatement();
	}
	
	public void breakf() throws MismatchException, IOException, UnexpectedException  {
    	match("break");
    	Statements.add(new Break());
    }
	
	public void returnf() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException  {
    	match("return");
    	Statements.add(new Return(lookahead, getExpression()));
    }
	protected abstract void block() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException;
	
	protected abstract void singleStatement() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException;
		
	protected abstract void forAsOrAd(Unknown name) throws MismatchException, IOException, UnexpectedException, MissingResourceException;
	
	protected abstract BaseNode createUnk(Token name) throws MismatchException, MissingResourceException, IOException, UnexpectedException, OverrideException;
	
	protected abstract void pushVariable(Token t, String type) throws OverrideException, MissingResourceException;
}
