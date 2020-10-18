package intermediate;

import intermediate.expression.Call;
import intermediate.expression.ExprNode;

import java.util.ArrayList;

import exceptions.MismatchException;
import exceptions.MissingResourceException;
import exceptions.UnexpectedException;


import lexer.Token;
import generator.NodeVisitor;

public class Run extends Call{
	
	public Run(Token t, ArrayList<ExprNode> args) throws UnexpectedException, MismatchException, MissingResourceException{
		super(t, args);
	}
	
	public void accept(NodeVisitor visitor) throws MissingResourceException, MismatchException{
		visitor.visit(this);
	}
}
