package intermediate.control;

import generator.NodeVisitor;
import intermediate.BaseNode;
import intermediate.expression.ExprNode;

import java.util.List;

public class While extends ControlNode{

	public While(ExprNode n, List<BaseNode> b) {
		super(n, b);
	}
	
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}

}
