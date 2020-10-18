package lexer;
import java.io.IOException;

import exceptions.*;
public class SeaLexer extends Lexer{
	
	public static void setInput(String input) throws IOException{
		new SeaLexer(input);
	}
	
	private SeaLexer(String input) throws IOException {
		super(input);
	}

	public static Token nextToken() throws MismatchException, IOException, UnexpectedException{
		while (c != EOF){
			whiteSpace();
			switch(Character.toLowerCase(c)){
			case'"': 
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
			case EOF:
				break;
			default: 
				if (isAlpha())
					return getName();
				else if (isNum())
					return getNumber();
				consume();
				throw new UnexpectedException("Error " + line + ", " + character + ": Unexpected character: '" + c + "'.");
			}
		}
		return newToken("EOF", Type.EOF);
	}
	
	private static Token getNumber() throws IOException{
		String num = "";
		while (isNum())
			num += consume();
		return newToken(num, Type.NUMBER);
	}
	
	private static Token getString() throws IOException, MismatchException{
		String theString = "";
		char last = consume();
		while (c != EOF){
			if (c == '"' && last != '\\'){
				consume();
				return newToken(theString, Type.STRING);
			}
			last = consume();
			theString += last;
		}
		throw new MismatchException("Reached EOF and still no ending string character");
	}
	
	private static Token getName() throws IOException{
		String name = "" + consume();
		while (isAlNum())
			name += consume();
		switch (name){
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
	
	private static Token getOperator() throws IOException, MismatchException, UnexpectedException{
		String op = "" + consume();
		switch (op){
		case "+":
			if (c == '=')
				op += consume();
			break;
		case "!":
			if (c == '=')
				op += consume();
			break;
		case "-":
			if (c == '=')
				op += consume();
			break;
		case "=":
			if (c == '=')
				op += consume();
			break;
		case ">":
			if (c == '=')
				op += consume();
			break;
		case "<":
			if (c == '=')
				op += consume();
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
			while(c != '\n')
				consume();
			return nextToken();
		case ";":
			return nextToken();
		case "/":
			if (c == '/'){
				op += consume();
				while(c != '\n')
					consume();
				return nextToken();
			} else if (c == '*'){
				while(true)
					if (consume() == '*')
						if (consume() == '/')
							break;
				return nextToken();
			} else
				break;
		default:
			break;
		}
			return newToken(op, Type.OPERATOR);
	}
	
	private static Token newToken(String s, Type t){
		return new Token(s, t, line, character);
	}
	
	private static Token newToken(char c, Type t){
		return new Token(c, t, line, character);
	}

}
