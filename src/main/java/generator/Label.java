package generator;

public class Label {

    private final String label;
    private final Label parent;

    public Label(Label parent, String label) {
        this.parent = parent;
        this.label = label;
    }

    public Label parent() {
        return parent;
    }

    public String label() {
        return label;
    }

}
