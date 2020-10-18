package intermediate.control;

import generator.NodeVisitor;
import intermediate.BaseNode;

import java.util.List;

public class Loop extends ControlNode {

    public Loop(List<BaseNode> b) {
        super(null, b);
    }

    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

}
