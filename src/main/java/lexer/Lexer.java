package lexer;

import exceptions.MismatchException;
import exceptions.UnexpectedException;

import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;


public abstract class Lexer implements Closeable {

    private static final char EOF = (char) -1;

    private final Reader inputStream;

    protected char c;
    protected int line;
    protected int character;

    protected Lexer(Reader reader) {
        inputStream = reader;
        line = 1;
        character = 0;
        consume();
    }

    char consume() {
        char temp = c;
        if (c == '\n') {
            line++;
            character = 0;
        }
        c = nextChar();
        character++;
        return temp;
    }

    private char nextChar() {
        try {
            return (char) inputStream.read();
        } catch (IOException ex) {
            throw new UnexpectedException("Failed to read next char!");
        }
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

    void whiteSpace() {
        while (isWhiteSpace()) {
            consume();
        }
    }

    void match(char c) {
        if (this.c == c) {
            consume();
        } else {
            throw new MismatchException(("Error " + line + "," + character +
                    ": Expecting '" + c + "', found '" + this.c + "'."));
        }
    }

    public abstract Token nextToken();

    @Override
    public void close() throws IOException {
        inputStream.close();
    }

}
