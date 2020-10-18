package persistent;

import lexer.Token;

public abstract class Symbol {
	private String name;
	private String alias;
	private Token token;
	private String type;
	
	Symbol(String alias, String type){
		name = alias;
		this.type = type;
		this.alias = alias;
	}
	
	public Symbol(String type, Token t, String alias){
		this(alias, type);
		name = t.value();
		token = t;
	}
	
	public String name(){
		return name;
	}

	public String alias(){
		return alias;
	}
	
	public Token token(){
		return token;
	}
	
	protected void setType(String type){
		this.type = type;
	}
	
	public String type(){
		return type;
	}
	
}
