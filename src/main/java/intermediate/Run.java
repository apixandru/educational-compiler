package intermediate;

import generator.NodeVisitor;
import intermediate.expression.Call;
import intermediate.expression.ExprNode;
import lexer.Token;

import java.util.ArrayList;

public class Run extends Call {

    public Run(Token t, ArrayList<ExprNode> args) {
        super(t, args);
    }

    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

}
