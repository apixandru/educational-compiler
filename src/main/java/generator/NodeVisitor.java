package generator;

import intermediate.*;
import intermediate.control.*;
import intermediate.expression.*;

public interface NodeVisitor {

    void visit(Assign node);

    void visit(Increment node);

    void visit(Decrement node);

    void visit(Call node);

    void visit(Function node);

    void visit(Run node);

    void visit(Return node);

    void visit(Free node);

    void visit(Definition node);

    void visit(Repeat node);

    void visit(While node);

    void visit(Break node);

    void visit(Loop node);

    void visit(Do node);

    void visit(For node);

    void visit(If node);

    void visit(GreaterEqual node);

    void visit(LessEqual node);

    void visit(Greater node);

    void visit(Less node);

    void visit(Equal node);

    void visit(NotEqual node);

    void visit(Or node);

    void visit(Not node);

    void visit(And node);

    void visit(Add node);

    void visit(Substract node);

    void visit(Multiply node);

    void visit(Divide node);

    void visit(Constant node);

    void visit(Unknown node);

    void visit(Modulo node);

}
