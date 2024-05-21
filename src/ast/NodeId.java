package ast;

import visitor.*;

/**
 * La classe NodeId rappresenta un nodo per identificatori nel linguaggio.
 * 
 * Estende la classe NodeAST e rappresenta un identificatore all'interno 
 * dell'albero sintattico astratto (AST).
 * 
 * @see ast.NodeAST
 * @see visitor.IVisitor
 * 
 * @author Guido Broglio 20043973
 */
public class NodeId extends NodeAST
{
    private String nome;

    /**
     * Costruisce un nuovo oggetto NodeId con il nome specificato.
     * 
     * @param nome il nome dell'identificatore
     */
    public NodeId(String nome)
    {
        this.nome = nome;
    }

    /**
     * Restituisce il nome dell'identificatore.
     * 
     * @return il nome dell'identificatore
     */
    public String getNome() 
    { 
        return this.nome; 
    }

    /**
     * Restituisce una rappresentazione testuale dell'identificatore.
     * 
     * @return una stringa che rappresenta l'identificatore
     */
    @Override
    public String toString() 
    {
        return "ID: " + this.nome;
    }

    /**
     * Accetta un visitatore per la visita al nodo identificatore.
     * 
     * @param visitor il visitatore da accettare
     */
    @Override
    public void accept(IVisitor visitor) 
    {
        visitor.visit(this);      
    }
}
