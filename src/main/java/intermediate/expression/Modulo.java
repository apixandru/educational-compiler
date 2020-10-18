package intermediate.expression;

import exceptions.MismatchException;
import exceptions.MissingResourceException;

import generator.NodeVisitor;


public class Modulo extends ExprNode{
	public Modulo(ExprNode left, ExprNode right) throws MismatchException{
		super(null);
		ln = left;
		if (right.optimize().equals("0"))
			throw new MismatchException("Cannot divide by zero.");
		rn = right;
		findType("modulo");
	}
	
	public String toString(){
		return getLeft() + "%" + getLeft();
	}

	public String optimize(){
		String left = getLeft().toString();
		String right = getRight().toString();
		try{
			int intLeft = Integer.parseInt(left);
			int intRight = Integer.parseInt(right);
			return (intLeft % intRight) + "";
		}catch (Exception e){
			if (left.equals("0") || right.equals("1"))
				return "0";
			else if (left.equals("1"))
				return "1";
			else 
				return toString();
		}
	}
	
	public void accept(NodeVisitor visitor) throws MissingResourceException, MismatchException{
		visitor.visit(this);
	}

}
