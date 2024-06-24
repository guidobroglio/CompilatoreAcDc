package token;

/**
 * La classe Token rappresenta un token generato dal scanner
 * 
 * @author Guido Lorenzo Broglio mat 20043973
 *
 */
public class Token 
{

	private int riga;
	private TokenType tipo;
	private String val;
	
	// Costruttore
	public Token(TokenType tipo, int riga, String val) 
	{
		this.tipo=tipo;
		this.riga=riga;
		this.val=val;
	}
	
	// Costruttore
	public Token(TokenType tipo, int riga) 
	{
		this(tipo, riga, null);
	}

    // Getters per i campi
	public String toString() 
	{
	    return String.format("<%s,r:%d%s>", getTipo(), getRiga(), (getVal() != null ? "," + getVal() : ""));
	}

	// Getters per il campo riga 
	public int getRiga() 
	{
		return riga;
	}

	// Getters per il campo tipo
	public TokenType getTipo() 
	{
		return tipo;
	}

	// Getters per il campo val
	public String getVal() 
	{
		return val;
	}    
}
