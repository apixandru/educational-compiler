package exceptions;

import lexer.Token;

@SuppressWarnings("serial")
public class OverrideException extends GenericException{
	
	public OverrideException(Token token, String message) {
		super(token, message);
	}

}
