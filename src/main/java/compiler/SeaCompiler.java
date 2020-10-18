package compiler;
import exceptions.MismatchException;
import exceptions.MissingResourceException;
import exceptions.OverrideException;
import exceptions.UnexpectedException;
import generator.X86;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import parser.SeaParser;

public class SeaCompiler extends Request{
	
	static private String output = "";		
	static boolean asmf = false;
	static boolean guif = false;
	static boolean debug = true;

	public static void compile(String s) throws IOException, InterruptedException{
		compileFile(s);
	}
	
	public static String debug(String s) throws IOException, InterruptedException {
		return debugFile(s.substring(1));
	}
	
	static void compileFile(String s) throws IOException, InterruptedException{
		System.out.println(debugFile(s));
	}
	
	static String debugFile(String s) throws IOException, InterruptedException {
		return new SeaCompiler(s).output();
	}
	
	private SeaCompiler(String s) throws IOException, InterruptedException {
		init(s);
	}
	
	private void init(String s) throws IOException, InterruptedException {
		String[] array = s.split("/");
		String temp = array[array.length-1];
		if(temp.length() > 4){
			if(!s.substring(s.length()-4, s.length()).equals(".sea"))
				throw new MismatchException("ERROR: Filename must have the 'sea' extension");
			String x;
			if (asmf)
				x = s.substring(0, s.length()-3)+"asm";
			else
				x = "/tmp/ilon.asm";
			
		new SeaParser(s);	
			BufferedWriter writeToFile = new BufferedWriter(new FileWriter(x));
//			writeToFile.write
			writeToFile.write(new X86().output());
			writeToFile.close();

			if (!asmf){
				if (debug){
					execute("as -gstabs --32 -o /tmp/ilon.o " + x);
					execute("ld -melf_i386 -o " + "/tmp/ilon" + " /tmp/ilon.o");	
					output = execute("/tmp/ilon");
				} else {
					execute("as -gstabs --32 -o /tmp/ilon.o " + x);
					execute("ld -melf_i386 -o " + s.subSequence(0, s.length()-4) + " /tmp/ilon.o");
				}
			}
		} else 
			throw new MismatchException("ERROR: Filename too short");
	}

	private String output(){
		return output;
	}
	
	public static void main(String args[]) throws IOException, InterruptedException{
		if (debug){
			compileFile("./tests/algorithms/fibonacci.sea");
			compileFile("./tests/algorithms/timestamp.sea");
		} else {
			try {
				String s = getArgs(args);
				if (guif)
					s = new FileChooser().name();
				else
					compileFile(s);
				String x = "";
				if (asmf)
					x = ".asm";
				System.out.println(s.substring(0,s.length()-4) + x + 	" was successfully created.");
			} catch (Exception e){
				System.out.println(e.getMessage());
			}
		}
		
	}
	
	public static String getArgs(String args[]) throws Exception{
		String path = "";
		final String asm = "-asm";
		final String gui = "-gui";
		boolean pathSet = false;
		if (args.length < 1){
			System.out.println("you need to specify at least one argument");
			System.exit(1);
		}
		for (String s : args){
		switch(s){
		case asm:
			if (asmf)
				throw new MismatchException("the asm flag was already set once.");
			asmf = true;
			break;
		case gui:
			if (guif)
				throw new Exception("the gui flag was already set once.");
			guif = true;
			break;
		default: 
			if (pathSet)
				throw new Exception("path was already set to: " + path);
			path = s;
			pathSet = true;
		}
		}
		return path;
	}
}
