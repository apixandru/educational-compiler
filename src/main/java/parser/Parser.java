package parser;

import java.io.IOException;

import intermediate.expression.*;
import lexer.Lexer;
import persistent.*;
import exceptions.*;

import lexer.SeaLexer;
import lexer.Token;
import lexer.Type;

public abstract class Parser {

    protected Token lookahead;

    private final Lexer lexer;

    protected Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    protected void match(Type x) throws MismatchException, IOException, UnexpectedException{
        if ( lookahead.type().equals(x) )
        	consume();
        else
        	throw new MismatchException(generateMessage("type " + x.toString(), lookahead.type().toString()));
    }

    protected void match(String x) throws MismatchException, IOException, UnexpectedException{
        if ( lookahead.value().equals(x) )
        	consume();
        else
        	throw new MismatchException(generateMessage(x.toString(), lookahead.value()));
    }

    protected void lBrack() throws MismatchException, UnexpectedException, IOException{
        match(Type.LBRACK);
    }

    protected void begin() throws MismatchException, UnexpectedException, IOException{
        match(Type.BEGINBLOCK);
    }


    protected void comma() throws MismatchException, UnexpectedException, IOException{
        match(Type.COMMA);
    }


    protected void end() throws MismatchException, UnexpectedException, IOException{
        match(Type.ENDBLOCK);
    }

    protected String type() throws MismatchException, UnexpectedException, IOException{
        String temp = lookahead.value();
    	match(Type.TYPE);
    	return temp;
    }

    protected Token identifier() throws MismatchException, UnexpectedException, IOException{
        Token temp = lookahead;
    	match(Type.IDENTIFIER);
    	return temp;
    }

    protected void rBrack() throws MismatchException, UnexpectedException, IOException{
    	match(Type.RBRACK);
    }

    protected boolean isType(){
    	return lookahead.type().equals(Type.TYPE);
    }

//    protected boolean isGlobal(){
//    	return lookahead.value().equals("global");
//    }
//    
    protected boolean isNum(){
    	return lookahead.type().equals(Type.NUMBER);
    }

    protected boolean isID(){
    	return lookahead.type().equals(Type.IDENTIFIER);
    }

    protected boolean isString(){
    	return lookahead.type().equals(Type.STRING);
    }

    protected boolean isComma(){
    	return lookahead.type().equals(Type.COMMA);
    }

    protected boolean isLBrack(){
    	return lookahead.type().equals(Type.LBRACK);
    }

    protected boolean isRBrack(){
    	return lookahead.type().equals(Type.RBRACK);
    }

    protected void consume() throws MismatchException, IOException, UnexpectedException{
    	lookahead = lexer.nextToken();
    }

    protected Token lookahead() {
    	return lookahead;
    }

//    protected static Token lookahead() {
//    	return lookahead;
//    }

    protected void deleteLast() {
    	Terms.removePenultimate();
    }

    protected void add( ExprNode n ) {
    	Terms.add( n );
    }

    protected void addNode( ExprNode node ) {
    	addNodeAux(node, 2);
    }

    protected void addNode( ExprNode node, int i ) {
    	addNodeAux( node, i );
    }

    protected void addNodeAux( ExprNode node, int i ) {
    	try{
    		add( new Constant( Integer.parseInt( node.optimize() ) ) );
		} catch ( Exception e ) {
			add( node );
		}
    	while ( i > 0 ) {
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

    protected boolean is(String value){
    	return lookahead.value().equals(value);
    }

	public void warning(String s){
		System.out.println("Warning: " + s);
	}

	public void warning(Token t, String s){
		System.out.println("Warning " + t.position() + ": " + s);
	}

    private String generateMessage(String expected, String recieved){
    	return lookahead.position() + ": expecting type '" + expected +
                "' found '" + recieved + "'";
    }

}
