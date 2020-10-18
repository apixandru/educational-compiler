package intermediate;

import exceptions.MissingResourceException;
import generator.NodeVisitor;


public class Free extends BaseNode{

	private int ammount;
	
	public Free(int ammount) {
		this.ammount = ammount;

	}
	
	public int ammount(){
		return ammount*4;
	}
	
	public void accept(NodeVisitor visitor) throws MissingResourceException{
		visitor.visit(this);
	}
}
