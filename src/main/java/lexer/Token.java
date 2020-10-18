package lexer;

public class Token {

    private final Type type;
    private final String value;
    private final int line;
    private final int character;

    public Token(String value, Type type, int line, int character) {
        this.value = value;
        this.type = type;
        this.line = line;
        this.character = character - value.length();
    }

    public Type type() {
        return type;
    }

    public String value() {
        return value;
    }

    public String position() {
        return line + ", " + character;
    }

}
