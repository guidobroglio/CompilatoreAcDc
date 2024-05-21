package ast;

import visitor.IVisitor;

/**
 * La classe NodeBinOp rappresenta un nodo che contiene un'operazione binaria nell'albero sintattico astratto (AST).
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
     * Restituisce il nodo espressione sinistro.
     * 
     * @return il nodo espressione sinistro
     */
    public NodeExpr getLeft()
    {
        return this.left;
    }
    
    /**
     * Restituisce il nodo espressione destro.
     * 
     * @return il nodo espressione destro
     */
    public NodeExpr getRight()
    {
        return this.right;
    }
    
    /**
     * Imposta il nodo espressione sinistro.
     * 
     * @param exp il nodo espressione sinistro da impostare
     */
    public void setLeft(NodeExpr exp)
    {
        this.left = exp;
    }
    
    /**
     * Imposta il nodo espressione destro.
     * 
     * @param exp il nodo espressione destro da impostare
     */
    public void setRight(NodeExpr exp)
    {
        this.right = exp;
    }
    
    @Override
    public String toString()
    {
        return this.left.toString() + " " + this.op.toString() + " " + this.right.toString();
    }

    @Override
    public void accept(IVisitor visitor) 
    {
        visitor.visit(this);
    }
}
