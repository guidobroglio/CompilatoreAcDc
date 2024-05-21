package ast;

import visitor.IVisitor;

/**
 * La classe NodeDeref rappresenta un nodo per la dereferenziazione di un identificatore 
 * 	all'interno dell'albero sintattico astratto (AST).
 * 
 * Estende la classe NodeExpr e rappresenta un'operazione di dereferenziazione su un identificatore.
 * 
 * @see ast.NodeExpr
 * @see visitor.IVisitor
 * 
 * @author Guido Broglio 20043973
 */
public class NodeDeref extends NodeExpr
{
    private NodeId id;
    
    /**
     * Costruisce un nuovo oggetto NodeDeref con l'identificatore specificato.
     * 
     * @param id l'identificatore da dereferenziare
     */
    public NodeDeref(NodeId id)
    {
        this.id = id;
    }
    
    /**
     * Restituisce l'identificatore oggetto NodeId dereferenziato.
     * 
     * @return l'identificatore oggetto NodeId dereferenziato
     */
    public NodeId getId()
    {
        return this.id;
    }
    
    /**
     * Restituisce una stringa che rappresenta il nodo di dereferenziazione,
     * 	con l'identificatore associato.
     * 
     * @return una stringa che rappresenta il nodo di dereferenziazione
     */
    @Override
    public String toString()
    {
        return "Deref: [" + this.id.toString() + "]";
    }

    /**
     * Implementazione del metodo accept per consentire la visita del nodo
     * 	da parte di un oggetto visitor.
     * 
     * @param visitor l'oggetto visitor che visita il nodo
     */
    @Override
    public void accept(IVisitor visitor) 
    {
        visitor.visit(this);
    }
}
