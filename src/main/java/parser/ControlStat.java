package parser;

import exceptions.MismatchException;
import intermediate.*;
import intermediate.control.*;
import intermediate.expression.Constant;
import intermediate.expression.ExprNode;
import intermediate.expression.Unknown;
import lexer.Lexer;
import lexer.Token;
import lexer.Type;
import persistent.Scope;
import persistent.Statements;

import java.util.ArrayList;
import java.util.List;

public abstract class ControlStat extends BooleanExpression {

    public ControlStat(Lexer lexer) {
        super(lexer);
    }

    protected void forf() {
        match("for");
        lBrack();
        Token variable = lookahead();
        match(Type.IDENTIFIER);
        Unknown name = getUnk(variable);
        match("=");
        ExprNode n = getExpression();
        Assign begin = new Assign(name, n);
        match("to");
        ExprNode end = getExpression();
        Scope.push();
        Unknown condition = new Unknown(Scope.plusOne(), "int");
        Statements.add(new Assign(condition, end));
        match(Type.COMMA);
        forAsOrAd(name);
        AddNode change = Statements.getChange();
        rBrack();
        Statements.push();
        stats();
        List<BaseNode> forStats = Statements.pop();
        Statements.add(new For(begin, condition, change, forStats));
        Scope.pop();
    }

    protected void loop() {
        match("loop");
        Statements.push();
        stats();
        List<BaseNode> loopStats = Statements.pop();
        Statements.add(new Loop(loopStats));
    }

    protected void dof() {
        match("do");
        Unknown condition = new Unknown(Scope.plusOne(), "int");
        try {
            Integer.parseInt(lookahead().value());
        } catch (NumberFormatException e) {
            throw new MismatchException(lookahead, "Do requires a positive number");
        }
        ExprNode n = new Constant(lookahead().value());
        Assign change = new Assign(condition, n);
        match(lookahead().value());
        Statements.push();
        stats();
        Statements.add(new Decrement(condition));
        List<BaseNode> doStats = Statements.pop();
        Statements.add(new Do(change, doStats));
    }

    protected void whilef() {
        Token t = lookahead();
        match("while");
        lBrack();
        ExprNode n = getBoolean();
        rBrack();
        Statements.push();
        stats();
        List<BaseNode> whileStats = Statements.pop();
        try {
            int temp = Integer.parseInt(n.toString());
            if (temp == 1) {
                warning(t, "'While' test condition always resolves to true, converting statment from while to loop");
                Statements.add(new Loop(whileStats));
            } else {
                warning(t, "'While' test condition always resolves to false, removing the while statment.");
            }
        } catch (Exception e) {
            Statements.add(new While(n, whileStats));
        }
    }

    protected void iff() {
        List<BaseNode> ifStats = new ArrayList<>();
        List<BaseNode> elseStats = new ArrayList<>();
        Token t = lookahead();
        match("if");
        lBrack();
        ExprNode n = getBoolean();
        rBrack();
        Statements.push();
        stats();
        ifStats = Statements.pop();
        if (is("else")) {
            Statements.push();
            match("else");
            stats();
            elseStats = Statements.pop();
        }
        try {
            int temp = Integer.parseInt(n.toString());
            if (temp == 1) {
                warning(t, "'If' test condition always resolves to true, removing the unnecesairy test condition.");
                Statements.addAll(ifStats);
            } else {
                warning(t, "'If' test condition always resolves to false, removing the unnecesairy test condition and the if body.");
                Statements.addAll(elseStats);
            }
        } catch (Exception e) {
            Statements.add(new If(n, ifStats, elseStats));
        }
    }


    protected void repeatf() {
        Token t = lookahead();
        match("repeat");
        Statements.push();
        stats();
        List<BaseNode> repeatStats = Statements.pop();
        match("until");
        lBrack();
        ExprNode n = getBoolean();
        rBrack();
        try {
            int temp = Integer.parseInt(n.toString());
            if (temp == 1) {
                warning(t, "'Repeat' test condition always resolves to true, converting statment from repeat to loop");
                Statements.add(new Loop(repeatStats));
            } else {
                warning(t, "'Repeat' test condition always resolves to false, removing the test condition.");
                Statements.addAll(repeatStats);
            }
        } catch (Exception e) {
            Statements.add(new Repeat(n, repeatStats));
        }
    }

    private void stats() {
        if (lookahead().type() == Type.BEGINBLOCK) {
            match(Type.BEGINBLOCK);
            Scope.banLocal();
            block();
            Scope.allowLocal();
            match(Type.ENDBLOCK);
        } else {
            singleStatement();
        }
    }

    public void breakf() {
        match("break");
        Statements.add(new Break());
    }

    public void returnf() {
        match("return");
        Statements.add(new Return(lookahead, getExpression()));
    }

    protected abstract void block();

    protected abstract void singleStatement();

    protected abstract void forAsOrAd(Unknown name);

    protected abstract BaseNode createUnk(Token name);

    protected abstract void pushVariable(Token t, String type);

}
