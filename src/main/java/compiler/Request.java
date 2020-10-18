package compiler;

import java.io.*; 

public abstract class Request { 
	
	public String execute(final String s) throws IOException, InterruptedException{ 
		String output = "";
		Process p = Runtime.getRuntime().exec(s); 
		p.waitFor(); 
		BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream())); 
		String line=reader.readLine(); 
		while( line != null ){ 
			output += line + '\n'; 
			line = reader.readLine(); 
		} 
		return output;
	} 
	
	public static String pwd() throws IOException, InterruptedException { 
		String line = null;
		Process p = Runtime.getRuntime().exec("pwd"); 
		p.waitFor(); 
		BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream())); 
		line = reader.readLine(); 
		return line;
	} 
} 
