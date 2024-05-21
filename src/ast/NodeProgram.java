package ast;

import java.util.ArrayList;
import visitor.IVisitor;

/**
 * La classe NodeProgram rappresenta un nodo per il programma nel linguaggio.
 * 
 * Estende la classe NodeAST e rappresenta l'intero programma come un elenco 
 * di dichiarazioni e istruzioni all'interno dell'albero sintattico astratto (AST).
 * 
 * @see ast.NodeAST
 * @see visitor.IVisitor
 * 
 * @author Guido Broglio 20043973
 */
public class NodeProgram extends NodeAST
{
    private ArrayList<NodeDecSt> decSts;
    
    /**
     * Costruisce un nuovo oggetto NodeProgram con l'elenco specificato di dichiarazioni e istruzioni.
     * 
     * @param decSts l'elenco di dichiarazioni e istruzioni del programma
     */
    public NodeProgram(ArrayList<NodeDecSt> decSts)
    {
        this.decSts = decSts;
    }
    
    /**
     * Restituisce l'elenco di dichiarazioni e istruzioni del programma.
     * 
     * @return l'elenco di dichiarazioni e istruzioni del programma
     */
    public ArrayList<NodeDecSt> getDecSts()
    {
        return this.decSts;
    }
    
    /**
     * Restituisce una rappresentazione testuale del programma.
     * 
     * @return una stringa che rappresenta il programma
     */
    @Override
    public String toString()
    {
        return "Program [decSts= " + decSts + "]";
    }

    /**
     * Accetta un visitatore per la visita al programma.
     * 
     * @param visitor il visitatore da accettare
     */
    @Override
    public void accept(IVisitor visitor) 
    {
        visitor.visit(this);
    }
}
