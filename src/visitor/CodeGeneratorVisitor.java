package visitor;

import java.util.ArrayList;
import ast.*;
import symbolTable.*;

/**
 * Questa classe implementa un visitor per generare codice dall'albero sintattico.
 * Implementa l'interfaccia IVisitor per consentire la visita dei nodi dell'albero.
 * 
 * Ogni tipo di nodo viene visitato e il codice corrispondente viene generato e
 * aggiunto a un oggetto StringBuilder.
 * 
 * Gli errori di registrazione vengono gestiti e memorizzati in un log.
 * 
 * @see IVisitor
 * @see SymbolTable
 * @see Registri
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
public class CodeGeneratorVisitor implements IVisitor 
{
    /** StringBuilder per costruire il codice generato */
    private StringBuilder code = new StringBuilder();
    /** Log per memorizzare i messaggi di errore */
    private String log;
    
    /**
     * Costruttore che inizializza la tabella dei simboli e l'insieme dei registri.
     */
    public CodeGeneratorVisitor() 
    {
        SymbolTable.init();
        Registri.init();
    }
    
    /**
     * Restituisce il codice generato finora.
     * 
     * @return il codice generato come stringa
     */
    public String getCode() 
    {
        return code.toString();
    }
    
    /**
     * Restituisce il log degli errori.
     * 
     * @return il log degli errori come stringa
     */
    public String getLog() 
    {
        return this.log;
    }
    
    /**
     * Visita un nodo di tipo NodeProgram.
     * Genera codice per tutte le dichiarazioni e istruzioni del programma.
     * 
     * @param node il nodo di tipo NodeProgram
     */
    public void visit(NodeProgram node) 
    {
        ArrayList<NodeDecSt> decSts = node.getDecSts();
        
        for (NodeDecSt decSt : decSts) 
        {
            decSt.accept(this);
        }
    }
    
    /**
     * Visita un nodo di tipo NodeId.
     * Questa implementazione non genera codice per i nodi di tipo NodeId.
     * 
     * @param node il nodo di tipo NodeId
     */
    public void visit(NodeId node) 
    {
        return;
    }

    /**
     * Visita un nodo di tipo NodeDecl.
     * Genera codice per la dichiarazione di una variabile, compresa l'inizializzazione se presente.
     * 
     * @param node il nodo di tipo NodeDecl
     */
    @Override
    public void visit(NodeDecl node) 
    {
        char r = Registri.newRegister();
        
        if (r != '\0') 
        {
            SymbolTable.enter(node.getId().getNome(), new Attributes(node.getType(), r));
        } 
        else 
        {
            log = "Superato il numero di registri disponibili";
        }
        
        if (node.getInit() != null) 
        {
            node.getInit().accept(this);
            Attributes attr = SymbolTable.lookup(node.getId().getNome());
            if (attr != null) 
            {
                code.append("s").append(attr.getRegistro()).append(" 0 k ");
            }
        }
    }
    
    /**
     * Visita un nodo di tipo NodeBinOp.
     * Genera codice per un'operazione binaria (somma, sottrazione, moltiplicazione, divisione).
     * 
     * @param node il nodo di tipo NodeBinOp
     */
    public void visit(NodeBinOp node) 
    {
        NodeExpr left = node.getLeft();
        left.accept(this);
        NodeExpr right = node.getRight();
        right.accept(this);
        LangOper operator = node.getOp();
                
        switch (operator) 
        {
            case PLUS:
                code.append("+ ");
                break;
                
            case MINUS:
                code.append("- ");
                break;
                
            case TIMES:
                code.append("* ");
                break;
                
            case DIVIDE:
                code.append("/ ");
                break;
        }
    }
    
    /**
     * Visita un nodo di tipo NodePrint.
     * Genera codice per stampare il valore di una variabile.
     * 
     * @param node il nodo di tipo NodePrint
     */
    public void visit(NodePrint node) 
    {
        NodeId id = node.getId();
        Attributes attributes = SymbolTable.lookup(id.getNome());
        if (attributes != null) {
            code.append("l").append(attributes.getRegistro()).append(" p P ");
        } 
        else 
        {
            System.err.println("Error: ID " + id.getNome() + " non trovato nel SymbolTable");
        }
    }
    
    /**
     * Visita un nodo di tipo NodeDeref.
     * Genera codice per dereferenziare una variabile.
     * 
     * @param node il nodo di tipo NodeDeref
     */
    public void visit(NodeDeref node) 
    {
        NodeId id = node.getId();
        Attributes attributes = SymbolTable.lookup(id.getNome());
        if (attributes != null) 
        {
            code.append("l").append(attributes.getRegistro()).append(" ");
        } 
        else 
        {
            System.err.println("Error: ID " + id.getNome() + " non trovato nel SymbolTable");
        }
    }
    
    /**
     * Visita un nodo di tipo NodeAssign.
     * Genera codice per assegnare un valore a una variabile.
     * 
     * @param node il nodo di tipo NodeAssign
     */
    public void visit(NodeAssign node) 
    {
        NodeId id = node.getId();
        Attributes attributes = SymbolTable.lookup(id.getNome());
        if (attributes != null) 
        {
            node.getExpr().accept(this);
            code.append("s").append(attributes.getRegistro()).append(" 0 k ");
        } 
        else 
        {
            System.err.println("Error: ID " + id.getNome() + " non trovato nel SymbolTable");
        }
    }
    
    /**
     * Visita un nodo di tipo NodeConvert.
     * Genera codice per convertire un valore in float.
     * 
     * @param node il nodo di tipo NodeConvert
     */
    public void visit(NodeConvert node) 
    {
        NodeExpr expr = node.getExpr();
        expr.accept(this);
        code.append("5 k ");
    }
    
    /**
     * Visita un nodo di tipo NodeCost.
     * Genera codice per una costante.
     * 
     * @param node il nodo di tipo NodeCost
     */
    public void visit(NodeCost node) 
    {
        code.append(node.getValue()).append(" ");
    }
    
    
}
