package symbolTable;
import java.util.*;
import ast.LangType;

public class Attributes 
{
	private LangType type;
	
	public Attributes(LangType type)
	{
		this.type = type;
	}
	
	public LangType getType()
	{
		return type;
	}
}
