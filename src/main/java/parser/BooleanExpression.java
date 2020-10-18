package parser;

import java.io.IOException;

import lexer.Lexer;
import persistent.Terms;
import exceptions.*;
import intermediate.expression.*;
import lexer.Token;

public abstract class BooleanExpression extends Expression{

	private boolean isBoolean;

	public BooleanExpression(Lexer lexer) {
		super(lexer);
	}

	private boolean isOrOp() {
		return is("||");
	}
	
	private boolean isAndOp() {
		return is("&&");
	}
	
	private boolean isRelOp() {
    	switch ( lookahead().value() ) {
    	case "==":
    	case "!=":
    	case "<":
    	case "<=":
    	case ">":
    	case ">=":
        	return true;
        default: 
        	return false;
    	}
    }
    
    private void relation() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException {
    	isBoolean = false;
    	bool();
    	if ( !isBoolean ){
    		Token t = lookahead();
    		throw new MismatchException(t, "Expecting boolean operator, found '" + t.value() + "'.");
    	}
    }

    private void bool() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException {
    	expression();
    	if ( isRelOp() ){
    		isBoolean = true;
    		createNode( lookahead().value() );
    	}
    }
    
    private void createNode( String value ) throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException {
		consume();
		expression();
		ExprNode ln = Terms.getPenultimate();
    	ExprNode rn = Terms.getLast();
    	ExprNode temp = null;
    	switch ( value ) {
    	case "==": 
    		temp = new Equal( ln, rn ); 
    		break;
		case "!=":
			temp = new NotEqual( ln, rn ); 
			break;
		case "<": 
			temp = new Less( ln, rn ); 
			break;
		case ">": 
			temp = new Greater( ln, rn ); 
			break;
		case "<=": 
			temp = new LessEqual( ln, rn ); 
			break;
		case ">=": 
			temp = new GreaterEqual( ln, rn ); 
			break;
		}
    	addNode( temp );
    }
    
    private void createBoolNode( String value ) {
    	ExprNode ln = Terms.getPenultimate();
    	ExprNode rn = Terms.getLast();
    	ExprNode temp = null;
    	if (value.equals("&&")){ 
    		temp = new And( ln, rn );
    	} else {
    		temp = new Or( ln, rn );
    	}
    	addNode( temp );
    }

    private void notFactor() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException {
    	if ( is("!") ){
    		consume();
    		lBrack();
    		boolExpression();
    		rBrack();
    		ExprNode temp = new Not( Terms.getLast() );
    		addNode( temp, 1 );
    	} else 
    		relation();
    }
	
	private void boolTerm() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException {
    	notFactor();
    	while ( isAndOp() ) {
    		consume();
    		notFactor();
    		createBoolNode("&&");
    	}
    }
    
    protected void boolExpression() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException {
		boolTerm();
		while ( isOrOp() ) {
			consume();
	    	boolTerm();
	    	createBoolNode("||");
		}    
    }
    
    protected ExprNode getExpression() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException{
    	return getAux(false);
    }
    
    protected ExprNode getBoolean() throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException{
    	return getAux(true);
    }
    
    private ExprNode getAux(boolean bool) throws MismatchException, IOException, MissingResourceException, UnexpectedException, OverrideException{
		Terms.push();
		if (bool)
			boolExpression();
		else
			expression();
		ExprNode last = Terms.getLast();
		Terms.pop();
		return last;
	}
    
}
