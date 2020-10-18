package lexer;

import exceptions.MismatchException;
import exceptions.UnexpectedException;

import java.io.Reader;

public class SeaLexer extends Lexer {

    public SeaLexer(Reader reader) {
        super(reader);
    }

    public Token nextToken() {
        while (!isEof()) {
            if (isWhiteSpace()) {
                whiteSpace();
                continue;
            }
            switch (Character.toLowerCase(c)) {
                case '"':
                    return getString();
                case '{':
                    return newToken(consume(), Type.BEGINBLOCK);
                case '}':
                    return newToken(consume(), Type.ENDBLOCK);
                case '(':
                    return newToken(consume(), Type.LBRACK);
                case ')':
                    return newToken(consume(), Type.RBRACK);
                case ',':
                    return newToken(consume(), Type.COMMA);
                case '#':
                case '/':
                case '+':
                case '-':
                case '*':
                case '|':
                case '&':
                case '<':
                case '>':
                case '=':
                case '%':
                case ';':
                case '!':
                    return getOperator();
                default:
                    if (isAlpha()) {
                        return getName();
                    } else if (isNum()) {
                        return getNumber();
                    }
                    consume();
                    throw new UnexpectedException("Error " + line + ", " + character + ": Unexpected character: '" + c + "'.");
            }
        }
        return newToken("EOF", Type.EOF);
    }

    private Token getNumber() {
        String num = "";
        while (isNum()) {
            num += consume();
        }
        return newToken(num, Type.NUMBER);
    }

    private Token getString() {
        String theString = "";
        char last = consume();
        while (!isEof()) {
            if (c == '"' && last != '\\') {
                consume();
                return newToken(theString, Type.STRING);
            }
            last = consume();
            theString += last;
        }
        throw new MismatchException("Reached EOF and still no ending string character");
    }

    private Token getName() {
        String name = "" + consume();
        while (isAlNum()) {
            name += consume();
        }
        switch (name) {
            case "int":
            case "string":
            case "void":
                return newToken(name, Type.TYPE);
            case "if":
            case "else":
            case "loop":
            case "for":
            case "while":
            case "repeat":
            case "until":
            case "do":
            case "break":
            case "run":
            case "return":
                return newToken(name, Type.KEYWORD);
            default:
                return newToken(name, Type.IDENTIFIER);
        }
    }

    private Token getOperator() {
        String op = "" + consume();
        switch (op) {
            case "+":
            case "-":
            case "!":
            case "=":
            case ">":
            case "<":
                if (c == '=') {
                    op += consume();
                }
                break;
            case "&":
                op += '&';
                match('&');
                break;
            case "|":
                op += '|';
                match('|');
                break;
            case "#":
                op += consume();
                while (c != '\n') {
                    consume();
                }
                return nextToken();
            case ";":
                return nextToken();
            case "/":
                if (c == '/') {
                    op += consume();
                    while (c != '\n') {
                        consume();
                    }
                    return nextToken();
                } else if (c == '*') {
                    while (true) {
                        if (consume() == '*') {
                            if (consume() == '/') {
                                break;
                            }
                        }
                    }
                    return nextToken();
                } else {
                    break;
                }
            default:
                break;
        }
        return newToken(op, Type.OPERATOR);
    }

    private Token newToken(String s, Type t) {
        return new Token(s, t, line, character);
    }

    private Token newToken(char c, Type t) {
        return new Token(c, t, line, character);
    }

}
