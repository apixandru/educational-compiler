package persistent;

import intermediate.*;
import intermediate.expression.*;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;


import exceptions.*;

import lexer.Token;

public class FunTable{
	
	@SuppressWarnings("unused")
	private static FunTable parent = new FunTable(); // for code coverage purposes only
	
	private static Hashtable<String, FunBol> symbols = new Hashtable<String, FunBol>();
	
	private static int unique = 0;
	private static final String PREFIX = "UDF";
	public static String functions;
	
	public static String define(Token t, String type, ArrayList<ExprNode> args) {
		String name = t.value();
		FunBol sym = symbols.get(name);
		if (sym == null){
			symbols.put(name, createFun(t, type, getArguments(args)));
			sym = symbols.get(name);
		}
		if ( !sym.isDefined() ){
			sym.define(type);
			checkArgs(sym.args(), args, t);
		} else {
			String x = sym.token().position();
			if ( x.equals("-10, -10") )
				throw new OverrideException(t, "Cannot override builtin functions.");
			throw new OverrideException(t, "Function " + name + " was already defined at " + sym.token().position());
		}
		return sym.alias();
	}
	
	public static String call(Token t, ArrayList<ExprNode> args, String type) {
		FunBol sym = symbols.get(t.value());
		if (sym == null){
			sym = createFun(t, type, getArguments(args));
		}
		sym.call();
		symbols.put(t.value(), sym);
		checkArgs(sym.args(), args, t);
		return sym.alias();
	}
	
	private static ArrayList<String> getArguments(ArrayList<ExprNode> args){
		ArrayList<String> arguments = new ArrayList<String>();
		for (ExprNode b: args)
			arguments.add(b.type());
		return arguments;
	}
	
	private static void checkArgs(ArrayList<String> inTable, ArrayList<ExprNode> called, Token t) {
		if (inTable.size() != called.size())
			throw new MismatchException(t, "Number of arguments don't match.");
		else {
			int size = inTable.size();
			for (int i = 0; i < size ; i++ ){
				String type1 = inTable.get(i);
				String type2 = called.get(i).type();
				if(type2 == null){
					FunBol x = FunTable.symbols.get(((Call) called.get(i)).name());
					x.setType(type1);
					type2 = x.type();
				}
				if (!type1.equals(type2))
					throw new MismatchException(t, "Type Mismatch.");
			}
		}
	}
	
	public static int undefined(){
		Enumeration<String> x = symbols.keys();
		int notDefinded = 0;
		FunBol cymbol = null;
		functions = "";
		while (x.hasMoreElements()){
			cymbol = symbols.get(x.nextElement());
			if (!cymbol.isDefined()){
				notDefinded++;
				functions += cymbol.name() +'\n';
				remove(cymbol);
			}
		}
		return notDefinded;
	}
	
	public static int uncalled(){
		Enumeration<String> x = symbols.keys();
		int notCalled = 0;
		FunBol cymbol = null;
		functions = "";
		while (x.hasMoreElements()){
			cymbol = symbols.get(x.nextElement());
			if (!cymbol.wasCalled()){	
				notCalled++;
				functions += cymbol.name() +'\n';
				remove(cymbol);
			}
		}
		return notCalled;
	}
	
	public static Hashtable<String, FunBol> symbols(){
		return symbols;
	}
	
	public static void remove(Symbol cymbol){
		symbols.remove(cymbol.name());
		Statements.remove(cymbol);
		for (BaseNode b : Statements.getAll())
			if (b instanceof Function)
				if (((Function) b).name().equals(cymbol.name())){
					Statements.remove(b);
					return;
				}
	}
	
	public static void setInt(String name){
		FunBol cymbol = symbols.get(name);
		cymbol.setType("int");
	}
	
	public static void add(String name, String type, ArrayList<String> args) {
		symbols.put(name, new FunBol(new Token(name, null, -10, -10+name.length()), type, name, args));
		FunBol s = symbols.get(name);
		s.define(type);
		s.call();
	}
	
	public static void reset() {
		symbols.clear();
		
		ArrayList<String> intArgument = new ArrayList<String>();
		ArrayList<String> stringArgument = new ArrayList<String>();
		
		FunTable.add("printlf", "void", new ArrayList<String>());
		FunTable.add("exit", "void", new ArrayList<String>());
		intArgument.add("int");
		FunTable.add("printintlf", "void", intArgument);
		FunTable.add("printint", "void", intArgument);
		stringArgument.add("string");
		FunTable.add("print", "void", stringArgument);
		FunTable.add("getlen", "void", stringArgument);
	}

	private static FunBol createFun(Token t, String type, ArrayList<String> args){
		return new FunBol(t, type, PREFIX + unique++, args);
	}
	
}
