package exceptions;

import lexer.Token;

public class MismatchException extends SeaException {

    public MismatchException(String exception) {
        super(exception);
    }

    public MismatchException(Token token, String message) {
        super(token, message);
    }

}
