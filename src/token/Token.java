package token;

public class Token {

	private int riga;
	private TokenType tipo;
	private String val;
	
	public Token(TokenType tipo, int riga, String val) 
	{
		this.tipo=tipo;
		this.riga=riga;
		this.val=val;
	}
	
	public Token(TokenType tipo, int riga) 
	{
		this(tipo, riga, null);
	}

    // Getters per i campi
	public String toString() 
	{
	    return String.format("<%s,r:%d%s>", getTipo(), getRiga(), (getVal() != null ? "," + getVal() : ""));
	}

	public int getRiga() {
		return riga;
	}

	public TokenType getTipo() {
		return tipo;
	}

	public String getVal() {
		return val;
	}    
}
