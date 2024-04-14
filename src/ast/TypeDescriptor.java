package ast;

public class TypeDescriptor 
{
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
		return this.tipo;
	}
	
	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	
	public void setTipo(TipoTD tipo)
	{
		this.tipo = tipo;
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
