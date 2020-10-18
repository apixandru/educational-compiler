package intermediate;

import exceptions.MismatchException;
import exceptions.MissingResourceException;
import intermediate.expression.ExprNode;
import intermediate.expression.Substract;
import intermediate.expression.Unknown;

public class SubFrom extends Assign{

	public SubFrom(Unknown variable, ExprNode ex) throws MismatchException, MissingResourceException{
		super(variable, new Substract(variable,ex), '-');
	}
	
}
