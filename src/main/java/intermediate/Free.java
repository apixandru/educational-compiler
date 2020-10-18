package intermediate;

import generator.NodeVisitor;


public class Free extends BaseNode {

    private final int ammount;

    public Free(int ammount) {
        this.ammount = ammount;

    }

    public int ammount() {
        return ammount * 4;
    }

    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

}
