package lexer;

public class Token {
    private Type type;
    private String value;
    private int line;
    private int character;

    public Token(String value, Type type, int line, int character){
    	this.value = value;
    	this.type = type;
    	this.line = line;
    	this.character = character - value.length();
    }
    
    public Token(char value, Type type, int line, int character){
    	this.value = value + "";
    	this.type = type;
    	this.line = line;
    	this.character = character - 1;
    }
    
    public Type type() {
    	return type;
    }
    
    public String value() {
    	return value;
    }
    
    public String position(){
    	return line + ", " + character;
    }
    
}
