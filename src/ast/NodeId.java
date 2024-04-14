package ast;

import visitor.*;

public class NodeId extends NodeAST
{
	private String nome;
	
	public NodeId(String nome)
	{
		this.nome=nome;
	}
	
	public String getNome() 
	{ 
		return this.nome; 
	}

	@Override
	public String toString() 
	{
		return "ID: " + this.nome;
	}

	@Override
	public void accept(IVisitor visitor) 
	{
		visitor.visit(this);		
	}

}
