package symbolTable;
import ast.LangType;

public class Attributes 
{
	private LangType type;
	private char registro;
	
	public Attributes(LangType type)
	{
		this.type = type;
	}
	
	public LangType getType()
	{
		return type;
	}
	
	public void setRegistro(char registro)
	{
		this.registro = registro;
	}
	
	public char getRegistro()
	{
		return registro;
	}
}