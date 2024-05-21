package visitor;

import ast.*;

/**
 * Interfaccia per il pattern Visitor.
 * Definisce i metodi per visitare diversi tipi di nodi nell'albero sintattico.
 * Ogni tipo di nodo nell'albero ha un metodo corrispondente in questa interfaccia.
 *
 * @see NodeProgram
 * @see NodeId
 * @see NodeDecl
 * @see NodeBinOp
 * @see NodePrint
 * @see NodeDeref
 * @see NodeAssign
 * @see NodeConvert
 * @see NodeCost
 * 
 * @autor Guido Lorenzo Broglio 20043973
 */
public interface IVisitor 
{
    /**
     * Visita un nodo di tipo NodeProgram.
     *
     * @param node il nodo di tipo NodeProgram
     */
    public abstract void visit(NodeProgram node);

    /**
     * Visita un nodo di tipo NodeId.
     *
     * @param node il nodo di tipo NodeId
     */
    public abstract void visit(NodeId node);

    /**
     * Visita un nodo di tipo NodeDecl.
     *
     * @param node il nodo di tipo NodeDecl
     */
    public abstract void visit(NodeDecl node);

    /**
     * Visita un nodo di tipo NodeBinOp.
     *
     * @param node il nodo di tipo NodeBinOp
     */
    public abstract void visit(NodeBinOp node);

    /**
     * Visita un nodo di tipo NodePrint.
     *
     * @param node il nodo di tipo NodePrint
     */
    public abstract void visit(NodePrint node);

    /**
     * Visita un nodo di tipo NodeDeref.
     *
     * @param node il nodo di tipo NodeDeref
     */
    public abstract void visit(NodeDeref node);

    /**
     * Visita un nodo di tipo NodeAssign.
     *
     * @param node il nodo di tipo NodeAssign
     */
    public abstract void visit(NodeAssign node);

    /**
     * Visita un nodo di tipo NodeConvert.
     *
     * @param node il nodo di tipo NodeConvert
     */
    public abstract void visit(NodeConvert node);

    /**
     * Visita un nodo di tipo NodeCost.
     *
     * @param node il nodo di tipo NodeCost
     */
    public abstract void visit(NodeCost node);
}
