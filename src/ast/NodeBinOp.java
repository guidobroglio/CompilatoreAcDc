package ast;

public class NodeBinOp extends NodeExpr
{
	private LangOper op;
	private NodeExpr left;
	private NodeExpr right;
	
	public NodeBinOp(LangOper op, NodeExpr left, NodeExpr right)
	{
		this.op = op;
		this.left = left;
		this.right = right;
	}
	
	public LangOper getOp()
	{
		return this.op;
	}
	
	public NodeExpr getLeft()
	{
		return this.left;
	}
	
	public NodeExpr getRight()
	{
		return this.right;
	}
	
	public void setLeft(NodeExpr exp)
	{
		this.left = exp;
	}
	
	public void setRight(NodeExpr exp)
	{
		this.right = exp;
	}
	
	@Override
	public String toString()
	{
		return this.left.toString() + " " + this.op.toString() + " " + this.right.toString();
	}
}
