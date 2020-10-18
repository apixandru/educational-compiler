package intermediate;

import generator.NodeVisitor;
import intermediate.expression.ExprNode;
import lexer.Token;


public class Return extends BaseNode {

    private final String varType;
    private final ExprNode expression;
    private final Token t;

    public Return(Token t, ExprNode expression) {
        this.t = t;
        this.expression = expression;
        varType = expression.type();

    }

    public String varType() {
        return varType;
    }

    public ExprNode expression() {
        return expression;
    }

    public Token token() {
        return t;
    }

    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

}
