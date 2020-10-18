package intermediate.control;

import java.util.ArrayList;

import exceptions.MismatchException;
import exceptions.MissingResourceException;

import generator.NodeVisitor;
import intermediate.Assign;
import intermediate.BaseNode;

public class Do extends ControlNode{

	private Assign change;
	
	public Do(Assign change, ArrayList<BaseNode> b) {
		super(null, b);
		this.change = change;
	}
	
	public Assign change(){
		return change;
	}
	
	public void accept(NodeVisitor visitor) throws MissingResourceException, MismatchException{
		visitor.visit(this);
	}

}
