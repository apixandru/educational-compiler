package lexer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import exceptions.MismatchException;


public abstract class Lexer {

	static final char EOF = (char) - 1;
	static FileReader inputStream = null;
    static char c;
    static int line;
    static int character;

    
    
    protected Lexer(String input) throws IOException {
    	try {
            inputStream = new FileReader(input);
            line = 1;
    	    character = 0;
    	    consume();
		} catch (FileNotFoundException e){
			throw new FileNotFoundException("ERROR: could not find " + input );
		}
    }

    static char consume() throws IOException {
    	char temp = c;
        if (c == '\n'){
        	line++;
        	character = 0;
        }
        c = (char) inputStream.read();
        character++;
		return temp;
    }

    static boolean isWhiteSpace() {
    	return ( c == ' ' || c == '\t' || c == '\n');
    }

    static boolean isNum() {
    	return Character.isDigit(c);
	}

    static boolean isAlpha() {
    	return Character.isLetter(c);
	}

    static boolean isAlNum() {
    	return Character.isLetterOrDigit(c);
    }

    static void whiteSpace() throws IOException {
        while (isWhiteSpace())
        	consume();
    }

    static void match(char c) throws MismatchException, IOException {
    	if (Lexer.c == c)
    		consume();
    	else
    		throw new MismatchException(("Error " + line + "," + character +
    				": Expecting '" + c + "', found '" + Lexer.c + "'."));
    }
    
}
