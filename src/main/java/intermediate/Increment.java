package intermediate;

import generator.NodeVisitor;
import intermediate.expression.Unknown;

public class Increment extends AddNode {

    private final Unknown variable;

    public Increment(Unknown t) {
        super('+');
        this.variable = t;
    }

    public String variable() {
        return variable.alias();
    }

    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

}
