package ast;

import visitor.IVisitor;

/**
 * La classe astratta NodeAST rappresenta un nodo generico nell'albero sintattico astratto (AST).
 * 
 * @see visitor.IVisitor
 * 
 * @author Guido Broglio 20043973
 */
public abstract class NodeAST 
{
    /**
     * Metodo astratto che consente a un oggetto IVisitor di visitare questo nodo AST.
     * 
     * @param visitor l'oggetto IVisitor che visita questo nodo
     */
    public abstract void accept(IVisitor visitor);
}
