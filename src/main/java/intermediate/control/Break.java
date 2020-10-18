package intermediate.control;

import exceptions.MismatchException;
import exceptions.MissingResourceException;
import generator.NodeVisitor;

public class Break extends ControlNode{

	public Break() {
		super(null, null);
	}

	public void accept(NodeVisitor visitor) throws MissingResourceException, MismatchException{
		visitor.visit(this);
	}

}
