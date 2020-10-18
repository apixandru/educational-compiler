package intermediate;

import exceptions.MismatchException;
import exceptions.MissingResourceException;
import generator.NodeVisitor;

public abstract class BaseNode{

	public abstract void accept(NodeVisitor visitor) throws MissingResourceException, MismatchException;

}	
