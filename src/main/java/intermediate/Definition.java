package intermediate;

import exceptions.MissingResourceException;
import generator.NodeVisitor;


public class Definition extends BaseNode{

	private String name;
	private String value;
	
	public Definition(String name) {
		this.name = name;
		this.value = "$0";

	}
	
	public String alias(){
		return name;
	}

	public String value(){
		return value;
	}
	
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
}
