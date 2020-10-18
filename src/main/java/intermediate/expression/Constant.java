package intermediate.expression;

import exceptions.MissingResourceException;
import generator.NodeVisitor;

public class Constant extends ExprNode{
	
	private final int value;
	
	public Constant(int value){
		super("int");
		this.value = value;
	}
	
	public Constant(String value){
		super("int");
		{
			int i;
			try{
				i=Integer.parseInt(value);
				this.value = i;
			} catch (NumberFormatException e){
				throw new NumberFormatException(value + " is not a valid integer, allowed values are -2147483648 to 2147483647.");
			}
		}
	}
	
	public int value(){
		return value;
	}
	
	public String toString(){
		return String.valueOf(value);
	}
	
	public String optimize(){
		return toString();
	}
	
	public void accept(NodeVisitor visitor) throws MissingResourceException{
		visitor.visit(this);
	}
}
