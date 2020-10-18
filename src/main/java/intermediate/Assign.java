package intermediate;

import persistent.FunTable;
import exceptions.*;
import generator.NodeVisitor;
import intermediate.expression.*;

public class Assign extends AddNode{

	private Unknown variable;
	private ExprNode expression;
	
	public Assign(Unknown variable, ExprNode ex, char sign) throws MismatchException, MissingResourceException{
		super(sign);
		this.variable = variable;
		expression = ex;
		checkType();
	}
	
	public Assign(Unknown variable, ExprNode ex) throws MismatchException, MissingResourceException{
		this(variable, ex, ' ');
	}
	
	private void checkType() throws MismatchException, MissingResourceException{
		String type1 = variable.type();
		String type2 = expression.type();
		if (type2 == null){
			String x = ((Call) expression).name();
			FunTable.setInt(x);
		} else if(!type1.equals(type2)){
			throw new MismatchException("Assign type " + type2 + " to type " + type1 + ".");
		} 
	}
	
	public Unknown variable(){
		return variable;
	}
	
	public ExprNode expression(){
		return expression;
	}
	
	public void accept(NodeVisitor visitor) throws MissingResourceException, MismatchException{
		visitor.visit(this);
	}
}