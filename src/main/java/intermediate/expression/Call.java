package intermediate.expression;


import java.util.ArrayList;

import exceptions.MismatchException;
import exceptions.MissingResourceException;
import exceptions.UnexpectedException;

import persistent.FunBol;
import persistent.FunTable;

import lexer.Token;

import generator.NodeVisitor;

public class Call extends ExprNode{
	private ArrayList<ExprNode> args;
	private Token token;
	private String alias;
	
	public Call(Token token, ArrayList<ExprNode> args) throws UnexpectedException, MismatchException, MissingResourceException{
		super(null);
		this.token = token;
		this.args = args;
		String type = getType(token);
		alias = FunTable.call(token, args, type);
		setType(type);
	}
	
	public String toString(){
		return name() + "()";
	}
	
	public String name(){
		return token.value();
	}
	
	public String alias() throws MissingResourceException{
		return alias;
	}
	
	public ArrayList<ExprNode> args(){
		return args;
	}
	
	public void accept(NodeVisitor visitor) throws MissingResourceException, MismatchException{
		visitor.visit(this);
	}

	public String optimize() {
		return toString();
	}
	
	private String getType(Token t){
		FunBol fb = FunTable.symbols().get(t.value());
		if (fb == null)
			return null;
		return fb.type();
	}
	
}
