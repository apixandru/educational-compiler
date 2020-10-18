package intermediate;

import exceptions.MismatchException;
import exceptions.MissingResourceException;
import lexer.Token;
import intermediate.expression.ExprNode;
import generator.NodeVisitor;


public class Return extends BaseNode{

	private String varType;
	private ExprNode expression;
	private Token t;
	
	public Return(Token t, ExprNode expression) {
		this.t = t;
		this.expression = expression;
		varType = expression.type();

	}
	
	public String varType(){
		return varType;
	}

	public ExprNode expression(){
		return expression;
	}
	
	public Token token(){
		return t;
	}
	
	public void accept(NodeVisitor visitor) throws MissingResourceException, MismatchException{
		visitor.visit(this);
	}
}
