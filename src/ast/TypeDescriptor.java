package ast;

public class TypeDescriptor 
{
	public enum TipoTD
	{
		INT, FLOAT, OK, ERROR
	}
	
	private TipoTD tipo;
	private String msg;
	
	public TypeDescriptor(TipoTD tipo)
	{
		this.tipo = tipo;
	}
	
	public TypeDescriptor(TipoTD tipo, String msg)
	{
		this.tipo = tipo;
		this.msg = msg;
	}
	
	public String getMsg()
	{
		return msg;
	}
	
	public TipoTD getTipo()
	{
		return tipo;
	}
	
	public boolean compatibile(TipoTD tD)
	{
		if(tipo==tD || tipo==TipoTD.FLOAT && tD == TipoTD.INT)
		{
			return true;
		}
		else
		{
			return false;
		}
	}	
}
