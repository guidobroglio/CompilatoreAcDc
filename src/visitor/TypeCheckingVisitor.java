package visitor;

import ast.*;
import symbolTable.*;

/**
 * Questa classe implementa un visitor per eseguire il controllo dei tipi sull'albero sintattico.
 * Implementa l'interfaccia IVisitor per consentire la visita dei nodi dell'albero.
 * 
 * @see IVisitor
 * @see ast
 * @see symbolTable
 * @see TypeDescriptor
 * @see TipoTD
 * @see LangType
 * @see Attributes
 * @see SymbolTable
 * 
 * @autor Guido Lorenzo Broglio 20043973
 */
public class TypeCheckingVisitor implements IVisitor
{   
    /** Tipo corrente durante il controllo dei tipi */
    private TypeDescriptor type; 

    /**
     * Costruttore per inizializzare il TypeCheckingVisitor.
     * Inizializza la SymbolTable e imposta il tipo di default come TipoTD.OK.
     */
    public TypeCheckingVisitor()
    {
        SymbolTable.init();
        type = new TypeDescriptor(TipoTD.OK, "");
    }

    /**
     * Restituisce il tipo corrente dopo il controllo dei tipi.
     * 
     * @return Il tipo corrente
     */
    public TypeDescriptor getType()
    {
        return this.type;
    }

    /**
     * Visita di un nodo di tipo NodeProgram.
     * Esegue il controllo dei tipi su ciascuna dichiarazione del programma.
     * Se trova un errore di tipo durante la visita, imposta il tipo di default come TipoTD.ERROR.
     * 
     * @param node Il nodo di tipo NodeProgram
     */
    @Override
    public void visit(NodeProgram node) {
        for (NodeDecSt decSt : node.getDecSts()) 
        {
            // Delega la visita del nodo NodeDecSt corrente a questo stesso visitor
            decSt.accept(this);
            
            // Verifica se il tipo corrente è compatibile con TipoTD.ERROR
            if (type.getTipo().equals(TipoTD.ERROR)) {
                // Se è compatibile, imposta un nuovo TypeDescriptor con tipo TipoTD.ERROR e il messaggio di errore corrente
                type = new TypeDescriptor(TipoTD.ERROR, type.getMsg());
                return; // Interrompe la visita prematuramente in caso di errore
            }
        }
        // Se nessun errore è stato rilevato durante la visita di tutte le dichiarazioni e istruzioni, imposta il tipo corrente come TipoTD.OK
        type = new TypeDescriptor(TipoTD.OK);
    }

    /**
     * Visita di un nodo di tipo NodeId.
     * Esegue un controllo nella SymbolTable per cercare l'attributo associato all'identificatore.
     * Imposta il tipo corrente come TipoTD.INT o TipoTD.FLOAT a seconda del tipo dell'attributo.
     * Se non trova l'attributo, imposta il tipo di default come TipoTD.ERROR.
     * 
     * @param node Il nodo di tipo NodeId
     */
    @Override
    public void visit(NodeId node) 
    {
        // Effettua il lookup dell'identificatore nella SymbolTable
        Attributes attributes = SymbolTable.lookup(node.getNome());
        
        if (attributes == null) 
        {
            // Identificatore non definito: imposta TipoTD.ERROR e fornisce un messaggio di errore
            type.setTipo(TipoTD.ERROR);
            type.setMsg("Variabile '" + node.getNome() + "' non definita");
        } 
        else if (attributes.getType() == LangType.INT) 
        {
            // Identificatore definito con tipo LangType.INT: imposta il tipo corrente come TipoTD.INT
            type.setTipo(TipoTD.INT);
        } 
        else if (attributes.getType() == LangType.FLOAT) 
        {
            // Identificatore definito con tipo LangType.FLOAT: imposta il tipo corrente come TipoTD.FLOAT
            type.setTipo(TipoTD.FLOAT);
        }
    }

    /**
     * Visita di un nodo di tipo NodeDecl.
     * Controlla se l'identificatore della dichiarazione è già presente nella SymbolTable.
     * Se lo è, imposta il tipo di default come TipoTD.ERROR.
     * Se non è presente, imposta il tipo corrente come TipoTD.OK.
     * 
     * @param node Il nodo di tipo NodeDecl
     */
    @Override
    public void visit(NodeDecl node) 
    {
        if (SymbolTable.lookup(node.getId().getNome()) != null) 
        {
            // Identificatore già presente nella SymbolTable, quindi errore di definizione duplicata
            type.setTipo(TipoTD.ERROR);
            type.setMsg("Variabile già definita");
        } 
        else if (node.getInit() != null) 
        {
            // Se c'è un'inizializzazione, visita l'espressione di inizializzazione
            node.getInit().accept(this);
            TypeDescriptor initType = new TypeDescriptor(type.getTipo(), type.getMsg());
            
            if (initType.getTipo() == TipoTD.ERROR) 
            {
                // Se l'inizializzazione ha un tipo incompatibile o errore, imposta TipoTD.ERROR
                type = new TypeDescriptor(initType.getTipo(), initType.getMsg());
            } 
            else 
            {
                // Aggiungi l'identificatore con il tipo specificato nella SymbolTable
                SymbolTable.enter(node.getId().getNome(), new Attributes(node.getType()));
                // Imposta il tipo corrente come OK
                type.setTipo(TipoTD.OK);
            }
        } 
        else 
        {
            // Se non c'è inizializzazione, aggiungi semplicemente l'identificatore con il tipo specificato
            SymbolTable.enter(node.getId().getNome(), new Attributes(node.getType()));
            // Imposta il tipo corrente come OK
            type.setTipo(TipoTD.OK);
        }
    }

    /**
     * Visita di un nodo di tipo NodeBinOp.
     * Esegue il controllo dei tipi su ciascuna delle due operazioni.
     * Se trova un errore di tipo durante la visita, imposta il tipo di default come TipoTD.ERROR.
     * Se non trova un errore, imposta il tipo corrente come TipoTD.OK.
     * 
     * @param node Il nodo NodeBinOp da visitare
     */
    @Override
    public void visit(NodeBinOp node) 
    {
        // Visita il sotto-albero sinistro del nodo BinOp
        node.getLeft().accept(this);
        // Ottieni il tipo e il messaggio risultanti dalla visita del sotto-albero sinistro
        TypeDescriptor leftType = new TypeDescriptor(type.getTipo(), type.getMsg());

        // Visita il sotto-albero destro del nodo BinOp
        node.getRight().accept(this);
        // Ottieni il tipo e il messaggio risultanti dalla visita del sotto-albero destro
        TypeDescriptor rightType = new TypeDescriptor(type.getTipo(), type.getMsg());

        // Verifica le regole di compatibilità dei tipi per il nodo BinOp
        if (leftType.getTipo() == TipoTD.ERROR || leftType.getTipo() == rightType.getTipo()) 
        {
            // Se il tipo sinistro è un errore o i tipi sinistro e destro sono uguali, imposta il tipo corrente
            type = new TypeDescriptor(leftType.getTipo(), leftType.getMsg());
        } 
        else if (rightType.getTipo() == TipoTD.ERROR) 
        {
            // Se il tipo destro è un errore, imposta il tipo corrente con l'errore destro
            type = new TypeDescriptor(rightType.getTipo(), rightType.getMsg());
        } 
        else 
        {
            // Altrimenti, esegui la conversione del tipo appropriata e imposta il tipo corrente a FLOAT
            NodeConvert convert;
            if (leftType.getTipo() == TipoTD.INT) 
            {
                // Conversione del sotto-albero sinistro a FLOAT se è di tipo INT
                convert = new NodeConvert(node.getLeft(), node.getOp());
                node.setLeft(convert);
            } 
            else 
            {
                // Conversione del sotto-albero destro a FLOAT se è di tipo FLOAT
                convert = new NodeConvert(node.getRight(), node.getOp());
                node.setRight(convert);
            }
            // Imposta il tipo corrente a FLOAT dopo la conversione
            type.setTipo(TipoTD.FLOAT);
        }
    }

    /**
     * Visita di un nodo di tipo NodePrint.
     * Controlla se l'identificatore della stampa è presente nella SymbolTable.
     * Se lo è, imposta il tipo corrente come TipoTD.OK.
     * Se non è presente, imposta il tipo corrente come TipoTD.ERROR.
     * 
     * @param node Il nodo di tipo NodePrint
     */
    @Override
    public void visit(NodePrint node) 
    {
        // Ottiene gli attributi corrispondenti all'identificatore dalla SymbolTable
        Attributes attributes = SymbolTable.lookup(node.getId().getNome());
        
        if (attributes == null) 
        {
            // Se gli attributi non sono trovati (null), imposta il tipo corrente come TipoTD.ERROR
            // e specifica un messaggio di errore indicando che la variabile non è definita
            type.setTipo(TipoTD.ERROR);
            type.setMsg("Variabile '" + node.getId().getNome() + "' non definita");
        } 
        else 
        {
            // Se gli attributi sono trovati (non null), imposta il tipo corrente come TipoTD.OK
            type.setTipo(TipoTD.OK);
        }
    }

    /**
     * Visita di un nodo di tipo NodeDeref.
     * Controlla se il messaggio corrente nel tipo è null.
     * Se lo è, imposta il tipo corrente come lo stesso tipo senza messaggio.
     * Se non lo è, imposta il tipo corrente come lo stesso tipo e messaggio.
     * 
     * @param node Il nodo di tipo NodeDeref
     */
    @Override
    public void visit(NodeDeref node) 
    {
        // Visita l'identificatore all'interno del nodo NodeDeref
        node.getId().accept(this);
    }

    /**
     * Visita di un nodo di tipo NodeAssign.
     * Controlla i tipi per determinare la compatibilità dell'assegnazione.
     * Se l'assegnazione è compatibile, imposta il tipo corrente come TipoTD.OK.
     * Se l'assegnazione è incompatibile, imposta il tipo corrente come TipoTD.ERROR.
     * 
     * @param node Il nodo di tipo NodeAssign da visitare
     */
    @Override
    public void visit(NodeAssign node) 
    {
        NodeId id = node.getId();
        id.accept(this);
        TypeDescriptor idType = new TypeDescriptor(type.getTipo(), type.getMsg());

        NodeExpr expr = node.getExpr();
        expr.accept(this);
        TypeDescriptor exprType = new TypeDescriptor(type.getTipo(), type.getMsg());
        
        if (idType.compatibile(exprType))
        {
            type.setTipo(TipoTD.OK);
        }
        else if (idType.getTipo() == TipoTD.ERROR || exprType.getTipo() == TipoTD.ERROR)
        {
            type.setTipo(TipoTD.ERROR);
            type.setMsg(idType.getMsg() + "" + exprType.getMsg());
        }
        else
        {
            type.setTipo(TipoTD.ERROR);
            type.setMsg("Assegnazione incompatibile");
        }       
    }

    /**
     * Visita di un nodo di tipo NodeConvert.
     * Imposta il tipo corrente come TipoTD.FLOAT.
     * 
     * @param node Il nodo di tipo NodeConvert
     */
    @Override
    public void visit(NodeConvert node) 
    {
        // Ottiene l'espressione all'interno del nodo NodeConvert
        NodeExpr expr = node.getExpr();

        // Esegue la visita dell'espressione
        expr.accept(this);

        // Imposta il tipo corrente come TipoTD.FLOAT, indipendentemente dal tipo dell'espressione
        type = new TypeDescriptor(TipoTD.FLOAT);
    }

    /**
     * Visita di un nodo di tipo NodeCost.
     * Controlla il tipo della costante.
     * Se il tipo è LangType.INT, imposta il tipo corrente come TipoTD.INT.
     * Se il tipo è LangType.FLOAT, imposta il tipo corrente come TipoTD.FLOAT.
     * Se il tipo non è riconosciuto come INT o FLOAT, imposta il tipo corrente come TipoTD.ERROR.
     * 
     * @param node Il nodo di tipo NodeCost
     */
    @Override
    public void visit(NodeCost node) 
    {
        // Controlla il tipo della costante rappresentata dal nodo
        if (node.getType() == LangType.INT) 
        {
            // Se il tipo è LangType.INT, imposta il tipo corrente come TipoTD.INT
            type.setTipo(TipoTD.INT);
        } 
        else if (node.getType() == LangType.FLOAT) 
        {
            // Se il tipo è LangType.FLOAT, imposta il tipo corrente come TipoTD.FLOAT
            type.setTipo(TipoTD.FLOAT);
        } 
        else 
        {
            // Se il tipo non è riconosciuto come INT o FLOAT, imposta il tipo corrente come TipoTD.ERROR
            // e imposta un messaggio di errore specifico
            type.setTipo(TipoTD.ERROR);
            type.setMsg("Tipo di costante non corretto");
        }
    }
}
