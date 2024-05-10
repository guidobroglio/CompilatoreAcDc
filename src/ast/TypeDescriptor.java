package ast;

/**
 * Questa classe rappresenta un descrittore di tipo che contiene informazioni
 * sul tipo di dato (TipoTD) e un messaggio associato.
 * @author Guido Lorenzo Broglio 20043973
 */
public class TypeDescriptor 
{

    private TipoTD tipo; // Il tipo di dato
    private String msg;  // Il messaggio associato al tipo

    /**
     * Costruttore per creare un TypeDescriptor con il tipo specificato e nessun messaggio.
     * 
     * @param tipo Il tipo di dato (TipoTD) del TypeDescriptor
     */
    public TypeDescriptor(TipoTD tipo) 
    {
        this.tipo = tipo;
        this.msg = "";
    }

    /**
     * Costruttore per creare un TypeDescriptor con il tipo e il messaggio specificati.
     * 
     * @param tipo Il tipo di dato (TipoTD) del TypeDescriptor
     * @param msg  Il messaggio associato al tipo
     */
    public TypeDescriptor(TipoTD tipo, String msg) 
    {
        this.tipo = tipo;
        this.msg = msg;
    }

    /**
     * Restituisce il messaggio associato al TypeDescriptor.
     * 
     * @return Il messaggio associato al tipo
     */
    public String getMsg() 
    {
        return msg;
    }

    /**
     * Restituisce il tipo di dato (TipoTD) del TypeDescriptor.
     * 
     * @return Il tipo di dato (TipoTD)
     */
    public TipoTD getTipo() 
    {
        return this.tipo;
    }

    /**
     * Imposta il messaggio associato al TypeDescriptor.
     * 
     * @param msg Il nuovo messaggio da associare al tipo
     */
    public void setMsg(String msg) 
    {
        this.msg = msg;
    }

    /**
     * Imposta il tipo di dato (TipoTD) del TypeDescriptor.
     * 
     * @param tipo Il nuovo tipo di dato da impostare
     */
    public void setTipo(TipoTD tipo) 
    {
        this.tipo = tipo;
    }

    /**
     * Verifica se questo TypeDescriptor è compatibile con un altro TipoTD.
     * 
     * @param tD Il tipo di dato (TipoTD) da confrontare con questo TypeDescriptor
     * @return true se i tipi sono uguali o se tipo è FLOAT e tD è INT, false altrimenti
     */
    public boolean compatibile(TypeDescriptor tD) 
    {
    	boolean cond1=(tipo==tD.getTipo() && tipo!=TipoTD.ERROR && tD.getTipo()!=TipoTD.ERROR);
    	boolean cond2=(tipo==TipoTD.FLOAT && tD.getTipo()==TipoTD.INT);
		return cond1 || cond2;
    }
}
