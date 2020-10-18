package test;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import compiler.Request;
import compiler.SeaCompiler;
import exceptions.*;

import lexer.*;

public class TestLexer{

	Token curToken = null;
	Token testToken = null;

	@Test
	public void noEffect() throws IOException{
		Type.valueOf("STRING");
		Type.values();
	}

	@Test(expected=MismatchException.class)
	public void neverEndingString() throws IOException, MismatchException, UnexpectedException, InterruptedException{
		SeaLexer.setInput(Request.pwd() + "/tests/lexer/badString.sea");
		curToken = SeaLexer.nextToken();
	 }

	@Test(expected=UnexpectedException.class)
	public void matchFail() throws IOException, MismatchException, UnexpectedException, InterruptedException{
		SeaLexer.setInput(Request.pwd() + "/tests/lexer/badTokens.sea");
		curToken = SeaLexer.nextToken();
    }

	@Test(expected=MismatchException.class)
	public void badOperator() throws IOException, MismatchException, UnexpectedException, InterruptedException{
		SeaLexer.setInput(Request.pwd() + "/tests/lexer/badTokens2.sea");
		curToken = SeaLexer.nextToken();
    }

	@Test(expected=FileNotFoundException.class)
	public void noFile() throws IOException, MismatchException, UnexpectedException, InterruptedException{
		SeaLexer.setInput("/noFile");
		curToken = SeaLexer.nextToken();
    }

	@Test(expected=MismatchException.class)
	public void tooShort() throws Exception{
		SeaCompiler.compile("/noFile");
		curToken = SeaLexer.nextToken();
    }

	@Test(expected=MismatchException.class)
	public void lessThan3Letters() throws Exception{
		SeaCompiler.debug("/xx");
		curToken = SeaLexer.nextToken();
    }
	
}
