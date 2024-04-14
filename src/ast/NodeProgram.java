package ast;

import java.util.ArrayList;

import visitor.IVisitor;

public class NodeProgram extends NodeAST
{
	private ArrayList<NodeDecSt> decSts;
	
	public NodeProgram(ArrayList<NodeDecSt> decSts)
	{
		this.decSts = decSts;
	}
	
	public ArrayList<NodeDecSt> getDecSts()
	{
		return this.decSts;
	}
	
	@Override
	public String toString()
	{
		return "Program [decSts= " + decSts + "]";
	}

	@Override
	public void accept(IVisitor visitor) 
	{
		visitor.visit(this);
	}
	
}
