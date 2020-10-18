package exceptions;

import lexer.Token;

public class SeaException extends RuntimeException {

    public SeaException(String message) {
        super(message);
    }

    public SeaException(Token token, String message) {
        super("Error " + token.position() + ": " + message);
    }

}
