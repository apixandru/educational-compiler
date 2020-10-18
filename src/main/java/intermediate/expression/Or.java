package intermediate.expression;

import exceptions.MismatchException;
import exceptions.MissingResourceException;
import generator.NodeVisitor;

public class Or extends ExprNode{
	
	public Or(ExprNode left, ExprNode right){
		super(null);
		ln = left;
		rn = right;
	}
	
	public String toString() {
		return ln + " || " + rn;
	}

	@Override
	public String optimize() {
		try{
			int l = Integer.parseInt(ln.toString());
			int r = Integer.parseInt(rn.toString());
			if (l == 1 || r == 1){
				return "1";
			} else {
				return "0";
			}
		} catch (Exception e) {
			return toString();
		}
	}
	
	public void accept(NodeVisitor visitor) throws MissingResourceException, MismatchException{
		visitor.visit(this);
	}
}
