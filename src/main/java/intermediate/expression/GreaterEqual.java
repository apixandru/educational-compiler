package intermediate.expression;

import generator.NodeVisitor;

public class GreaterEqual extends ExprNode {

    public GreaterEqual(ExprNode left, ExprNode right) {
        super(null);
        ln = left;
        rn = right;
    }

    public String toString() {
        return ln + " >= " + rn;
    }

    @Override
    public String optimize() {
        try {
            int l = Integer.parseInt(ln.toString());
            int r = Integer.parseInt(rn.toString());
            if (l >= r) {
                return "1";
            } else {
                return "0";
            }
        } catch (Exception e) {
            return toString();
        }
    }

    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

}
