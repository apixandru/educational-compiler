package intermediate.control;

import intermediate.BaseNode;
import intermediate.expression.ExprNode;

import java.util.List;

public abstract class ControlNode extends BaseNode{
	ExprNode test;
	List<BaseNode> body;
	
	public ControlNode(ExprNode n, List<BaseNode> b) {
		test = n;
		body = b;
	}

	public ExprNode test(){
		return test;
	}
	
	public List<BaseNode> body(){
		return body;
	}
	
}
