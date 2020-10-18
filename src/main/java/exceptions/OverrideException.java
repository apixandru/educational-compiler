package exceptions;

import lexer.Token;

public class OverrideException extends SeaException {

    public OverrideException(Token token, String message) {
        super(token, message);
    }

}
