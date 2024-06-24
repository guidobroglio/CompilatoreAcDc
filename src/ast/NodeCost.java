package ast;

import visitor.IVisitor;

/**
 * La classe NodeCost rappresenta un nodo che contiene un valore costante e il suo tipo nel
 * albero sintattico astratto (AST).
 * 
 * @see visitor.IVisitor
 * 
 * @author Guido Broglio 20043973
 */
public class NodeCost extends NodeExpr
{
    private String value;
    private LangType type;
    
    /**
     * Costruisce un nuovo oggetto NodeCost con il valore costante specificato e il tipo.
     * 
     * @param value il valore costante
     * @param type il tipo del valore costante
     */
    public NodeCost(String value, LangType type)
    {
        this.value = value;
        this.type = type;
    }
    
    /**
     * Restituisce il valore costante.
     * 
     * @return il valore costante
     */
    public String getValue()
    {
        return this.value;
    }
    
    /**
     * Restituisce il tipo del valore costante.
     * 
     * @return il tipo del valore costante
     */
    public LangType getType()
    {
        return this.type;
    }
    
	/**
	 * Restituisce una stringa che rappresenta il valore costante.
	 * 
	 * @return una stringa che rappresenta il valore costante
	 */
    @Override
    public String toString()
    {
        return this.value + " " + value;
    }
    
	/**
	 * Implementazione del metodo accept per consentire la visita da parte di oggetti IVisitor.
	 * 
	 * @param visitor l'oggetto IVisitor che visita il NodeCost
	 */
    @Override
    public void accept(IVisitor visitor) 
    {
        visitor.visit(this);
    }
}
