package ast;

import visitor.IVisitor;

/**
 * La classe NodeConvert rappresenta un nodo che contiene un'espressione e un operatore di conversione nel
 * 	albero sintattico astratto (AST).
 * 
 * @see visitor.IVisitor
 * 
 * @author Guido Broglio 20043973
 */
public class NodeConvert extends NodeExpr
{
    private NodeExpr expr;
    private LangOper op;
    
    /**
     * Costruisce un nuovo oggetto NodeConvert con l'espressione specificata e l'operatore di conversione.
     * 
     * @param expr l'espressione da convertire
     * @param op l'operatore di conversione
     */
    public NodeConvert(NodeExpr expr, LangOper op)
    {
        this.expr = expr;
        this.op = op;
    }
    
    /**
     * Restituisce l'espressione da convertire.
     * 
     * @return l'espressione da convertire
     */
    public NodeExpr getExpr()
    {
        return this.expr;
    }
    
    /**
     * Restituisce l'operatore di conversione.
     * 
     * @return l'operatore di conversione
     */
    public LangOper getOp()
    {
        return op;
    }

    @Override
    public void accept(IVisitor visitor) 
    {
        visitor.visit(this);     
    }
}
