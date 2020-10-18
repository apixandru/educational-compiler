package intermediate.expression;

import exceptions.MismatchException;
import intermediate.BaseNode;
import persistent.FunTable;


public abstract class ExprNode extends BaseNode {

    protected ExprNode ln;
    protected ExprNode rn;

    private String type;

    public ExprNode(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }

    protected void setType(String type) {
        this.type = type;
    }

    public ExprNode getLeft() {
        return ln;
    }

    public ExprNode getRight() {
        return rn;
    }

    protected void findType(String x) {
        String type1 = ln.type();
        String type2 = rn.type();
        String error = "Cannot " + x + " types " + type1 + " and " + type2 + ".";
        if (type1 == null) {
            FunTable.setInt(FunTable.symbols().get(((Call) ln).name()).name());
        } else if (!type1.equals("int")) {
            throw new MismatchException(error);
        }
        if (type2 == null) {
            FunTable.setInt(FunTable.symbols().get(((Call) rn).name()).name());
        } else if (!type2.equals("int")) {
            throw new MismatchException(error);
        }
        setType("int");
    }

    public abstract String toString();

    public abstract String optimize();

}
