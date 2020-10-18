package persistent;

import intermediate.expression.ExprNode;

import java.util.ArrayList;
import java.util.List;

public class Terms {

    private static Terms t = new Terms(null);
    private final Terms parent;
    private final List<ExprNode> terms;

    private Terms(Terms t) {
        parent = t;
        terms = new ArrayList<>();
    }

    public static ExprNode getLast() {
        return t.terms.get(t.terms.size() - 1);
    }

    public static void push() {
        t = new Terms(t);
    }

    public static ExprNode getPenultimate() {
        return t.terms.get(t.terms.size() - 2);
    }

    public static void add(ExprNode n) {
        t.terms.add(n);
    }

    public static void removePenultimate() {
        t.terms.remove(t.terms.size() - 2);
    }

    public static void pop() {
        t = t.parent;
    }

}
