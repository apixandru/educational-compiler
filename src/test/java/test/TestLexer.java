package test;

import compiler.SeaCompiler;
import exceptions.MismatchException;
import exceptions.UnexpectedException;
import lexer.SeaLexer;
import lexer.Token;
import lexer.Type;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TestLexer {

    Token curToken = null;
    Token testToken = null;

    @Test
    public void noEffect() {
        Type.valueOf("STRING");
        Type.values();
    }

    @Test(expected = MismatchException.class)
    public void neverEndingString() throws IOException, MismatchException, UnexpectedException {
        SeaLexer lexer = new SeaLexer("tests/lexer/badString.sea");
        curToken = lexer.nextToken();
    }

    @Test(expected = UnexpectedException.class)
    public void matchFail() throws IOException, MismatchException, UnexpectedException {
        SeaLexer lexer = new SeaLexer("tests/lexer/badTokens.sea");
        curToken = lexer.nextToken();
    }

    @Test(expected = MismatchException.class)
    public void badOperator() throws IOException, MismatchException, UnexpectedException {
        SeaLexer lexer = new SeaLexer("tests/lexer/badTokens2.sea");
        curToken = lexer.nextToken();
    }

    @Test(expected = FileNotFoundException.class)
    public void noFile() throws IOException, MismatchException, UnexpectedException {
        SeaLexer lexer = new SeaLexer("/noFile");
        curToken = lexer.nextToken();
    }

    @Test(expected = MismatchException.class)
    public void tooShort() throws Exception {
        SeaCompiler.compile("/noFile");
    }

    @Test(expected = MismatchException.class)
    public void lessThan3Letters() throws Exception {
        SeaCompiler.debug("/xx");
    }

}
