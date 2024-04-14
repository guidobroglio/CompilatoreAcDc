package ast;

import visitor.IVisitor;

public class NodeConvert extends NodeExpr
{
	private NodeExpr expr;
	private LangOper op;
	
	public NodeConvert(NodeExpr expr, LangOper op)
	{
		this.expr = expr;
		this.op = op;
	}
	
	public NodeExpr getExpr()
	{
		return expr;
	}
	
	public LangOper getOp()
	{
		return op;
	}

	@Override
	public void accept(IVisitor visitor) 
	{
		visitor.visit(this);		
	}
}
