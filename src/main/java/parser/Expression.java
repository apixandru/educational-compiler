
package parser;

import intermediate.expression.Add;
import intermediate.expression.Call;
import intermediate.expression.Constant;
import intermediate.expression.Divide;
import intermediate.expression.ExprNode;
import intermediate.expression.Modulo;
import intermediate.expression.Multiply;
import intermediate.expression.Substract;
import intermediate.expression.Unknown;

import java.io.IOException;
import java.util.ArrayList;

import lexer.Token;
import persistent.Terms;
import exceptions.MismatchException;
import exceptions.MissingResourceException;
import exceptions.OverrideException;
import exceptions.UnexpectedException;


public abstract class Expression extends Parser{
	
	protected void expression() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException {
		if( isString() )
			throw new MismatchException(lookahead, "cannot assign strings.");
		term();
    	while ( isAddOp() ) 
    		expNode();
    }
    
    private void term() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException {
    	factor();
    	while ( isMulOp() )
    		expNode();
    }
    
    private void factor() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException {
    	if ( isAddOp() ) {
    		Terms.add( new Constant( 0 ) );
    		expNode();
    	} else {
    		if ( isLBrack() ) {
    			lBrack();
    			expression();
    			rBrack();
    		} else {
    			if ( isNum() ) {
    				add( new Constant( lookahead().value() ) );
    				consume();
    			} else if ( isID() ){
    				Token t = identifier();
    				if (isLBrack())
    					add( new Call( t, getArgs() ) );
    				else
    					add( getUnk(t) );
    			} else 
    				throw new UnexpectedException(lookahead, "Expression cointains an illegal token:" + lookahead.value());
    		}
    	}
    }
    
	private void expNode() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException {
		String value = lookahead.value();
		consume();
		if (value.equals("+") || value.equals("-"))
			term();
		else 
			factor();
		
    	ExprNode ln = Terms.getPenultimate();
    	ExprNode rn = Terms.getLast();
    	ExprNode temp = null;
    	switch ( value ) {
    	case "+":
			temp = new Add( ln, rn );
			break;
    	case "-":
			temp = new Substract( ln, rn );
			break;
    	case "*":
			temp = new Multiply( ln, rn );
			break;
    	case "/":
			temp = new Divide( ln, rn );
			break;
    	case "%":
			temp = new Modulo( ln, rn );
			break;
		}
    	addNode( temp );
    }
	
	protected abstract Unknown getUnk(Token token) throws MissingResourceException;

	protected abstract ArrayList<ExprNode> getArgs() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException;

}
