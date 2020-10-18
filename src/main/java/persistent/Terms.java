package persistent;

import intermediate.expression.ExprNode;

import java.util.ArrayList;

public class Terms {

    private static Terms t = new Terms(null);
    private final Terms parent;
    private final ArrayList<ExprNode> terms;

    private Terms(Terms t) {
        parent = t;
        terms = new ArrayList<ExprNode>();
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
