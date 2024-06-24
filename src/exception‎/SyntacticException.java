package exception‎;

/**
 * La classe SyntacticException implementa un'eccezione per l'analisi sintattica.
 * 
 * @author Guido Lorenzo Broglio 20043973
 *
 */
public class SyntacticException extends Exception 
{

	/**
	 * Costruttore della classe SyntacticException con parametro stringa.
	 * 
	 * @param string 
	 */
    public SyntacticException(String string) 
    {
        super(string);
    }

	/**
	 * Costruttore della classe SyntacticException con parametro stringa e Throwable
	 * 
	 * @param string 
	 * @param cause
	 */
    public SyntacticException(String string, Throwable cause) 
    {
        super(string, cause);
    }
}