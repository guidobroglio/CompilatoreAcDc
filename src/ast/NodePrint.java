package ast;

import visitor.IVisitor;

/**
 * La classe NodePrint rappresenta un nodo per l'istruzione di stampa nel linguaggio.
 * 
 * Estende la classe NodeStm e rappresenta un'istruzione di stampa all'interno 
 * dell'albero sintattico astratto (AST).
 * 
 * @see ast.NodeStm
 * @see visitor.IVisitor
 * 
 * @author Guido Broglio 20043973
 */
public class NodePrint extends NodeStm
{
    private NodeId id;
    
    /**
     * Costruisce un nuovo oggetto NodePrint con l'identificatore specificato.
     * 
     * @param id l'identificatore da stampare
     */
    public NodePrint(NodeId id)
    {
        this.id = id;
    }
    
    /**
     * Restituisce l'identificatore da stampare.
     * 
     * @return l'identificatore da stampare
     */
    public NodeId getId()
    {
        return this.id;
    }
    
    /**
     * Restituisce una rappresentazione testuale dell'istruzione di stampa.
     * 
     * @return una stringa che rappresenta l'istruzione di stampa
     */
    @Override
    public String toString()
    {
        return "Print: [" + this.id.toString() + "]"; 
    }
    
    /**
     * Accetta un visitatore per la visita all'istruzione di stampa.
     * 
     * @param visitor il visitatore da accettare
     */
    @Override
    public void accept(IVisitor visitor)
    {
        visitor.visit(this);
    }
}
