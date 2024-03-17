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
			case FLOAT, INT, ID, PRINT, EOF:
				parseDSs();
				match(TokenType.EOF);
				return;
			default:
				throw new SyntacticException("errore sintattico: atteso token ' " + tkn.getTipo() + "' alla riga" + tkn.getRiga());
		}
	}
	
	private void parseDSs() throws SyntacticException, LexicalException, IOException
	{
		Token tkn = scanner.peekToken();
		while(tkn.getTipo().equals(TokenType.FLOAT) || tkn.getTipo().equals(TokenType.INT) || tkn.getTipo().equals(TokenType.ID))
		{
			parseDcl();
			tkn = scanner.peekToken();
		}
	}
	
	private void parseDcl() throws SyntacticException, LexicalException, IOException
	{
		Token tkn = scanner.peekToken();
		switch(tkn.getTipo())
		{
			case FLOAT:
			case INT:
				parseTy();
				match(TokenType.ID);
				parseDclP();
				return;
			default:
				throw new SyntacticException("errore sintattico: atteso token ' " + tkn.getTipo() + "' alla riga" + tkn.getRiga());
		}
	}
	
	private void parseTy() throws SyntacticException, LexicalException
	{
		Token tkn = scanner.nextToken();
		switch(tkn.getTipo())
		{
			case FLOAT:
			case INT:
				return;
			default:
				throw new SyntacticException("errore sintattico: atteso token ' " + tkn.getTipo() + "' alla riga" + tkn.getRiga());
		}
	}
	
	private void parseDclP() throws SyntacticException, LexicalException, IOException
	{
		Token tkn = scanner.peekToken();
		while(tkn.getTipo().equals(TokenType.FLOAT) || tkn.getTipo().equals(TokenType.INT) || tkn.getTipo().equals(TokenType.ID))
		{
			parseDcl();
			tkn = scanner.peekToken();
		}
	}
	
	private void parseStm() throws SyntacticException, LexicalException, IOException
	{
		Token tkn = scanner.peekToken();
		switch(tkn.getTipo())
		{
			case PRINT:
				return;
			default:
				throw new SyntacticException("errore sintattico: atteso token ' " + tkn.getTipo() + "' alla riga" + tkn.getRiga());
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
			throw new SyntacticException("errore sintattico: atteso token ' " + expectedType + "'alla riga" + tkn.getRiga());
		}
	}
	
}
