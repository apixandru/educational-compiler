package exceptions;

import lexer.Token;

public class UnexpectedException extends SeaException {

    public UnexpectedException(String message) {
        super(message);
    }

    public UnexpectedException(Token token, String message) {
        super(token, message);
    }

}
