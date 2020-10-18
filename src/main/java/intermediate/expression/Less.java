package intermediate.expression;

import exceptions.MismatchException;
import exceptions.MissingResourceException;
import generator.NodeVisitor;

public class Less extends ExprNode{
	
	public Less(ExprNode left, ExprNode right) {
		super(null);
		ln = left;
		rn = right;
		findType("compare");
	}
	
	@Override
	public String toString() {
		return ln.toString() + " < " + rn.toString();
	}

	@Override
	public String optimize() {
		try{
			int l = Integer.parseInt(ln.toString());
			int r = Integer.parseInt(rn.toString());
			if (l < r){
				return "1";
			} else {
				return "0";
			}
		} catch (Exception e) {
			return toString();
		}
	}
	
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}

}
