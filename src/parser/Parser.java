package parser;

import java.io.*;
import java.util.*;

import scanner.Scanner;
import token.*;
import exception‎.*;

public class Parser 
{
	private Scanner scanner;
	
	public Parser(Scanner scanner)
	{
		this.scanner=scanner;
		
	}
		
	public void parse() throws SyntacticException, LexicalException, IOException
	{
		parsePrg();
	}
	
	private void parsePrg() throws SyntacticException, LexicalException, IOException
	{
		Token tkn = scanner.peekToken();
		switch(tkn.getTipo())
		{
			case TYFLOAT, TYINT, ID, PRINT, EOF:
				parseDSs();
				match(TokenType.EOF);
				return;
			default:
				throw new SyntacticException("Errore sintattico: token '" + tkn.getTipo() + "' non valido alla riga " + tkn.getRiga());
		}
	}
	
	private void parseDSs() throws SyntacticException, LexicalException, IOException
	{
		Token tkn = scanner.peekToken();
		switch(tkn.getTipo())
		{
			case TYFLOAT, TYINT:
				parseDcl();
				parseDSs();
				return;
			
			case ID, PRINT:
				parseStm();
				parseDSs();
				return;
			
			case EOF:
				match(TokenType.EOF);
				return;
				
			default:
				throw new SyntacticException("Errore sintattico: token '" + tkn.getTipo() + "' non valido alla riga " + tkn.getRiga());
		}
	}
	
	private void parseDcl() throws SyntacticException, LexicalException, IOException
	{
		Token tkn = scanner.peekToken();
		switch(tkn.getTipo())
		{
			case TYFLOAT:
			case TYINT:
				parseTy();
				match(TokenType.ID);
				parseDclP();
				return;
			default:
				throw new SyntacticException("Errore sintattico: token '" + tkn.getTipo() + "' non valido alla riga " + tkn.getRiga());
		}
	}
	
	private void parseTy() throws SyntacticException, LexicalException
	{
		Token tkn = scanner.nextToken();
		switch(tkn.getTipo())
		{
			case TYFLOAT:
			case TYINT:
				return;
			default:
				throw new SyntacticException("Errore sintattico: token '" + tkn.getTipo() + "' non valido alla riga " + tkn.getRiga());
		}
	}
	
	private void parseDclP() throws SyntacticException, LexicalException, IOException
	{
		Token tkn = scanner.peekToken();
		switch(tkn.getTipo())
		{
			case SEMI:
				match(TokenType.SEMI);
				return;
			//case OP_ASSIGN:
				//match(TokenType.OP_ASSIGN);
				//parseExp();
				//match(TokenType.SEMI);
				//return;
			default:
				throw new SyntacticException("Errore sintattico: token '" + tkn.getTipo() + "' non valido alla riga " + tkn.getRiga());
		}

	}
	
	private void parseStm() throws SyntacticException, LexicalException, IOException
	{
		Token tkn = scanner.peekToken();
		switch(tkn.getTipo())
		{
			case PRINT:
				return;
				
			case ID:
				//match(TokenType.OP_ASSIGN);
				//parseExp();
				//match(TokenType.SEMI);
				//return;
				
			default:
				throw new SyntacticException("Errore sintattico: token '" + tkn.getTipo() + "' non valido alla riga " + tkn.getRiga());
		}
	}
	
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
