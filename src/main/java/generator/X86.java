package generator;

import java.util.ArrayList;
import java.util.List;

import persistent.FunBol;
import persistent.FunTable;
import persistent.Statements;

import exceptions.MismatchException;
import exceptions.MissingResourceException;


import intermediate.*;
import intermediate.control.*;
import intermediate.expression.*;

public class X86 implements NodeVisitor{

	private int args = 0;
	private String type = "";

	private String code;
	static final char LF = '\n';
	static final char TAB = '\t';

	public static final String PREFIX = "UL";
	public static int num = 0;
	private List<BaseNode> assigns = new ArrayList<>();
	private boolean returned = false;

	private Label label = null;

	public X86() {
		code = "";
		new Prelude(this).prelude();
		for (BaseNode n : Statements.getAll()){
			if(n instanceof Assign){
				assigns.add(n);
				continue;
			}
			n.accept(this);
		}
	}

	@Override
	public void visit(Assign node) {
		node.expression().accept(this);
		movl("%eax", node.variable().alias());
	}

	@Override
	public void visit(Increment node) {
		outTab("incl " + node.variable());
	}

	@Override
	public void visit(Decrement node) {
		outTab("decl " + node.variable());

	}

	@Override
	public void visit(Call node) {
		ArrayList<ExprNode> temp = node.args();
		int size = temp.size();
		push("$0");
		for (int i = 0; i < size; i++){
			node.args().get(i).accept(this);
			push();
		}
		outTab("call " + node.alias());
		pop(size);
		pop("%eax");

	}

	@Override
	public void visit(Function node) {
		FunBol temp = FunTable.symbols().get(node.name());
		args = temp.args().size() * 4;
		type = temp.type();
		returned = false;
		List<BaseNode> stats = node.statements();
		out(node.alias() + ":");
		beginFun();
		for (BaseNode b : stats){
			b.accept(this);
		}
		BaseNode b;
		if (type.equals("int")){
			if (!returned)
				throw new MissingResourceException(temp.token(), "Missing return statement.");
			int i = stats.size() - 1;
			while (true){
				b = stats.get(i--);
				if (b instanceof Free){
					continue;
				} else if (b instanceof Return){
					break;
				} else {
					throw new MismatchException(temp.token(),"The return statement should be the last statement in the function.");
				}
			}
		}
		endFun();
	}

	@Override
	public void visit(Run node) {
		out("_start:");
		for (BaseNode n : assigns){
			n.accept(this);
		}
		visit((Call) node);
	}

	@Override
	public void visit(Free node) {
		int temp = node.ammount();
		if (temp != 0)
			outTab("addl $" + temp + ",%esp");
	}

	@Override
	public void visit(Definition node) {
		outTab("subl $4,%esp");
		movl(node.value(), node.alias());
	}

	@Override
	public void visit(Repeat node) {
		String l1=newLabel();
		String l2=newLabel();
		postLabel(l1);
		pushLabel(l2);
		for (BaseNode n :node.body())
			n.accept(this);
		node.test().accept(this);
		outTab("cmp $0,%al");
		outTab("je " + l1);
		postLabel(l2);
		popLabel();
	}

	@Override
	public void visit(While node) {
		String l1=newLabel();
		String l2=newLabel();
		postLabel(l1);
		node.test().accept(this);
		jumpFalse(l2);
		pushLabel(l2);
		for (BaseNode n :node.body())
			n.accept(this);
		jump(l1);
		postLabel(l2);
		popLabel();
	}

	@Override
	public void visit(Break node) {
		if (label != null){
			jump(label);
		} else
			throw new MismatchException("No loop to break from");
	}

	@Override
	public void visit(Loop node) {
		String l1=newLabel();
		String l2=newLabel();
		postLabel(l1);
		pushLabel(l2);
		for (BaseNode n :node.body())
			n.accept(this);
		jump(l1);
		postLabel(l2);
		popLabel();
	}

	@Override
	public void visit(Do node) {
		String l1=newLabel();
		String l2=newLabel();
		node.change().accept(this);
		postLabel(l1);
		out("#");
		movl(node.change().variable().toString(), "%eax");
		jumpFalse(l2);
		pushLabel(l2);
		for (BaseNode n :node.body())
			n.accept(this);
		jump(l1);
		postLabel(l2);
	}

	@Override
	public void visit(For node) {
		String l1=newLabel();
		String l2=newLabel();
		String name = node.variable().variable().alias();
		node.variable().accept(this);
		node.test().accept(this);
		AddNode id = node.update();
		postLabel(l1);
		movl(name, "%eax");
		outTab("cmp " + node.test().toString() + ",%eax");
		if (node.update().sign() == '+'){
			out("jg " + l2);
		}
		else {
			out("jl " + l2);
		}
		pushLabel(l2);
		for (BaseNode n :node.body()){
			n.accept(this);
		}
		id.accept(this);
		jump(l1);
		postLabel(l2);
		popLabel();
	}

	@Override
	public void visit(If node) {
		String s1 = newLabel();
		String s2 = s1;
		node.test().accept(this);
		jumpFalse(s1);
		for (BaseNode n :node.body())
			n.accept(this);
		List<BaseNode> elseList = node.elseList();
		if (elseList.size() != 0){
			s2 = newLabel();
			jump(s2);
			postLabel(s1);
			for (BaseNode n :elseList)
				n.accept(this);
		}
		postLabel(s2);
	}

	@Override
	public void visit(GreaterEqual node) {
		compare(node);
		outTab("setle %al");
		pop();
	}

	@Override
	public void visit(LessEqual node) {
		compare(node);
		outTab("setge %al");
		pop();
	}

	@Override
	public void visit(Greater node) {
		compare(node);
		outTab("setl %al");
		pop();
	}

	@Override
	public void visit(Less node) {
		compare(node);
		outTab("setg %al");
		pop();
	}

	@Override
	public void visit(Equal node) {
		compare(node);
		outTab("sete %al");
		pop();
	}

	@Override
	public void visit(NotEqual node) {
		compare(node);
		outTab("setne %al");
		pop();
	}

	@Override
	public void visit(Or node) {
		mathOp(node);
		outTab("or (%esp),%eax");
		pop();
	}

	@Override
	public void visit(Not node) {
		node.expression().accept(this);
		outTab("xor $1,%al");
	}

	@Override
	public void visit(And node) {
		mathOp(node);
		outTab("and (%esp),%eax");
		pop();
	}

	@Override
	public void visit(Add node) {
		mathOp(node);
		outTab("addl (%esp), %eax");
		pop();
	}

	@Override
	public void visit(Substract node) {
		mathOp(node);
		outTab("subl (%esp),%eax");
		outTab("neg %eax");
		pop();
	}

	@Override
	public void visit(Multiply node) {
		mathOp(node);
		outTab("mull (%esp)");
		pop();
	}

	@Override
	public void visit(Divide node) {
		mathOp(node);
		outTab("movl %eax,%ebx");
		outTab("movl (%esp),%eax");
		outTab("cdq");
		outTab("idivl %ebx");
		pop();
	}

	@Override
	public void visit(Modulo node) {
		mathOp(node);
		outTab("movl %eax, %ebx");
		outTab("movl (%esp),%eax");
		outTab("xorl %edx,%edx");
		outTab("cdq");
		outTab("idivl %ebx");
		outTab("movl %edx, %eax");
		pop();
	}

	@Override
	public void visit(Constant node) {
		movl("$" + node.value(), "%eax");
	}

	@Override
	public void visit(Unknown node) {
		movl(node.alias(), "%eax");
	}

	void out(String s){
		code += s + LF;
	}

	void outTab(String s){
		out(TAB + s);
	}

	private void mathOp(ExprNode f) {
		f.getLeft().accept(this);
		push();
		f.getRight().accept(this);
	}

	void movl(String source, String destination){
		code += TAB + "movl " + source + ", " + destination + LF;
	}

	void push(String s){
		code += TAB + "pushl " + s + LF;
	}

	void push(){
		push("%eax");
	}

	private void compare(ExprNode f) {
		mathOp(f);
		outTab("cmp (%esp),%eax");
	}

	private void pop(String s){
		code += TAB + "popl " +s + LF;
	}

	void pop(){
		outTab("addl $4,%esp");
	}

	void pop(int i){
		if (i > 0)
			outTab("addl $" + i*4 + ",%esp");
	}

	void jumpFalse(String s){
		outTab("cmp $0,%al");
		outTab("je " + s);
	}

	void jump(String s){
		outTab("jmp " + s);
	}

	void jump(Label l){
		outTab("jmp " + l.label());
	}

	public static String newLabel(){
		String temp = "" + num++;
		while (temp.length() < 3)
			temp = "0" + temp;
		return PREFIX + temp;
	}

	public void postLabel(String label){
		out(label + ":");
	}

	public String output(){
		return code;
	}

	public void pushLabel(String s){
		label = new Label(label, s);
	}

	public void popLabel(){
		label = label.parent();
	}

	void beginFun(){
		push("%ebp");
		movl("%esp", "%ebp");
	}

	void endFun(){
		movl("%ebp", "%esp");
		pop("%ebp");
		outTab("ret" + LF);
	}

	@Override
	public void visit(Return node) {
		if (returned)
			throw new MismatchException(node.token(), "Can not have more than one return.");
		node.expression().accept(this);
		movl("%eax", 8 + args + "(%ebp)");
		if (!node.varType().equals(type))
			throw new MismatchException(node.token(), "Expecting return type " + type + ", found " + node.varType() + " instead.");
		returned = true;
	}
}
