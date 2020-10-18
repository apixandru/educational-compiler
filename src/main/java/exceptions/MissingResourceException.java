package exceptions;

import lexer.Token;

@SuppressWarnings("serial")
public class MissingResourceException extends GenericException{
	
	public MissingResourceException(String message) {
		super(message);
	}
	
	public MissingResourceException(Token token, String message) {
		super(token, message);
	}

}
