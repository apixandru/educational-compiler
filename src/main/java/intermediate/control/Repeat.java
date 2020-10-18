package intermediate.control;

import java.util.ArrayList;

import exceptions.MismatchException;
import exceptions.MissingResourceException;

import generator.NodeVisitor;
import intermediate.BaseNode;
import intermediate.expression.ExprNode;

public class Repeat extends ControlNode{

	public Repeat(ExprNode test, ArrayList<BaseNode> body) {
		super(test, body);
	}

	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}

}
