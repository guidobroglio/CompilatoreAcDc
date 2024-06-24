package ast;

import visitor.IVisitor;

/**
 * La classe NodeBinOp rappresenta un nodo che contiene un'operazione binaria nell'albero 
 * sintattico astratto (AST).
 * 
 * @see visitor.IVisitor
 * 
 * @author Guido Broglio 20043973
 */
public class NodeBinOp extends NodeExpr
{
    private LangOper op;
    private NodeExpr left;
    private NodeExpr right;
    
    /**
     * Costruisce un nuovo oggetto NodeBinOp con l'operatore specificato e gli operandi sinistro e destro.
     * 
     * @param op l'operatore binario
     * @param left il nodo espressione sinistro
     * @param right il nodo espressione destro
     */
    public NodeBinOp(LangOper op, NodeExpr left, NodeExpr right)
    {
        this.op = op;
        this.left = left;
        this.right = right;
    }
    
    /**
     * Restituisce l'operatore binario.
     * 
     * @return l'operatore binario
     */
    public LangOper getOp()
    {
        return this.op;
    }
    
    /**
     * Restituisce il nodo dell'espressione a sinistra.
     * 
     * @return il nodo dell'espressione a sinistra
     */
    public NodeExpr getLeft()
    {
        return this.left;
    }
    
    /**
     * Restituisce il nodo dell'espressione a destra.
     * 
     * @return il nodo dell'espressione a destra
     */
    public NodeExpr getRight()
    {
        return this.right;
    }
    
    /**
     * Imposta il nodo dell'espressione a sinistra.
     * 
     * @param exp il nodo dell'espressione a sinistra da impostare
     */
    public void setLeft(NodeExpr exp)
    {
        this.left = exp;
    }
    
    /**
     * Imposta il nodo dell'espressione a destra.
     * 
     * @param exp il nodo dell'espressione a destra da impostare
     */
    public void setRight(NodeExpr exp)
    {
        this.right = exp;
    }
    
    
    /**
     * Restituisce una rappresentazione testuale dell'istruzione di assegnamento.
     * 
     * @return una stringa che rappresenta un operazione binaria nel formato "[left op right]"
     */
    @Override
    public String toString()
    {
        return this.left.toString() + " " + this.op.toString() + " " + this.right.toString();
    }

    /**
     * Implementazione del metodo accept per consentire la visita da parte di oggetti IVisitor.
     * 
     * @param visitor l'oggetto IVisitor che visita il NodeBinOp
     */
    @Override
    public void accept(IVisitor visitor) 
    {
        visitor.visit(this);
    }
}
