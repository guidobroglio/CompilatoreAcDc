package exception‎;


/**
 * La classe LexicalException implementa un'eccezione per l'analisi lessicale.
 * 
 * @see exception.SyntacticException
 * @see exception.LexicalException
 * @see scanner.Scanner
 * 
 * @author Guido Lorenzo Broglio 20043973
 * 
 */
public class LexicalException extends Exception
{
	
	/**
	 * Costruttore
	 * 
	 * @param string stringa di errore.
	 */
    public LexicalException(String string) 
    {
        super(string);
    }

    /**
     * 
     * Costruttore con Throwable 
     * @param string stringa di errore.
     * @param cause eccezione causata.
     */
    public LexicalException(String string, Throwable cause) 
    {
        super(string, cause);
    }

	
}
