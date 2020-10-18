package intermediate.control;

import generator.NodeVisitor;
import intermediate.AddNode;
import intermediate.Assign;
import intermediate.BaseNode;
import intermediate.expression.Unknown;

import java.util.List;

public class For extends ControlNode{

	private Assign initialize;
	private AddNode change;
	
	public For(Assign init, Unknown test, AddNode change, List<BaseNode> b) {
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