package intermediate;


public abstract class AddNode extends BaseNode {

    private final char sign;

    public AddNode(char sign) {
        this.sign = sign;
    }

    public char sign() {
        return sign;
    }

}
