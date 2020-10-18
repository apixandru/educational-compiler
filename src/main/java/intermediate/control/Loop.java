package intermediate.control;

import java.util.ArrayList;
import java.util.List;

import exceptions.MismatchException;
import exceptions.MissingResourceException;

import generator.NodeVisitor;
import intermediate.BaseNode;

public class Loop extends ControlNode{

	public Loop(List<BaseNode> b) {
		super(null, b);
	}
	
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}

}
