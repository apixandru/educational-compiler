package intermediate;

import exceptions.MismatchException;
import exceptions.MissingResourceException;
import exceptions.OverrideException;
import generator.NodeVisitor;

import intermediate.expression.ExprNode;

import java.util.ArrayList;
import java.util.List;

import persistent.FunTable;
import persistent.Statements;

import lexer.Token;


public class Function extends BaseNode{
	
	private List<BaseNode> statements;
	private Token token;
	private String alias;
	
	public Function(Token t, String type, List<ExprNode> args) {
		alias = FunTable.define(t, type, args);
		token = t;
		statements = Statements.pop();
	}
	
	public String alias() {
		return alias;
	}
	
	public String name(){
		return token.value();
	}
	
	public List<BaseNode> statements(){
		return statements;
	}
	
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
	
}
