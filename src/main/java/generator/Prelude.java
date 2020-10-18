package generator;

import java.util.Enumeration;
import java.util.Hashtable;

import persistent.FunBol;
import persistent.FunTable;
import persistent.Symbol;


public class Prelude {
	
	X86 gen;
	
	private static String strings = "";
	
	private static final String PREFIX = "UDV";
	private static int num = 0;
	
	Prelude(X86 visitor){
		this.gen = visitor;
	}
	
	void prelude(){
		out(".data");
		out(strings);
		out(".text");
		out(".globl _start");
		Hashtable<String, FunBol> s = FunTable.symbols();
		Enumeration<String> x = s.keys();
		Symbol cymbol = null;
		while (x.hasMoreElements()){
			cymbol = s.get(x.nextElement());
			out(".type " + cymbol.alias() + ", @function");
		}
		out("");
		
		exit();
		getlen();
		print();
		printint();
		printlf();
		printintlf();
		
	}
	
	
	private void printlf(){
		out("printlf:");
		gen.beginFun();
		gen.push("$10");
		movl("$4", "%eax");
		movl("$1", "%ebx");
		movl("%esp", "%ecx");
		movl("$1", "%edx");
		outTab("int $0x80");
		gen.pop();
		gen.endFun();
	}
	
	private void print(){
		out("print:");
		gen.beginFun();
		outTab("movl 8(%ebp), %edx");
		outTab("pushl %edx");
		outTab("call getlen");
		outTab("popl %edx");
		outTab("movl 8(%ebp), %ecx");
		movl("$4", "%eax");
		movl("$1", "%ebx");
		outTab("int $0x80");
		gen.endFun();
	}
	
	private void printintlf(){
		out("printintlf:");
		gen.push("4(%esp)");
		outTab("call printint");
		outTab("call printlf");
		gen.pop();
		outTab("ret" + X86.LF);
	}
	
	private void printint(){
		out("printint:");
		gen.beginFun();
		movl("8(%ebp)", "%eax");
		outTab("subl $24, %esp");
		movl("$0", "%ecx");
		outTab("cmp $0, %eax");
		String label = X86.newLabel();
		outTab("jge " + label);
		outTab("neg %eax");
		outTab("addl $1, %ecx");
		out(label + ":");
		outTab("movl %eax, -4(%ebp)");
		outTab("movl $0, -8(%ebp)");
		outTab("movl $1, -12(%ebp)");
		outTab("lea -13(%ebp), %edi");
		label = X86.newLabel();
		out(label + ":");
		outTab("movl -4(%ebp),%eax");
		outTab("xorl %edx,%edx");
		outTab("movl $10, %ebx");
		outTab("divl %ebx");
		outTab("movl %eax, -4(%ebp)");
		outTab("cmp $0, %eax");
		String label2 = X86.newLabel();
		outTab("jne " + label2);
		outTab("movl $0, -12(%ebp)");
		out(label2 + ":");
		outTab("addl $48, %edx");
		outTab("movb %dl, (%edi)");
		outTab("dec %edi");
		outTab("incl -8(%ebp)");
		outTab("cmp $0,-12(%ebp)");
		outTab("jne " + label);
		outTab("cmp $0,%ecx");
		label = X86.newLabel();
		outTab("jz " + label);
		outTab("movb $45,(%edi)");
		outTab("dec %edi");
		outTab("incl -8(%ebp)");
		out(label + ":");
		outTab("incl %edi");
		outTab("movl %edi,%ecx");
		outTab("movl $4,%eax");
		outTab("movl $1,%ebx");
		outTab("movl -8(%ebp),%edx");
		outTab("int $0x80");
		outTab("addl $24,%esp");
		gen.endFun();
	}
	
	private void exit(){
		out("exit:");
		movl("$1", "%eax");
		movl("$0", "%ebx");
		outTab("int $0x80");
		out("");
	}
	
	private void getlen(){
		out("getlen:");
		gen.beginFun();
		outTab("movl 8(%ebp), %edi");
		outTab("xorl %ecx, %ecx");
		outTab("not %ecx");
		outTab("sub %al, %al");
		outTab("cld");
		outTab("repne scasb");
		outTab("not %ecx");
		outTab("decl %ecx");
		outTab("movl %ecx, 8(%ebp)");
		gen.endFun();
	}
	
	public static String addString(String string){
		return add('\"'+string+'\"', "asciz");
	}
	
	public static String addInt(String string){
		return add(string, "int");
	}
	
	private static String add(String string, String type){
		String temp = PREFIX + num++;
		strings += X86.TAB + temp + ":" + X86.LF + X86.TAB + 
				X86.TAB + "." + type + " " + string + X86.LF;
		if(type.equals("asciz"))
			temp = "$" + temp;
		return temp;
	}
	
	public void outTab(String s){
		gen.outTab(s);
	}
	
	private void movl(String s, String d){
		gen.movl(s, d);
	}
	
	public void out(String s){
		gen.out(s);
	}
}
