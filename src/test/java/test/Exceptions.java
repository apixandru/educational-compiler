package test;

import compiler.SeaCompiler;
import exceptions.MismatchException;
import exceptions.MissingResourceException;
import exceptions.OverrideException;
import exceptions.UnexpectedException;
import org.junit.Test;

public class Exceptions {

    @Test(expected = OverrideException.class)
    public void overrideVar() throws Exception {
        SeaCompiler.debug("tests/exceptions/overrideVar.sea");
    }

    @Test(expected = MismatchException.class)
    public void badGlobal() throws Exception {
        SeaCompiler.debug("tests/exceptions/badGlobalType.sea");
    }

    @Test(expected = MismatchException.class)
    public void varInLoo() throws Exception {
        SeaCompiler.debug("tests/exceptions/varInLoo.sea");
    }

    @Test(expected = NumberFormatException.class)
    public void overflow() throws Exception {
        SeaCompiler.debug("tests/exceptions/overflow.sea");
    }

    @Test(expected = MismatchException.class)
    public void doubleReturn() throws Exception {
        SeaCompiler.debug("tests/exceptions/doubleReturn.sea");
    }

    @Test(expected = MismatchException.class)
    public void assignString() throws Exception {
        SeaCompiler.debug("tests/exceptions/assignString.sea");
    }

    @Test(expected = MismatchException.class)
    public void badRetPos() throws Exception {
        SeaCompiler.debug("tests/exceptions/badRetPos.sea");
    }

    @Test(expected = MissingResourceException.class)
    public void noReturn() throws Exception {
        SeaCompiler.debug("tests/exceptions/noReturn.sea");
    }

    @Test(expected = OverrideException.class)
    public void overrideBuiltIn() throws Exception {
        SeaCompiler.debug("tests/exceptions/overrideBuiltIn.sea");
    }

    @Test(expected = OverrideException.class)
    public void overrideFun() throws Exception {
        SeaCompiler.debug("tests/exceptions/overrideFun.sea");
    }


    @Test(expected = MismatchException.class)
    public void badFunType() throws Exception {
        SeaCompiler.debug("tests/exceptions/badFunType.sea");
    }

    @Test(expected = MismatchException.class)
    public void funArgsMismatch2() throws Exception {
        SeaCompiler.debug("tests/exceptions/funArgsMismatch2.sea");
    }

    @Test(expected = MismatchException.class)
    public void funArgsMismatch() throws Exception {
        SeaCompiler.debug("tests/exceptions/funArgsMismatch.sea");
    }

    @Test(expected = MismatchException.class)
    public void differentFunTypes() throws Exception {
        SeaCompiler.debug("tests/exceptions/differentFunTypes.sea");
    }

    @Test(expected = MismatchException.class)
    public void badArgsCall() throws Exception {
        SeaCompiler.debug("tests/exceptions/badArgsCall.sea");
    }

    @Test(expected = MismatchException.class)
    public void badVarType() throws Exception {
        SeaCompiler.debug("tests/exceptions/badVarType.sea");
    }

    @Test(expected = UnexpectedException.class)
    public void assignEx() throws Exception {
        SeaCompiler.debug("tests/exceptions/assignEx.sea");
    }

    @Test(expected = UnexpectedException.class)
    public void unexpectedStatement() throws Exception {
        SeaCompiler.debug("tests/exceptions/unexpectedStatement.sea");
    }

    @Test(expected = UnexpectedException.class)
    public void badAsOrAdd2() throws Exception {
        SeaCompiler.debug("tests/exceptions/badAsOrAdd2.sea");
    }

    @Test(expected = UnexpectedException.class)
    public void badAsOrAdd() throws Exception {
        SeaCompiler.debug("tests/exceptions/badAsOrAdd.sea");
    }

    @Test(expected = UnexpectedException.class)
    public void badFor() throws Exception {
        SeaCompiler.debug("tests/exceptions/badFor.sea");
    }

    @Test(expected = MismatchException.class)
    public void badDef() throws Exception {
        SeaCompiler.debug("tests/exceptions/badDef.sea");
    }

    @Test(expected = MismatchException.class)
    public void badReturnType() throws Exception {
        SeaCompiler.debug("tests/exceptions/badReturnType.sea");
    }

    @Test(expected = MismatchException.class)
    public void badRun() throws Exception {
        SeaCompiler.debug("tests/exceptions/badRun.sea");
    }

    @Test(expected = MismatchException.class)
    public void badBreak() throws Exception {
        SeaCompiler.debug("tests/exceptions/badBreak.sea");
    }

    @Test(expected = MismatchException.class)
    public void badMatchType() throws Exception {
        SeaCompiler.debug("tests/exceptions/badMatchType.sea");
    }

    @Test(expected = MismatchException.class)
    public void badMatchValue() throws Exception {
        SeaCompiler.debug("tests/exceptions/badMatchValue.sea");
    }

    @Test(expected = MismatchException.class)
    public void badAssign() throws Exception {
        SeaCompiler.debug("tests/exceptions/badAssign.sea");
    }

    @Test(expected = MismatchException.class)
    public void badReturn() throws Exception {
        SeaCompiler.debug("tests/exceptions/badReturn.sea");
    }

    @Test(expected = MismatchException.class)
    public void badDo() throws Exception {
        SeaCompiler.debug("tests/exceptions/badDo.sea");
    }

    @Test(expected = MismatchException.class)
    public void negativeDo() throws Exception {
        SeaCompiler.debug("tests/exceptions/negativeDo.sea");
    }

    @Test(expected = MismatchException.class)
    public void nodeDifferentTypes() throws Exception {
        SeaCompiler.debug("tests/exceptions/badTypeNodes.sea");
    }

    @Test(expected = MismatchException.class)
    public void nodeDifferentTypes2() throws Exception {
        SeaCompiler.debug("tests/exceptions/badTypeNodes2.sea");
    }

    @Test(expected = MismatchException.class)
    public void divisionByZero() throws Exception {
        SeaCompiler.debug("tests/exceptions/divisionByZero.sea");
    }

    @Test(expected = MismatchException.class)
    public void moduloByZero() throws Exception {
        SeaCompiler.debug("tests/exceptions/moduloByZero.sea");
    }

    @Test(expected = MissingResourceException.class)
    public void undefinedVar() throws Exception {
        SeaCompiler.debug("tests/exceptions/undefinedVar.sea");
    }

    @Test(expected = MissingResourceException.class)
    public void undefinedFun() throws Exception {
        SeaCompiler.debug("tests/exceptions/undefinedFun.sea");
    }

    @Test(expected = UnexpectedException.class)
    public void unexpectedExp() throws Exception {
        SeaCompiler.debug("tests/exceptions/unexpectedExp.sea");
    }

    @Test(expected = MismatchException.class)
    public void notBool() throws Exception {
        SeaCompiler.debug("tests/exceptions/notBool.sea");
    }

}
