package exceptions;

import lexer.Token;

@SuppressWarnings("serial")
public class UnexpectedException extends GenericException{
	
	public UnexpectedException(String message) {
		super(message);
	}
	
	public UnexpectedException(Token token, String message) {
		super(token, message);
	}

}
