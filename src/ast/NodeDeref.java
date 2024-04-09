package ast;

public class NodeDeref extends NodeExpr
{
	private NodeId id;
	
	public NodeDeref(NodeId id)
	{
		this.id = id;
	}
	
	public NodeId getId()
	{
		return this.id;
	}
	
	@Override
	public String toString()
	{
		return "Deref: [" + this.id.toString() + "]";
	}
}
