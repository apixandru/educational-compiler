package persistent;

import intermediate.expression.ExprNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Terms {

    private final LinkedList<List<ExprNode>> stack = new LinkedList<>();
    private final List<ExprNode> terms;

    public Terms() {
        terms = new ArrayList<>();
    }

    public ExprNode getLast() {
        return terms.get(terms.size() - 1);
    }

    public void push() {
        stack.push(new ArrayList<>(terms));
        terms.clear();
    }

    public ExprNode getPenultimate() {
        return terms.get(terms.size() - 2);
    }

    public void add(ExprNode n) {
        terms.add(n);
    }

    public void removePenultimate() {
        terms.remove(terms.size() - 2);
    }

    public void pop() {
        terms.clear();
        terms.addAll(stack.pop());
    }

}
