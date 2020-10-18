package intermediate.expression;

import exceptions.MismatchException;
import exceptions.MissingResourceException;

import generator.NodeVisitor;


public class Divide extends ExprNode{
	public Divide(ExprNode left, ExprNode right) throws MismatchException{
		super(null);
		ln = left;
		if (right.optimize().equals("0"))
			throw new MismatchException("Cannot divide by zero.");
		rn = right;
		findType("divide");
	}
	
	

	public String toString(){
		return getLeft() + "/" + getRight();
	}

	public String optimize(){
		ExprNode l = getLeft();
		String left = l.toString();
		String right = getRight().toString();
		try{
			int intLeft = Integer.parseInt(left);
			int intRight = Integer.parseInt(right);
			return (intLeft / intRight) + "";
		}catch (Exception e){
			if (left.equals("0"))
				return "0";
			else if (right.equals("1"))
				return l.optimize();
			else 
				return toString();
		}
	}
	
	public void accept(NodeVisitor visitor) throws MissingResourceException, MismatchException{
		visitor.visit(this);
	}

}
