package intermediate.expression;

import exceptions.MissingResourceException;
import generator.NodeVisitor;


public class Unknown extends ExprNode{

	private String variable;
	
	public Unknown(String alias, String type){
		super(type);
		variable = alias;
	}

	public String toString() {
		return variable;
	}

	public String optimize() {
		return toString();
	}

	public String alias() {
		return toString();
	}
	
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
}
