package exceptions;

import lexer.Token;

@SuppressWarnings("serial")
public class MismatchException extends GenericException{
	public MismatchException(String exception) {
		super(exception);
	}
	
	public MismatchException(Token token, String message) {
		super(token, message);
	}
}
