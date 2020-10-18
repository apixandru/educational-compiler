package intermediate;

import generator.NodeVisitor;


public class Definition extends BaseNode {

    private final String name;
    private final String value;

    public Definition(String name) {
        this.name = name;
        this.value = "$0";

    }

    public String alias() {
        return name;
    }

    public String value() {
        return value;
    }

    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

}
