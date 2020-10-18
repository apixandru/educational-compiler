package persistent;

import intermediate.*;

import java.util.HashMap;
import java.util.Hashtable;
import exceptions.*;

import lexer.Token;
import lexer.Type;

public class Scope{
	
	private static Scope scope = null;
	private static int variables = 0; 

	private static boolean allow = true;
	
	private Scope parent;
	private HashMap<String, VarBol> symbols;
	
	public Scope(Scope parent){
		this.parent = parent;
		symbols = new HashMap<>();
	}
	
	
	public void define(Token t, String type) {
		define(t, type,  "-" + ++variables*4 + "(%ebp)");
	}
	
	public void global(String value, Token t, String type) {
		define(t, type, value);
	}
	
	public void define(Token t, String type, String alias) {
//		if (!t.type().equals(Type.STRING)){
			VarBol sym = symbols.get(t.value());
			if (sym == null)
				symbols.put(t.value(), new VarBol(type, t, alias));
			else
				throw new OverrideException(t, "Variable " + t.value() + " was already defined at " + sym.token().position());
		}
//	}
	
	public static String typeOf(Token t) {
		Symbol x = scope.getSym(t);
		return x.type();
	}
	
	public static String aliasOf(Token t) {
		Symbol x = scope.getSym(t);
		return x.alias();
	}
	
	private Symbol getSym(Token t) {
		String s = t.value();
		Symbol sym = symbols.get(s);
		if (sym == null)
			if (parent != null)
				return parent.getSym(t);
			else 
				throw new MissingResourceException(t, "Variable " + s + " is undefined.");
		return sym;
	}
	
	public static String plusOne() {
		Token t = new Token("for", Type.IDENTIFIER, 0, 0);
		pushVar(t, "int");
		return scope.symbols.get("for").alias();
	}
	
	public int size(){
		return symbols.size();
	}
	
	public static void push(){
		scope = new Scope(scope);
	}
	
	public static void pop(int i){
		int temp = scope.size() - i;
		variables -= temp;
		Statements.add(new Free(temp));
		scope = scope.parent;
	}
	
	public static void pop(){
		pop(0);
	}
	
	public static void pushVar(Token t, String type) {
		scope.define(t, type);
		Statements.add(new Definition(scope.getSym(t).alias()));
	}
	
	public static void pushGlobalVar(String value, Token t, String type) {
		scope.global(value, t, type);
	}
	
	public static void pushVariable(Token t, String type, String alias) {
		scope.define(t, type, alias);
	}
	
	public static void reset(){
		scope = new Scope(null);
		allowLocal();
		variables = 0;
	}
	
	public static void banLocal(){
		allow = false;
	}
	
	public static void allowLocal(){
		allow = true;
	}
	
	public static boolean allows(){
		return allow;
	}
}
