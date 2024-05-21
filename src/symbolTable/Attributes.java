package symbolTable;
import ast.LangType;

/**
 * Questa classe rappresenta gli attributi associati a un identificatore nella tabella dei simboli.
 * Gli attributi includono il tipo del linguaggio e, opzionalmente, un registro.
 * 
 * @see LangType
 * 
 * @autor Guido Lorenzo Broglio 20043973
 */
public class Attributes 
{
    /** Il tipo del linguaggio associato all'identificatore */
    private LangType type;

    /** Il registro associato all'identificatore */
    private char registro;
    
    /**
     * Costruttore che inizializza un oggetto Attributes con il tipo specificato.
     * 
     * @param type il tipo del linguaggio associato all'identificatore
     */
    public Attributes(LangType type)
    {
        this.type = type;
    }
    
    /**
     * Costruttore che inizializza un oggetto Attributes con il tipo e il registro specificati.
     * 
     * @param type il tipo del linguaggio associato all'identificatore
     * @param registro il registro associato all'identificatore
     */
    public Attributes(LangType type, char registro)
    {
        this.type = type;
        this.registro = registro;
    }
        
    /**
     * Restituisce il tipo del linguaggio associato all'identificatore.
     * 
     * @return il tipo del linguaggio
     */
    public LangType getType()
    {
        return type;
    }
    
    /**
     * Imposta il registro associato all'identificatore.
     * 
     * @param registro il registro da associare all'identificatore
     */
    public void setRegistro(char registro)
    {
        this.registro = registro;
    }
    
    /**
     * Restituisce il registro associato all'identificatore.
     * 
     * @return il registro
     */
    public char getRegistro()
    {
        return registro;
    }
}
