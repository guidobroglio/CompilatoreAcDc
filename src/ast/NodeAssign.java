package ast;

import visitor.IVisitor;

/**
 * La classe NodeAssign rappresenta un'istruzione di assegnamento in un albero sintattico astratto.
 * Un'istruzione di assegnamento assegna un valore a una variabile identificata da un NodeId.
 * 
 * @see ast.NodeId
 * @see ast.NodeExpr
 * @see visitor.IVisitor
 * 
 * @author Guido Broglio 20043973
 */
public class NodeAssign extends NodeStm
{
    private NodeId id; // Il NodeId che identifica la variabile a cui viene assegnato un valore.
    private NodeExpr expr; // L'espressione che rappresenta il valore assegnato alla variabile.

    /**
     * Costruisce un nuovo oggetto NodeAssign con l'NodeId e l'espressione specificati.
     * 
     * @param id   l'NodeId che identifica la variabile a cui viene assegnato un valore
     * @param expr l'espressione che rappresenta il valore assegnato alla variabile
     */
    public NodeAssign(NodeId id, NodeExpr expr)
    {
        this.id = id;
        this.expr = expr;
    }

    /**
     * Restituisce l'NodeId che identifica la variabile a cui viene assegnato un valore.
     * 
     * @return l'NodeId che identifica la variabile
     */
    public NodeId getId()
    {
        return this.id;
    }

    /**
     * Restituisce l'espressione che rappresenta il valore assegnato alla variabile.
     * 
     * @return l'espressione che rappresenta il valore assegnato
     */
    public NodeExpr getExpr()
    {
        return this.expr;
    }

    /**
     * Restituisce una rappresentazione testuale dell'istruzione di assegnamento.
     * 
     * @return una stringa che rappresenta l'assegnamento nel formato "[NodeId = Expr]"
     */
    @Override
    public String toString()
    {
        return "Assign: [" + this.id.toString() + " = " + this.expr.toString() + "]";
    }

    /**
     * Implementazione del metodo accept per consentire la visita da parte di oggetti IVisitor.
     * 
     * @param visitor l'oggetto IVisitor che visita il NodeAssign
     */
    @Override
    public void accept(IVisitor visitor) 
    {
        visitor.visit(this);       
    }
}
