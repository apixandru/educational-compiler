package lexer;

import exceptions.MismatchException;
import exceptions.UnexpectedException;

import java.io.FileReader;
import java.io.IOException;


public abstract class Lexer {

    private static final char EOF = (char) -1;

    private final FileReader inputStream;

    protected char c;
    protected int line;
    protected int character;

    protected Lexer(String input) throws IOException {
        inputStream = new FileReader(input);
        line = 1;
        character = 0;
        consume();
    }

    char consume() throws IOException {
        char temp = c;
        if (c == '\n') {
            line++;
            character = 0;
        }
        c = (char) inputStream.read();
        character++;
        return temp;
    }

    boolean isWhiteSpace() {
        return Character.isWhitespace(c);
    }

    boolean isNum() {
        return Character.isDigit(c);
    }

    boolean isEof() {
        return c == EOF;
    }

    boolean isAlpha() {
        return Character.isLetter(c);
    }

    boolean isAlNum() {
        return Character.isLetterOrDigit(c);
    }

    void whiteSpace() throws IOException {
        while (isWhiteSpace())
            consume();
    }

    void match(char c) throws MismatchException, IOException {
        if (this.c == c)
            consume();
        else
            throw new MismatchException(("Error " + line + "," + character +
                    ": Expecting '" + c + "', found '" + this.c + "'."));
    }

    public abstract Token nextToken() throws MismatchException, IOException, UnexpectedException;

}
