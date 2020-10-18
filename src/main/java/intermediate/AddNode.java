package intermediate;


public abstract class AddNode extends BaseNode{
	private char sign;
	
	public AddNode(char sign) {
		this.sign = sign;
	}

	public char sign(){
		return sign;
	}
	
}
