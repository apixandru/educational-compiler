package intermediate;

import generator.NodeVisitor;
import intermediate.expression.Unknown;

public class Decrement extends AddNode {

    private final Unknown variable;

    public Decrement(Unknown t) {
        super('-');
        this.variable = t;
    }

    public String variable() {
        return variable.alias();
    }

    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

}
