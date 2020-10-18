package exceptions;

import lexer.Token;

public class MissingResourceException extends SeaException {

    public MissingResourceException(String message) {
        super(message);
    }

    public MissingResourceException(Token token, String message) {
        super(token, message);
    }

}
