package intermediate.control;

import java.util.ArrayList;

import exceptions.MismatchException;
import exceptions.MissingResourceException;

import generator.NodeVisitor;
import intermediate.AddNode;
import intermediate.Assign;
import intermediate.BaseNode;
import intermediate.expression.Unknown;

public class For extends ControlNode{

	private Assign initialize;
	private AddNode change;
	
	public For(Assign init, Unknown test, AddNode change, ArrayList<BaseNode> b) {
		super(test, b);
		initialize = init;
		this.change = change;
	}
	
	public Assign variable(){
		return initialize;
	}

	public AddNode update(){
		return change;
	}
	
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
}