package exceptions;

import lexer.Token;

@SuppressWarnings("serial")
public abstract class GenericException extends Exception{
	
	public GenericException(String s){
		super(s);
	}
	
	public GenericException(Token t, String s){
		super("Error " + t.position() + ": " + s);
	}
	
}
