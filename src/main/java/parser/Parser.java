package parser;

import exceptions.MismatchException;
import intermediate.expression.Constant;
import intermediate.expression.ExprNode;
import lexer.Lexer;
import lexer.Token;
import lexer.Type;
import persistent.Terms;

import java.io.Closeable;
import java.io.IOException;

public abstract class Parser implements Closeable {

    private final Lexer lexer;

    protected Token lookahead;

    protected Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    protected void match(Type x) {
        if (lookahead.type().equals(x)) {
            consume();
        } else {
            throw new MismatchException(generateMessage("type " + x.toString(), lookahead.type().toString()));
        }
    }

    protected void match(String x) {
        if (lookahead.value().equals(x)) {
            consume();
        } else {
            throw new MismatchException(generateMessage(x, lookahead.value()));
        }
    }

    protected void lBrack() {
        match(Type.LBRACK);
    }

    protected void begin() {
        match(Type.BEGINBLOCK);
    }

    protected void comma() {
        match(Type.COMMA);
    }

    protected void end() {
        match(Type.ENDBLOCK);
    }

    protected String type() {
        String temp = lookahead.value();
        match(Type.TYPE);
        return temp;
    }

    protected Token identifier() {
        Token temp = lookahead;
        match(Type.IDENTIFIER);
        return temp;
    }

    protected void rBrack() {
        match(Type.RBRACK);
    }

    protected boolean isType() {
        return lookahead.type().equals(Type.TYPE);
    }

    protected boolean isNum() {
        return lookahead.type().equals(Type.NUMBER);
    }

    protected boolean isID() {
        return lookahead.type().equals(Type.IDENTIFIER);
    }

    protected boolean isString() {
        return lookahead.type().equals(Type.STRING);
    }

    protected boolean isComma() {
        return lookahead.type().equals(Type.COMMA);
    }

    protected boolean isLBrack() {
        return lookahead.type().equals(Type.LBRACK);
    }

    protected boolean isRBrack() {
        return lookahead.type().equals(Type.RBRACK);
    }

    protected void consume() {
        lookahead = lexer.nextToken();
    }

    protected Token lookahead() {
        return lookahead;
    }

    protected void deleteLast() {
        Terms.removePenultimate();
    }

    protected void add(ExprNode n) {
        Terms.add(n);
    }

    protected void addNode(ExprNode node) {
        addNodeAux(node, 2);
    }

    protected void addNode(ExprNode node, int i) {
        addNodeAux(node, i);
    }

    protected void addNodeAux(ExprNode node, int i) {
        try {
            add(new Constant(Integer.parseInt(node.optimize())));
        } catch (Exception e) {
            add(node);
        }
        while (i > 0) {
            deleteLast();
            --i;
        }
    }

    protected boolean isAddOp() {
        return is("+") || is("-");
    }

    protected boolean isMulOp() {
        return is("*") || is("/") || is("%");
    }

    protected boolean is(String value) {
        return lookahead.value().equals(value);
    }

    public void warning(String s) {
        System.out.println("Warning: " + s);
    }

    public void warning(Token t, String s) {
        System.out.println("Warning " + t.position() + ": " + s);
    }

    private String generateMessage(String expected, String recieved) {
        return lookahead.position() + ": expecting type '" + expected +
                "' found '" + recieved + "'";
    }

    @Override
    public void close() throws IOException {
        lexer.close();
    }

}
