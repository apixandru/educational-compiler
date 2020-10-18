package intermediate;

import exceptions.MismatchException;
import generator.NodeVisitor;
import intermediate.expression.Call;
import intermediate.expression.ExprNode;
import intermediate.expression.Unknown;
import persistent.FunTable;

public class Assign extends AddNode {

    private final Unknown variable;
    private final ExprNode expression;

    public Assign(Unknown variable, ExprNode ex, char sign) {
        super(sign);
        this.variable = variable;
        expression = ex;
        checkType();
    }

    public Assign(Unknown variable, ExprNode ex) {
        this(variable, ex, ' ');
    }

    private void checkType() {
        String type1 = variable.type();
        String type2 = expression.type();
        if (type2 == null) {
            String x = ((Call) expression).name();
            FunTable.setInt(x);
        } else if (!type1.equals(type2)) {
            throw new MismatchException("Assign type " + type2 + " to type " + type1 + ".");
        }
    }

    public Unknown variable() {
        return variable;
    }

    public ExprNode expression() {
        return expression;
    }

    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

}
