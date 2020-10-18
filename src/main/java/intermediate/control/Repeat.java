package intermediate.control;

import generator.NodeVisitor;
import intermediate.BaseNode;
import intermediate.expression.ExprNode;

import java.util.List;

public class Repeat extends ControlNode {

    public Repeat(ExprNode test, List<BaseNode> body) {
        super(test, body);
    }

    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

}
