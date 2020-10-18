package intermediate.control;

import generator.NodeVisitor;
import intermediate.Assign;
import intermediate.BaseNode;

import java.util.List;

public class Do extends ControlNode {

    private final Assign change;

    public Do(Assign change, List<BaseNode> b) {
        super(null, b);
        this.change = change;
    }

    public Assign change() {
        return change;
    }

    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

}
