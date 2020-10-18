package intermediate.expression;

import generator.NodeVisitor;

public class Add extends ExprNode {

    public Add(ExprNode left, ExprNode right) {
        super(null);
        ln = left;
        rn = right;
        cleanNode();
        findType("add");
    }

    private void cleanNode() {
        if (ln instanceof Add) {
            boolean success = false;
            try {
                int intLeft = Integer.parseInt(ln.getLeft().toString());
                int intRight = Integer.parseInt(rn.toString());
                rn = ln.getRight();
                ln = new Constant(intLeft + intRight);
                success = true;
            } catch (Exception e) {
                success = false;
            }
            if (!success) {
                try {
                    int intLeft = Integer.parseInt(ln.getRight().toString());
                    int intRight = Integer.parseInt(rn.toString());
                    ln = ln.getLeft();
                    rn = new Constant(intLeft + intRight);
                    success = true;
                } catch (Exception e) {
                }
            }
        }
    }

    public String optimize() {
        String left = getLeft().toString();
        String right = getRight().toString();
        try {
            int intLeft = Integer.parseInt(left);
            int intRight = Integer.parseInt(right);
            return (intLeft + intRight) + "";
        } catch (Exception e) {
            if (left.equals("0")) {
                return right;
            } else if (right.equals("0")) {
                return left;
            }
        }
        return toString();
    }

    public String toString() {
        return getLeft() + "+" + getRight();
    }

    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

}
