package intermediate;

import generator.NodeVisitor;

public abstract class BaseNode {

    public abstract void accept(NodeVisitor visitor);

}	
