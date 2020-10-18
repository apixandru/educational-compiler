package test;

import compiler.SeaCompiler;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestCompiler {
    private String output;

    @Test
    public void funTypeAfter() throws Exception {
        output = SeaCompiler.debug("tests/parser/funTypeAfter.sea");
        assertEquals("14\n", output);
    }

    @Test
    public void scope() throws Exception {
        output = SeaCompiler.debug("tests/parser/scope.sea");
        assertEquals("487074\n", output);
    }

    @Test
    public void ignoreCom() throws Exception {
        output = SeaCompiler.debug("tests/parser/ignoreCom.sea");
        assertEquals("0\n", output);
    }

    @Test
    public void multiScope() throws Exception {
        output = SeaCompiler.debug("tests/parser/multiScope.sea");
        assertEquals("12ah10\n", output);
    }

    @Test
    public void threeArguments() throws Exception {
        output = SeaCompiler.debug("tests/parser/threeArguments.sea");
        assertEquals("The sum is 38\n", output);
    }

    @Test
    public void dof() throws Exception {
        output = SeaCompiler.debug("tests/control/do.sea");
        assertEquals("01234\n", output);
    }

    @Test
    public void breakf() throws Exception {
        output = SeaCompiler.debug("tests/control/break.sea");
        assertEquals("0\n", output);
    }

    @Test
    public void loopf() throws Exception {
        output = SeaCompiler.debug("tests/control/loop.sea");
        assertEquals("01234\n", output);
    }

    @Test
    public void repeatf() throws Exception {
        output = SeaCompiler.debug("tests/control/repeat.sea");
        assertEquals("012345678910\n", output);
    }

    @Test
    public void forf() throws Exception {
        output = SeaCompiler.debug("tests/control/for.sea");
        assertEquals("123432112343211234321\n", output);
    }

    @Test
    public void whilef() throws Exception {
        output = SeaCompiler.debug("tests/control/while.sea");
        assertEquals("0-1-2-3-4-3-2-1\n", output);
    }

    @Test
    public void or() throws Exception {
        output = SeaCompiler.debug("tests/expression/or.sea");
        assertEquals("00101\n", output);
    }

    @Test
    public void and() throws Exception {
        output = SeaCompiler.debug("tests/expression/and.sea");
        assertEquals("10111\n", output);
    }

    @Test
    public void not() throws Exception {
        output = SeaCompiler.debug("tests/expression/not.sea");
        assertEquals("1000\n", output);
    }

    @Test
    public void greaterEqual() throws Exception {
        output = SeaCompiler.debug("tests/expression/greaterEqual.sea");
        assertEquals("1000\n", output);
    }

    @Test
    public void equal() throws Exception {
        output = SeaCompiler.debug("tests/expression/equal.sea");
        assertEquals("1000\n", output);
    }


    @Test
    public void notEqual() throws Exception {
        output = SeaCompiler.debug("tests/expression/notEqual.sea");
        assertEquals("0111\n", output);
    }

    @Test
    public void greater() throws Exception {
        output = SeaCompiler.debug("tests/expression/greater.sea");
        assertEquals("1011\n", output);
    }

    @Test
    public void emptyFunction() throws Exception {
        output = SeaCompiler.debug("tests/parser/emptyFunction.sea");
        assertEquals("1241\n", output);
    }

    @Test
    public void global() throws Exception {
        output = SeaCompiler.debug("tests/parser/global.sea");
        assertEquals("310hello0131\n", output);
    }

    @Test
    public void less() throws Exception {
        output = SeaCompiler.debug("tests/expression/less.sea");
        assertEquals("0111\n", output);
    }

    @Test
    public void lessEqual() throws Exception {
        output = SeaCompiler.debug("tests/expression/lessEqual.sea");
        assertEquals("0100\n", output);
    }

    @Test
    public void mod() throws Exception {
        output = SeaCompiler.debug("tests/expression/mod.sea");
        assertEquals("00011\n", output);
    }

    @Test
    public void div() throws Exception {
        output = SeaCompiler.debug("tests/expression/div.sea");
        assertEquals("1010\n", output);
    }

    @Test
    public void mul() throws Exception {
        output = SeaCompiler.debug("tests/expression/mul.sea");
        assertEquals("11133851200\n", output);
    }

    @Test
    public void sub() throws Exception {
        output = SeaCompiler.debug("tests/expression/sub.sea");
        assertEquals("01-1-31-1414\n", output);
    }

    @Test
    public void add() throws Exception {
        output = SeaCompiler.debug("tests/expression/add.sea");
        assertEquals("211351442\n", output);
    }

    @Test
    public void addTo() throws Exception {
        output = SeaCompiler.debug("tests/expression/addTo.sea");
        assertEquals("36\n", output);
    }

    @Test
    public void subFrom() throws Exception {
        output = SeaCompiler.debug("tests/expression/subFrom.sea");
        assertEquals("32\n", output);
    }

    @Test
    public void assign() throws Exception {
        output = SeaCompiler.debug("tests/expression/assign.sea");
        assertEquals("0-112\n", output);
    }

    @Test
    public void uncalled() throws Exception {
        output = SeaCompiler.debug("tests/parser/uncalled.sea");
        assertEquals("3\n", output);
    }

}
