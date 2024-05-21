package ast;

import visitor.IVisitor;

/**
 * La classe NodeDecl rappresenta un nodo che contiene una dichiarazione di variabile nel
 * 	albero sintattico astratto (AST).
 * 
 * @see visitor.IVisitor
 * 
 * @author Guido Broglio 20043973
 */
public class NodeDecl extends NodeDecSt
{
    private NodeId id;
    private LangType type;
    private NodeExpr init;
    
    /**
     * Costruisce un nuovo oggetto NodeDecl con l'identificatore, il tipo e l'espressione di inizializzazione specificati.
     * 
     * @param id l'identificatore della variabile dichiarata
     * @param type il tipo della variabile dichiarata
     * @param init l'espressione di inizializzazione della variabile dichiarata
     */
    public NodeDecl(NodeId id, LangType type, NodeExpr init)
    {
        this.id = id;
        this.type = type;
        this.init = init;
    }
    
    /**
     * Restituisce l'identificatore della variabile dichiarata.
     * 
     * @return l'identificatore della variabile dichiarata
     */
    public NodeId getId()
    {
        return this.id;
    }
    
    /**
     * Restituisce il tipo della variabile dichiarata.
     * 
     * @return il tipo della variabile dichiarata
     */
    public LangType getType()
    {
        return this.type;
    }
    
    /**
     * Restituisce l'espressione di inizializzazione della variabile dichiarata.
     * 
     * @return l'espressione di inizializzazione della variabile dichiarata
     */
    public NodeExpr getInit()
    {
        return this.init;
    }
    
    @Override
    public String toString()
    {
        return "Decl: " + this.getId() + ", type: " + this.getType() + ", init: " + this.getInit();
    }

    @Override
    public void accept(IVisitor visitor) 
    {
        visitor.visit(this);
    }
}
