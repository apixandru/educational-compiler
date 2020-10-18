package intermediate.control;

import java.util.ArrayList;

import intermediate.BaseNode;
import intermediate.expression.ExprNode;

public abstract class ControlNode extends BaseNode{
	ExprNode test;
	ArrayList<BaseNode> body;
	
	public ControlNode(ExprNode n, ArrayList<BaseNode> b) {
		test = n;
		body = b;
	}

	public ExprNode test(){
		return test;
	}
	
	public ArrayList<BaseNode> body(){
		return body;
	}
	
}
