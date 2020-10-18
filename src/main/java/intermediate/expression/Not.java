package intermediate.expression;

import exceptions.MismatchException;
import exceptions.MissingResourceException;
import generator.NodeVisitor;

public class Not extends ExprNode{

	private ExprNode expression;
	
	public Not(ExprNode n){
		super(null);
		expression = n;
	}
	
	@Override
	public String toString() {
		return "!(" + expression + ")"; 
	}

	@Override
	public String optimize() {
		try {
			int x = Integer.parseInt(expression.optimize());
			if (x == 1)
				return "0";
			else 
				return "1";
		} catch (Exception e){
			return toString();
		}
	}
	
	public ExprNode expression(){
		return expression;
	}
	
	public void accept(NodeVisitor visitor) throws MissingResourceException, MismatchException{
		visitor.visit(this);
	}

}
