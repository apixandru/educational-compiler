package intermediate;

import generator.NodeVisitor;
import intermediate.expression.ExprNode;
import lexer.Token;
import persistent.FunTable;
import persistent.Statements;

import java.util.List;


public class Function extends BaseNode {

    private final List<BaseNode> statements;
    private final Token token;
    private final String alias;

    public Function(Token t, String type, List<ExprNode> args) {
        alias = FunTable.define(t, type, args);
        token = t;
        statements = Statements.pop();
    }

    public String alias() {
        return alias;
    }

    public String name() {
        return token.value();
    }

    public List<BaseNode> statements() {
        return statements;
    }

    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

}
