package intermediate.control;

import java.util.ArrayList;

import exceptions.MismatchException;
import exceptions.MissingResourceException;

import generator.NodeVisitor;
import intermediate.BaseNode;
import intermediate.expression.ExprNode;

public class While extends ControlNode{

	public While(ExprNode n, ArrayList<BaseNode> b) {
		super(n, b);
	}
	
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}

}
