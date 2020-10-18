package intermediate;

import exceptions.MismatchException;
import exceptions.MissingResourceException;
import generator.NodeVisitor;
import intermediate.expression.Add;
import intermediate.expression.ExprNode;
import intermediate.expression.Unknown;

public class AddTo extends Assign{

	public AddTo(Unknown variable, ExprNode ex) {
		super(variable, new Add(variable,ex), '+');
	}

	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
}
