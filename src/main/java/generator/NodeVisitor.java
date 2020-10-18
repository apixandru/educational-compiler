package generator;
import exceptions.MismatchException;
import exceptions.MissingResourceException;
import intermediate.*;
import intermediate.control.*;
import intermediate.expression.*;

public interface NodeVisitor {
	void visit(Assign node) throws MissingResourceException, MismatchException;
	void visit(Increment node) throws MissingResourceException;
	void visit(Decrement node) throws MissingResourceException;
	void visit(Call node) throws MissingResourceException, MismatchException;
	void visit(Function node) throws MissingResourceException, MismatchException;
	void visit(Run node) throws MissingResourceException, MismatchException;
	void visit(Return node) throws MissingResourceException, MismatchException;
	void visit(Free node) throws MissingResourceException;
	void visit(Definition node) throws MissingResourceException;
	
	void visit(Repeat node) throws MissingResourceException, MismatchException;
	void visit(While node) throws MissingResourceException, MismatchException;
	void visit(Break node) throws MissingResourceException, MismatchException;
	void visit(Loop node) throws MissingResourceException, MismatchException;
	void visit(Do node) throws MissingResourceException, MismatchException;
	void visit(For node) throws MissingResourceException, MismatchException;
	void visit(If node) throws MissingResourceException, MismatchException;
	
	void visit(GreaterEqual node) throws MissingResourceException, MismatchException;
	void visit(LessEqual node) throws MissingResourceException, MismatchException;
	void visit(Greater node) throws MissingResourceException, MismatchException;
	void visit(Less node) throws MissingResourceException, MismatchException;
	void visit(Equal node) throws MissingResourceException, MismatchException;
	void visit(NotEqual node) throws MissingResourceException, MismatchException;
	void visit(Or node) throws MissingResourceException, MismatchException;
	void visit(Not node) throws MissingResourceException, MismatchException;
	void visit(And node) throws MissingResourceException, MismatchException;
	void visit(Add node) throws MissingResourceException, MismatchException;
	void visit(Substract node) throws MissingResourceException, MismatchException;
	void visit(Multiply node) throws MissingResourceException, MismatchException;
	void visit(Divide node) throws MissingResourceException, MismatchException;
	void visit(Constant node) throws MissingResourceException;
	void visit(Unknown node) throws MissingResourceException;
	void visit(Modulo node) throws MissingResourceException, MismatchException;
}
