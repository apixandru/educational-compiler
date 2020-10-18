package generator;

public class Label {

	private String label;
	private Label parent;
	
	public Label(Label parent, String label){
		this.parent = parent;
		this.label = label;
	}
	
	public Label parent(){
		return parent;
	}
	
	public String label(){
		return label;
	}
}
