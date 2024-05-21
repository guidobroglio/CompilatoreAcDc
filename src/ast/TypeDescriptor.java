package ast;

/**
 * Questa classe rappresenta un descrittore di tipo che contiene informazioni
 * sul tipo di dato (TipoTD) e un messaggio associato.
 * 
 * Questa classe include i seguenti metodi:
 * - Costruttore TypeDescriptor(TipoTD tipo): crea un TypeDescriptor con il tipo specificato e nessun messaggio.
 * - Costruttore TypeDescriptor(TipoTD tipo, String msg): crea un TypeDescriptor con il tipo e il messaggio specificati.
 * - getMsg(): restituisce il messaggio associato al TypeDescriptor.
 * - getTipo(): restituisce il tipo di dato (TipoTD) del TypeDescriptor.
 * - setMsg(String msg): imposta il messaggio associato al TypeDescriptor.
 * - setTipo(TipoTD tipo): imposta il tipo di dato (TipoTD) del TypeDescriptor.
 * - compatibile(TypeDescriptor tD): verifica se questo TypeDescriptor è compatibile con un altro TipoTD.
 * 
 * I tipi di dato (TipoTD) sono:
 * - INT: il tipo intero.
 * - FLOAT: il tipo floating point.
 * - OK: utilizzato per indicare un'esecuzione corretta o un'espressione corretta.
 * - ERROR: utilizzato per indicare un errore o un'espressione non corretta.
 * 
 * @author Guido Lorenzo Broglio
 * @version 1.0
 * @see TipoTD
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
