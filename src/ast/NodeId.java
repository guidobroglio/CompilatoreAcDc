package ast;

import visitor.*;
import symbolTable.*;

public class NodeId extends NodeAST
{
	private String nome;
	private Attributes definition;
	
	public NodeId(String nome)
	{
		this.nome=nome;
	}
	
	public String getNome() 
	{ 
		return this.nome; 
	}
	
	public Attributes getDefinition()
	{
		return definition;
	}

	public void setDefinition(Attributes definition)
	{
		this.definition=definition;
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
