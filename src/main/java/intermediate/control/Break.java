package intermediate.control;

import generator.NodeVisitor;

public class Break extends ControlNode {

    public Break() {
        super(null, null);
    }

    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

}
