package test;

import compiler.SeaCompiler;
import exceptions.MismatchException;
import exceptions.UnexpectedException;
import lexer.SeaLexer;
import lexer.Type;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

public class TestLexer {

    @Test
    public void noEffect() {
        Type.valueOf("STRING");
        Type.values();
    }

    @Test
    public void neverEndingString() {
        MismatchException ex = assertThrows(MismatchException.class, () -> {
            SeaLexer lexer = newLexer("tests/lexer/badString.sea");
            lexer.nextToken();
        });

        assertThat(ex.getMessage())
                .isEqualTo("Reached EOF and still no ending string character");
    }

    @Test
    public void matchFail() {
        UnexpectedException ex = assertThrows(UnexpectedException.class, () -> {
            SeaLexer lexer = newLexer("tests/lexer/badTokens.sea");
            lexer.nextToken();
        });

        assertThat(ex.getMessage())
                .isEqualTo("Error 1, 2: Unexpected character: '\r'.");
    }

    @Test
    public void badOperator() {
        MismatchException ex = assertThrows(MismatchException.class, () -> {
            SeaLexer lexer = newLexer("tests/lexer/badTokens2.sea");
            lexer.nextToken();
        });

        assertThat(ex.getMessage())
                .isEqualTo("Error 1,2: Expecting '|', found '^'.");
    }

    @Test
    public void noFile() {
        assertThrows(FileNotFoundException.class, () -> {
            SeaLexer lexer = newLexer("/noFile");
            lexer.nextToken();
        });
    }

    @Test
    public void tooShort() {
        MismatchException ex = assertThrows(MismatchException.class, () -> {
            SeaCompiler.compile("/noFile");
        });

        assertThat(ex.getMessage())
                .isEqualTo("ERROR: Filename must have the 'sea' extension");
    }

    @Test
    public void lessThan3Letters() {
        MismatchException ex = assertThrows(MismatchException.class, () -> {
            SeaCompiler.debug("xx");
        });

        assertThat(ex.getMessage())
                .isEqualTo("ERROR: Filename too short");
    }

    private SeaLexer newLexer(String s) throws FileNotFoundException {
        return new SeaLexer(new FileReader(s));
    }

}
