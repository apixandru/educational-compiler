package intermediate;

import exceptions.MissingResourceException;
import generator.NodeVisitor;
import intermediate.expression.Unknown;

public class Increment extends AddNode{
	private Unknown variable;
	public Increment(Unknown t){
		super('+');
		this.variable = t;
	}

	public String variable(){
		return variable.alias();
	}
	
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
}
