package intermediate.expression;

import exceptions.MismatchException;
import exceptions.MissingResourceException;

import generator.NodeVisitor;


public class Multiply extends ExprNode{
	public Multiply(ExprNode left, ExprNode right) throws MismatchException{
		super(null);
		ln = left;
		rn = right;
		cleanNode();
		findType("multiply");
	}

	private void cleanNode(){
		if (ln instanceof Multiply){
			boolean success = false;
			try {
				int intLeft = Integer.parseInt(ln.getLeft().toString());
				int intRight = Integer.parseInt(rn.toString());
				rn = ln.getRight();
				ln = new Constant(intLeft*intRight);
				success = true;
			} catch(Exception e){
				success = false;
			}
			if (!success)
				try {
					int intLeft = Integer.parseInt(ln.getRight().toString());
					int intRight = Integer.parseInt(rn.toString());
					ln = ln.getLeft();
					rn = new Constant(intLeft*intRight);
				} catch(Exception e){}
		}
	}
	
	public String toString(){
		return getLeft() + "*" + getRight();
	}
	public String optimize(){
		ExprNode l = getLeft();
		ExprNode r = getRight();
		String left = l.toString();
		String right = r.toString();
		try{
			int intLeft = Integer.parseInt(left);
			int intRight = Integer.parseInt(right);
			return (intLeft * intRight) + "";
		}catch (Exception e){
			if (left.equals("0") || right.equals("0"))
				return "0";
			else if (right.equals("1"))
				return l.optimize();
			else if (left.equals("1"))
				return r.optimize();
			else 
				return toString();
		}
	}
	
	public void accept(NodeVisitor visitor) throws MissingResourceException, MismatchException{
		visitor.visit(this);
	}

}
