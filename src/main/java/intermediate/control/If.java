package intermediate.control;

import generator.NodeVisitor;
import intermediate.BaseNode;
import intermediate.expression.ExprNode;

import java.util.List;

public class If extends ControlNode {

    private final List<BaseNode> elselist;

    public If(ExprNode n, List<BaseNode> b, List<BaseNode> elselist) {
        super(n, b);
        this.elselist = elselist;
    }

    public List<BaseNode> elseList() {
        return elselist;
    }

    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

}
