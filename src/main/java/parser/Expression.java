
package parser;

import exceptions.MismatchException;
import exceptions.UnexpectedException;
import intermediate.expression.*;
import lexer.Lexer;
import lexer.Token;
import persistent.Terms;

import java.util.ArrayList;

public abstract class Expression extends Parser{

    public Expression(Lexer lexer) {
        super(lexer);
    }

    protected void expression() {
		if( isString() )
			throw new MismatchException(lookahead, "cannot assign strings.");
		term();
    	while ( isAddOp() ) 
    		expNode();
    }
    
    private void term() {
    	factor();
    	while ( isMulOp() )
    		expNode();
    }
    
    private void factor() {
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
    
	private void expNode() {
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
	
	protected abstract Unknown getUnk(Token token);

	protected abstract ArrayList<ExprNode> getArgs();

}
