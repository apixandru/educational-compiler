package intermediate.control;

import java.util.ArrayList;

import exceptions.MismatchException;
import exceptions.MissingResourceException;

import generator.NodeVisitor;
import intermediate.BaseNode;

public class Loop extends ControlNode{

	public Loop(ArrayList<BaseNode> b) {
		super(null, b);
	}
	
	public void accept(NodeVisitor visitor) throws MissingResourceException, MismatchException{
		visitor.visit(this);
	}

}
