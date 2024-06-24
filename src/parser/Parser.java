package parser;

import java.io.*;
import java.util.*;

import ast.*;
import scanner.Scanner;
import token.*;
import exception‎.*;

/**
 * La classe Parser implementa un parser per analizzare la sintassi di un programma sorgente.
 * Utilizza uno scanner per ottenere i token dal programma sorgente e costruisce un albero sintattico.
 * 
 * @see scanner.Scanner
 * @see token.Token
 * @see ast.NodeProgram
 * @see exception.SyntacticException
 * @see exception.LexicalException
 * 
 * @author Guido Lorenzo Broglio 20043973
 */

public class Parser 
{
	private Scanner scanner;
	
	/**
	 * Costruisce un nuovo oggetto Parser con lo Scanner specificato
	 * @param scanner lo Scanner da utilizzare per la tokenizzazione da parte di questo Parser
	 */
	public Parser(Scanner scanner)
	{
		this.scanner=scanner;
	}
	
	/**
	 * Avvia il processo di parsing del programma sorgente per ottenere un oggetto NodeProgram
	 * @return un oggetto NodeProgram che rappresenta l'albero sintattico del programma
	 * @throws SyntacticException viene lanciata una eccezione se la sintassi non è corretta
	 * @throws LexicalException viene lanciata una eccezione se avviene un errore lessicale
	 * @throws IOException viene lanciata una eccezione se si verifica un errore di I/O
	 */
	public NodeProgram parse() throws SyntacticException, LexicalException, IOException
	{
		return this.parsePrg();
	}
	
	/**
	 * Analizza il token successivo e restituisce un oggetto NodeProgram
	 * @return un oggetto NodeProgram che rappresenta l'albero sintattico del programma
	 * @throws SyntacticException viene lanciata una eccezione se la sintassi non è corretta
	 * @throws LexicalException viene lanciata una eccezione se avviene un errore lessicale
	 * @throws IOException viene lanciata una eccezione se si verifica un errore di I/O
	 */
	private NodeProgram parsePrg() throws SyntacticException, LexicalException, IOException
	{
		Token tkn = scanner.peekToken();
		ArrayList<NodeDecSt> nodeDecSt;
		switch(tkn.getTipo())
		{
			case TYFLOAT, TYINT, ID, PRINT, EOF:
				nodeDecSt = parseDSs();
				match(TokenType.EOF);
				return new NodeProgram(nodeDecSt);
				
			default:
				throw new SyntacticException("Errore sintattico: token '" + tkn.getTipo() + "' non valido alla riga " + tkn.getRiga());
		}
	}
	
	/**
	 * Analizza il token successivo e restituisce un ArrayList di oggetti NodeDecSt
	 * @return un ArrayList di oggetti NodeDecSt che rappresentano i nodi decSt
	 * @throws SyntacticException viene lanciata una eccezione se la sintassi non è corretta
	 * @throws LexicalException viene lanciata una eccezione se avviene un errore lessicale
	 * @throws IOException viene lanciata una eccezione se si verifica un errore di I/O
	 */
	private ArrayList<NodeDecSt> parseDSs() throws SyntacticException, LexicalException, IOException
	{
		Token tkn = scanner.peekToken();
		NodeDecl nodeDecl;
		NodeStm nodeStm;
		ArrayList<NodeDecSt> nodeDecSt = new ArrayList<>();
		switch(tkn.getTipo())
		{
			case TYFLOAT, TYINT:
				nodeDecl = parseDcl();
				nodeDecSt = parseDSs();
				nodeDecSt.add(0,(nodeDecl));
				return nodeDecSt;
			
			case ID, PRINT:
				nodeStm = parseStm();
				nodeDecSt = parseDSs();
				nodeDecSt.add(0,(nodeStm));
				return nodeDecSt;
			
			case EOF:
				match(TokenType.EOF);
				return new ArrayList<>();
				
			default:
				throw new SyntacticException("Errore sintattico: token '" + tkn.getTipo() + "' non valido alla riga " + tkn.getRiga());
		}
	}
	
	/**
	 * Analizza il token successivo e restituisce un oggetto NodeDecl 
	 * @return un oggetto NodeDecl che rappresenta la dichiarazione analizzata 
	 * @throws SyntacticException viene lanciata una eccezione se la sintassi non è corretta
	 * @throws LexicalException viene lanciata una eccezione se avviene un errore lessicale
	 * @throws IOException viene lanciata una eccezione se si verifica un errore di I/O
	 */
	private NodeDecl parseDcl() throws SyntacticException, LexicalException, IOException
	{
		Token tkn = scanner.peekToken();
		NodeId id; 
		LangType type; 
		NodeExpr init;
		
		switch(tkn.getTipo())
		{
			case TYFLOAT:
			case TYINT:
				type = parseTy();
				try
				{
					id= new NodeId(scanner.peekToken().getVal());
				}
				catch(LexicalException e)
				{
					throw new SyntacticException("Errore sintattico: token '" + tkn.getTipo() + "' non valido alla riga " + tkn.getRiga());
				}
				match(TokenType.ID);
				init = parseDclP(id);
				return new NodeDecl(id, type, init);
				
			default:
				throw new SyntacticException("Errore sintattico: token '" + tkn.getTipo() + "' non valido alla riga " + tkn.getRiga());
		}
	}
	
	/**
	 * Analizza il token successivo e restituisce un oggetto LangType
	 * @return un oggetto LangType
	 * @throws SyntacticException viene lanciata una eccezione se la sintassi non è corretta
	 * @throws LexicalException viene lanciata una eccezione se avviene un errore lessicale
	 * @throws IOException viene lanciata una eccezione se si verifica un errore di I/O
	 */
	private LangType parseTy() throws SyntacticException, LexicalException, IOException
	{
		Token tkn = scanner.peekToken();
		
		switch(tkn.getTipo())
		{
			case TYFLOAT:
				match(TokenType.TYFLOAT);
				return LangType.FLOAT;
			case TYINT:
				match(TokenType.TYINT);
				return LangType.INT;
				
			default:
				throw new SyntacticException("Errore sintattico: token '" + tkn.getTipo() + "' non valido alla riga " + tkn.getRiga());
		}
	}
	
	/**
	 * Analizza il token successivo e restituisce un oggetto NodeExpr
	 * @param id l'oggetto NodeId
	 * @return un oggetto NodeExpr
	 * @throws SyntacticException viene lanciata una eccezione se la sintassi non è corretta
	 * @throws LexicalException viene lanciata una eccezione se avviene un errore lessicale
	 * @throws IOException viene lanciata una eccezione se si verifica un errore di I/O
	 */
	private NodeExpr parseDclP(NodeId id) throws SyntacticException, LexicalException, IOException
	{
		Token tkn = scanner.peekToken();
		switch(tkn.getTipo())
		{
			case SEMI:
				match(TokenType.SEMI);
				return null;
				
			case OP_ASSIGN:
				match(TokenType.OP_ASSIGN);
				NodeExpr exp = parseExp();
				match(TokenType.SEMI);
				return exp;
				
			default:
				throw new SyntacticException("Errore sintattico: token '" + tkn.getTipo() + "' non valido alla riga " + tkn.getRiga());
		}
	}
	
	/**
	 * Annalizza il token successivo e restituisce un oggetto NodeStm
	 * @return un oggetto NodeStm che rappresenta l'istruzione analizzata
	 * @throws SyntacticException viene lanciata una eccezione se la sintassi non è corretta
	 * @throws LexicalException viene lanciata una eccezione se avviene un errore lessicale
	 * @throws IOException viene lanciata una eccezione se si verifica un errore di I/O
	 */
	private NodeStm parseStm() throws SyntacticException, LexicalException, IOException
	{
		Token tkn = scanner.peekToken();
		switch(tkn.getTipo())
		{
			case PRINT:
				match(TokenType.PRINT);
				NodeId id = new NodeId(scanner.peekToken().getVal());
				match(TokenType.ID);
				match(TokenType.SEMI);
				return new NodePrint(id);
				
			case ID:
				var tokenId = match(TokenType.ID);
				match(TokenType.OP_ASSIGN);
				var exp = parseExp();
				match(TokenType.SEMI);
				return new NodeAssign(new NodeId(tokenId.getVal()), exp);
				
			default:
				throw new SyntacticException("Errore sintattico: token '" + tkn.getTipo() + "' non valido alla riga " + tkn.getRiga());
		}
	}
	
	/**
	 * Analizza il token successivo e restituisce un oggetto NodeExpr
	 * @return un oggetto NodeExpr che rappresenta l'espressione analizzata
	 * @throws SyntacticException viene lanciata una eccezione se la sintassi non è corretta
	 * @throws LexicalException viene lanciata una eccezione se avviene un errore lessicale
	 * @throws IOException viene lanciata una eccezione se si verifica un errore di I/O
	 */
	private NodeExpr parseExp() throws SyntacticException, LexicalException, IOException
	{
		Token tkn = scanner.peekToken();
		switch(tkn.getTipo())
		{
			case ID, FLOAT, INT:
				NodeExpr left = parseTr();
				NodeExpr nodeExpr = parseExpP(left);
				return nodeExpr;
			
			default:
				throw new SyntacticException("Errore sintattico: token '" + tkn.getTipo() + "' non valido alla riga " + tkn.getRiga());
		}
	}
	
	/**
	 * Analizza il token successivo e restituisce un oggetto NodeExpr
	 * @param left l'espressione sinistra di cui estendere l'analisi
	 * @return un oggetto NodeExpr che rappresenta l'espressione analizzata
	 * @throws SyntacticException viene lanciata una eccezione se la sintassi non è corretta
	 * @throws LexicalException viene lanciata una eccezione se avviene un errore lessicale
	 * @throws IOException viene lanciata una eccezione se si verifica un errore di I/O
	 */
	private NodeExpr parseExpP(NodeExpr left) throws SyntacticException, LexicalException, IOException
	{
		Token tkn = scanner.peekToken();
		switch(tkn.getTipo())
		{
			case PLUS:
				match(TokenType.PLUS);
				NodeExpr tr1 = parseTr();
				NodeExpr exp1 = parseExpP(left);
				return new NodeBinOp(LangOper.PLUS, tr1, exp1);
				
			case MINUS:
				match(TokenType.MINUS);
				NodeExpr tr2 = parseTr();
				NodeExpr exp2 = parseExpP(left);
				return new NodeBinOp(LangOper.MINUS, tr2, exp2);
			
			case SEMI:
				return left;
			
			default: 
				throw new SyntacticException("Errore sintattico: token '" + tkn.getTipo() + "' non valido alla riga " + tkn.getRiga());
		}
	}
	
	/**
	 * Analizza il token successivo e restituisce un oggetto NodeExpr
	 * @return un oggetto NodeExpr che rappresenta l'espressione analizzata
	 * @throws SyntacticException viene lanciata una eccezione se la sintassi non è corretta
	 * @throws LexicalException viene lanciata una eccezione se avviene un errore lessicale
	 * @throws IOException viene lanciata una eccezione se si verifica un errore di I/O
	 */
	private NodeExpr parseTr() throws SyntacticException, LexicalException, IOException
	{
		Token tkn = scanner.peekToken();
		switch(tkn.getTipo())
		{
			case ID, FLOAT, INT:
				NodeExpr val = parseVal();
				NodeExpr exp = parseTrP(val);
				return exp;
			
			default:
				throw new SyntacticException("Errore sintattico: token '" + tkn.getTipo() + "' non valido alla riga " + tkn.getRiga());
		}
	}
	
	/**
	 * Analizza il token successivo e restituisce un oggetto NodeExpr
	 * @param left l'espressione sinistra di cui estendere l'analisi
	 * @return un oggetto NodeExpr che rappresenta l'espressione analizzata
	 * @throws SyntacticException viene lanciata una eccezione se la sintassi non è corretta
	 * @throws LexicalException viene lanciata una eccezione se avviene un errore lessicale
	 * @throws IOException viene lanciata una eccezione se si verifica un errore di I/O
	 */
	private NodeExpr parseTrP(NodeExpr left) throws SyntacticException, LexicalException, IOException
	{
		Token tkn = scanner.peekToken();
		switch(tkn.getTipo())
		{
			case TIMES:
				match(TokenType.TIMES);
				NodeExpr val1 = parseVal();
				NodeExpr trp1 = parseTrP(left);
				return new NodeBinOp(LangOper.TIMES, val1, trp1);
				
			case DIVIDE:
				match(TokenType.DIVIDE);
				NodeExpr val2 = parseVal();
				NodeExpr trp2 = parseTrP(left);
				return new NodeBinOp(LangOper.DIVIDE, val2, trp2);
				
			case MINUS, PLUS, SEMI:
				return left;
				
			default:
				throw new SyntacticException("Errore sintattico: token '" + tkn.getTipo() + "' non valido alla riga " + tkn.getRiga());
		}
	}
	
	/**
	 * Analizza il token successivo e restituisce un oggetto NodeExpr
	 * @return un oggetto NodeExpr che rappresenta l'espressione analizzata
	 * @throws SyntacticException viene lanciata una eccezione se la sintassi non è corretta
	 * @throws LexicalException viene lanciata una eccezione se avviene un errore lessicale
	 * @throws IOException viene lanciata una eccezione se si verifica un errore di I/O
	 */
	private NodeExpr parseVal() throws SyntacticException, LexicalException, IOException
	{
		Token tkn = scanner.peekToken();
		switch(tkn.getTipo())
		{
			case INT:
				match(TokenType.INT);
				return new NodeCost(tkn.getVal(), LangType.INT);
				
			case FLOAT:
				match(TokenType.FLOAT);
				return new NodeCost(tkn.getVal(), LangType.FLOAT);
			
			case ID:
				match(TokenType.ID);
				NodeId id = new NodeId(tkn.getVal());
				return new NodeDeref(id);
				
			default:
				throw new SyntacticException("Errore sintattico: token '" + tkn.getTipo() + "' non valido alla riga " + tkn.getRiga());
		}
	}
	
	/**
	 * Confronta il tipo del token corrente con quello atteso e lo consuma se corrisponde
	 * @param expectedType il tipo di token atteso
	 * @return il token corrente
	 * @throws SyntacticException se il token corrente non corrisponde con quello atteso
	 * @throws IOException se si verifica un errore di I/O
	 * @throws LexicalException se si verifica un errore lessicale durante il confronto
	 */
	private Token match(TokenType expectedType) throws SyntacticException, IOException, LexicalException
	{
		Token tkn = scanner.peekToken();
		if(expectedType.equals(tkn.getTipo()))
		{
			return scanner.nextToken();
		}
		else
		{
			throw new SyntacticException("Errore sintattico: token '" + expectedType + "' non valido alla riga " + tkn.getRiga());
		}
	}
}