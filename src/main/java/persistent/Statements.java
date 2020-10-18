package persistent;

import intermediate.AddNode;
import intermediate.BaseNode;

import java.util.ArrayList;
import java.util.List;

public class Statements {

    private static Statements statlist = null;
    private final Statements parent;
    private final List<BaseNode> stats;

    private Statements(Statements stats) {
        parent = stats;
        this.stats = new ArrayList<>();
    }

    public static List<BaseNode> push() {
        statlist = new Statements(statlist);
        return statlist.stats;
    }

    public static List<BaseNode> pop() {
        List<BaseNode> temp = new ArrayList<>();
        temp.addAll(statlist.stats);
        statlist = statlist.parent;
        return temp;
    }

    public static List<BaseNode> getAll() {
        return statlist.stats;
    }

    public static void add(BaseNode n) {
        statlist.stats.add(n);
    }

    public static void addAll(List<BaseNode> n) {
        statlist.stats.addAll(n);
    }

    public static void clear() {
        statlist = new Statements(null);
    }

    public static AddNode getChange() {
        int pos = statlist.stats.size() - 1;
        AddNode n = (AddNode) statlist.stats.get(pos);
        statlist.stats.remove(pos);
        return n;
    }

    public static void remove(Object cymbol) {
        statlist.stats.remove(cymbol);
    }

}
