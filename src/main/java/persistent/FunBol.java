package persistent;


import exceptions.MismatchException;
import lexer.Token;

import java.util.ArrayList;

public class FunBol extends Symbol {

    private final ArrayList<String> args;
    private boolean defined;
    private boolean called;

    FunBol(Token t, String type, String alias, ArrayList<String> args) {
        super(type, t, alias);
        this.args = args;
        defined = false;
        called = false;
    }

    protected boolean isDefined() {
        return defined;
    }

    protected boolean wasCalled() {
        return called;
    }

    protected void define(String type) {
        defined = true;
        if (this.type() == null) {
            setType(type);
        } else if (!this.type().equals(type)) {
            throw new MismatchException(token(), "Function '" + name() + "' was previously called in a '" + type() + "' context.");
        }
    }

    protected FunBol call() {
        called = true;
        return this;
    }

    public ArrayList<String> args() {
        return args;
    }

}
