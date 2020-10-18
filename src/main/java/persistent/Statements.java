package persistent;

import intermediate.AddNode;
import intermediate.BaseNode;

import java.util.ArrayList;

public class Statements {
	private Statements parent;
	private ArrayList<BaseNode> stats;
	
	private static Statements statlist = null; 
	
	private Statements(Statements stats){
		parent = stats;
		this.stats = new ArrayList<BaseNode>(); 
	}
	
	public static ArrayList<BaseNode> push(){
		statlist = new Statements(statlist);
		return statlist.stats;
	}
	
	public static ArrayList<BaseNode> pop(){
		ArrayList<BaseNode> temp = new ArrayList<BaseNode>();
		temp.addAll(statlist.stats);
		statlist = statlist.parent;
		return temp;
	}
	
	public static ArrayList<BaseNode> getAll(){
		return statlist.stats;
	}
	
	public static void add(BaseNode n){
		statlist.stats.add(n);
	}

	public static void addAll(ArrayList<BaseNode> n){
		statlist.stats.addAll(n);
	}

	public static void clear(){
		statlist = new Statements(null);
	}
	
	public static AddNode getChange(){
		int pos = statlist.stats.size() - 1;
		AddNode n = (AddNode) statlist.stats.get(pos);
		statlist.stats.remove(pos);
		return n;
	}
	
	public static void remove(Object cymbol){
		statlist.stats.remove(cymbol);
	}
}
