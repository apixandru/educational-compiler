package intermediate.expression;


import generator.NodeVisitor;
import lexer.Token;
import persistent.FunBol;
import persistent.FunTable;

import java.util.ArrayList;

public class Call extends ExprNode {

    private final ArrayList<ExprNode> args;
    private final Token token;
    private final String alias;

    public Call(Token token, ArrayList<ExprNode> args) {
        super(null);
        this.token = token;
        this.args = args;
        String type = getType(token);
        alias = FunTable.call(token, args, type);
        setType(type);
    }

    public String toString() {
        return name() + "()";
    }

    public String name() {
        return token.value();
    }

    public String alias() {
        return alias;
    }

    public ArrayList<ExprNode> args() {
        return args;
    }

    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public String optimize() {
        return toString();
    }

    private String getType(Token t) {
        FunBol fb = FunTable.symbols().get(t.value());
        if (fb == null) {
            return null;
        }
        return fb.type();
    }

}
