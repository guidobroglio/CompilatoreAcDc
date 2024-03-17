package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

import org.junit.jupiter.api.Test;

import exception‎.LexicalException;

import org.junit.*;
import token.*;
import scanner.*;

class TestScanner 
{	
	@Test
	public void testEOF() throws  LexicalException, IOException 
	{
		String path="src/test/data/testScanner/testEOF.txt";
		Scanner scanner = new Scanner(path);
		Token token=scanner.nextToken();
		assertEquals(4, token.getRiga());
		assertEquals(TokenType.EOF, token.getTipo());
	}
	
	@Test
	public void testScanId() throws IOException, LexicalException
	{
		String path = "src/test/data/testScanner/testID.txt";
		Scanner scanner = new Scanner(path);
		
		Token token = scanner.nextToken();
		assertEquals("jskjdsfhkjdshkf", token.getVal());
		assertEquals(1, token.getRiga());
		assertEquals(TokenType.ID, token.getTipo());
		
		token=scanner.nextToken();
		assertEquals("printl", token.getVal());
		assertEquals(2, token.getRiga());
		assertEquals(TokenType.ID, token.getTipo());
		
		token = scanner.nextToken();
	    assertEquals("ffloat", token.getVal());
	    assertEquals(4, token.getRiga());
	    assertEquals(TokenType.ID, token.getTipo());

	    token = scanner.nextToken();
	    assertEquals("hhhjj", token.getVal());
	    assertEquals(6, token.getRiga());
	    assertEquals(TokenType.ID, token.getTipo());

	    token = scanner.nextToken();
	    assertEquals(7, token.getRiga());
	    assertEquals(TokenType.EOF, token.getTipo());
	}
	
	@Test
	public void testScanNumberINT() throws IOException, LexicalException {
	    String path = "src/test/data/testScanner/testINT.txt";
	    Scanner scanner = new Scanner(path);

	    Token token = scanner.nextToken();
	    assertEquals("698", token.getVal());
	    assertEquals(2, token.getRiga());
	    assertEquals(TokenType.INT, token.getTipo());

	    token = scanner.nextToken();
	    assertEquals("560099", token.getVal());
	    assertEquals(4, token.getRiga());
	    assertEquals(TokenType.INT, token.getTipo());

	    token = scanner.nextToken();
	    assertEquals("1234", token.getVal());
	    assertEquals(5, token.getRiga());
	    assertEquals(TokenType.INT, token.getTipo());

	    token = scanner.nextToken();
	    assertEquals(5, token.getRiga());
	    assertEquals(TokenType.EOF, token.getTipo());
	}

	@Test
	public void testScanNumberFLOAT() throws IOException, LexicalException {
	    String path = "src/test/data/testScanner/testFLOAT.txt";
	    Scanner scanner = new Scanner(path);
	    
	    Token token = scanner.nextToken();
	    assertEquals("098.8095", token.getVal());
	    assertEquals(1, token.getRiga());
	    assertEquals(TokenType.FLOAT, token.getTipo());

	    token = scanner.nextToken();
	    assertEquals("0.", token.getVal());
	    assertEquals(2, token.getRiga());
	    assertEquals(TokenType.FLOAT, token.getTipo());

	    token = scanner.nextToken();
	    assertEquals("98.", token.getVal());
	    assertEquals(3, token.getRiga());
	    assertEquals(TokenType.FLOAT, token.getTipo());
	    
	    token = scanner.nextToken();
	    assertEquals("89.99999", token.getVal());
	    assertEquals(5, token.getRiga());
	    assertEquals(TokenType.FLOAT, token.getTipo());

	    token = scanner.nextToken();
	    assertEquals(5, token.getRiga());
	    assertEquals(TokenType.EOF, token.getTipo());
	}

	@Test
	public void testScanOperators() throws IOException, LexicalException {
	    String path = "src/test/data/testScanner/testOperators.txt";
	    Scanner scanner = new Scanner(path);

	    Token token = scanner.nextToken();
	    assertEquals(TokenType.PLUS, token.getTipo());
	    assertEquals(1, token.getRiga());

	    token = scanner.nextToken();
	    assertEquals(TokenType.OP_ASSIGN, token.getTipo());
	    assertEquals(1, token.getRiga());

	    token = scanner.nextToken();
	    assertEquals(TokenType.MINUS, token.getTipo());
	    assertEquals(2, token.getRiga());

	    token = scanner.nextToken();
	    assertEquals(TokenType.TIMES, token.getTipo());
	    assertEquals(2, token.getRiga());

	    token = scanner.nextToken();
	    assertEquals(TokenType.DIVIDE, token.getTipo());
	    assertEquals(3, token.getRiga());

	    token = scanner.nextToken();
	    assertEquals(TokenType.OP_ASSIGN, token.getTipo());
	    assertEquals(5, token.getRiga());

	    token = scanner.nextToken();
	    assertEquals(TokenType.OP_ASSIGN, token.getTipo());
	    assertEquals(6, token.getRiga());

	    token = scanner.nextToken();
	    assertEquals(TokenType.OP_ASSIGN, token.getTipo());
	    assertEquals(6, token.getRiga());

	    token = scanner.nextToken();
	    assertEquals(TokenType.MINUS, token.getTipo());
	    assertEquals(8, token.getRiga());

	    token = scanner.nextToken();
	    assertEquals(TokenType.OP_ASSIGN, token.getTipo());
	    assertEquals(8, token.getRiga());
	    
	    token = scanner.nextToken();
	    assertEquals(TokenType.OP_ASSIGN, token.getTipo());
	    assertEquals(8, token.getRiga());
	    
	    token = scanner.nextToken();
	    assertEquals(TokenType.SEMI, token.getTipo());
	    assertEquals(10, token.getRiga());
	    
	    token = scanner.nextToken();
	    assertEquals(TokenType.EOF, token.getTipo());
	    assertEquals(10, token.getRiga());
	}
	
	@Test
	public void testScanIdKeywords() throws IOException, LexicalException {
	    String path = "src/test/data/testScanner/testIdKeywords.txt";
	    Scanner scanner = new Scanner(path);

	    Token token = scanner.nextToken();
	    assertEquals(null, token.getVal());
	    assertEquals(1, token.getRiga());
	    assertEquals(TokenType.INT, token.getTipo());

	    token = scanner.nextToken();
	    assertEquals("inta", token.getVal());
	    assertEquals(1, token.getRiga());
	    assertEquals(TokenType.ID, token.getTipo());

	    token = scanner.nextToken();
	    assertEquals(null, token.getVal());
	    assertEquals(2, token.getRiga());
	    assertEquals(TokenType.FLOAT, token.getTipo());

	    token = scanner.nextToken();
	    assertEquals(null, token.getVal());
	    assertEquals(3, token.getRiga());
	    assertEquals(TokenType.PRINT, token.getTipo());

	    token = scanner.nextToken();
	    assertEquals("nome", token.getVal());
	    assertEquals(4, token.getRiga());
	    assertEquals(TokenType.ID, token.getTipo());

	    token = scanner.nextToken();
	    assertEquals("intnome", token.getVal());
	    assertEquals(5, token.getRiga());
	    assertEquals(TokenType.ID, token.getTipo());

	    token = scanner.nextToken();
	    assertEquals(null, token.getVal());
	    assertEquals(6, token.getRiga());
	    assertEquals(TokenType.INT, token.getTipo());

	    token = scanner.nextToken();
	    assertEquals("nome", token.getVal());
	    assertEquals(6, token.getRiga());
	    assertEquals(TokenType.ID, token.getTipo());

	    token = scanner.nextToken();
	    assertEquals(6, token.getRiga());
	    assertEquals(TokenType.EOF, token.getTipo());
	}

	@Test
	public void testScanKeywords() throws IOException, LexicalException {
	    String path = "src/test/data/testScanner/testKeywords.txt";
	    Scanner scanner = new Scanner(path);

	    Token token = scanner.nextToken();
	    assertEquals(null, token.getVal());
	    assertEquals(2, token.getRiga());
	    assertEquals(TokenType.PRINT, token.getTipo());

	    token = scanner.nextToken();
	    assertEquals(null, token.getVal());
	    assertEquals(2, token.getRiga());
	    assertEquals(TokenType.FLOAT, token.getTipo());

	    token = scanner.nextToken();
	    assertEquals(null, token.getVal());
	    assertEquals(5, token.getRiga());
	    assertEquals(TokenType.INT, token.getTipo());

	    token = scanner.nextToken();
	    assertEquals(5, token.getRiga());
	    assertEquals(TokenType.EOF, token.getTipo());
	}

	@Test
	public void testScanGenerale() throws IOException, LexicalException {
	    String path = "src/test/data/testScanner/testGenerale.txt";
	    Scanner scanner = new Scanner(path);

	    Token token = scanner.nextToken();
	    assertEquals(TokenType.INT, token.getTipo());
	    assertEquals(null, token.getVal());
	    assertEquals(1, token.getRiga());

	    token = scanner.nextToken();
	    assertEquals(TokenType.ID, token.getTipo());
	    assertEquals("temp", token.getVal());
	    assertEquals(1, token.getRiga());

	    token = scanner.nextToken();
	    assertEquals(TokenType.SEMI, token.getTipo());
	    assertEquals(null, token.getVal());
	    assertEquals(1, token.getRiga());

	    token = scanner.nextToken();
	    assertEquals(TokenType.ID, token.getTipo());
	    assertEquals("temp", token.getVal());
	    assertEquals(2, token.getRiga());

	    token = scanner.nextToken();
	    assertEquals(TokenType.OP_ASSIGN, token.getTipo());
	    assertEquals("+=", token.getVal());
	    assertEquals(2, token.getRiga());

	    token = scanner.nextToken();
	    assertEquals(TokenType.FLOAT, token.getTipo());
	    assertEquals("5.", token.getVal());
	    assertEquals(2, token.getRiga());
	    
	    token = scanner.nextToken();
	    assertEquals(TokenType.SEMI, token.getTipo());
	    assertEquals(null, token.getVal());
	    assertEquals(2, token.getRiga());

	    token = scanner.nextToken();
	    assertEquals(TokenType.FLOAT, token.getTipo());
	    assertEquals(null, token.getVal());
	    assertEquals(4, token.getRiga());

	    token = scanner.nextToken();
	    assertEquals(TokenType.ID, token.getTipo());
	    assertEquals("b", token.getVal());
	    assertEquals(4, token.getRiga());

	    token = scanner.nextToken();
	    assertEquals(TokenType.SEMI, token.getTipo());
	    assertEquals(null, token.getVal());
	    assertEquals(4, token.getRiga());

	    token = scanner.nextToken();
	    assertEquals(TokenType.ID, token.getTipo());
	    assertEquals("b", token.getVal());
	    assertEquals(5, token.getRiga());

	    token = scanner.nextToken();
	    assertEquals(TokenType.OP_ASSIGN, token.getTipo());
	    assertEquals("=", token.getVal());
	    assertEquals(5, token.getRiga());

	    token = scanner.nextToken();
	    assertEquals(TokenType.ID, token.getTipo());
	    assertEquals("temp", token.getVal());
	    assertEquals(5, token.getRiga());

	    token = scanner.nextToken();
	    assertEquals(TokenType.PLUS, token.getTipo());
	    assertEquals(null, token.getVal());
	    assertEquals(5, token.getRiga());

	    token = scanner.nextToken();
	    assertEquals(TokenType.FLOAT, token.getTipo());
	    assertEquals("3.2", token.getVal());
	    assertEquals(5, token.getRiga());

	    token = scanner.nextToken();
	    assertEquals(TokenType.SEMI, token.getTipo());
	    assertEquals(null, token.getVal());
	    assertEquals(5, token.getRiga());

	    token = scanner.nextToken();
	    assertEquals(TokenType.PRINT, token.getTipo());
	    assertEquals(null, token.getVal());
	    assertEquals(6, token.getRiga());

	    token = scanner.nextToken();
	    assertEquals(TokenType.ID, token.getTipo());
	    assertEquals("b", token.getVal());
	    assertEquals(6, token.getRiga());

	    token = scanner.nextToken();
	    assertEquals(TokenType.SEMI, token.getTipo());
	    assertEquals(null, token.getVal());
	    assertEquals(6, token.getRiga());

	    token = scanner.nextToken();
	    assertEquals(TokenType.EOF, token.getTipo());
	    assertEquals(null, token.getVal());
	    assertEquals(7, token.getRiga());
	}

	@Test
	public void testErroriNumbers() throws IOException, LexicalException, FileNotFoundException
	{
		String path = "src/test/data/testScanner/erroriNumbers.txt";
	    Scanner scanner = new Scanner(path);
	     
	    LexicalException ex1 = assertThrows(LexicalException.class, ()->{scanner.nextToken();});
		assertEquals("Errore numerico alla riga 1: valore non valido, un intero non può iniziare con '0'", ex1.getMessage());
		
		LexicalException ex2 = assertThrows(LexicalException.class, ()->{scanner.nextToken();});
		assertEquals("Errore numerico alla riga 2: cifre seguite da lettere", ex2.getMessage());
		
		LexicalException ex3 = assertThrows(LexicalException.class, ()->{scanner.nextToken();});
		assertEquals("Errore numerico alla riga 3: cifre seguite da lettere", ex3.getMessage());
		
		LexicalException ex4 = assertThrows(LexicalException.class, ()->{scanner.nextToken();});
		assertEquals("Errore numerico alla riga 4: troppi decimali", ex4.getMessage());
		
	}

	@Test
	public void testPeekToken() throws IOException, LexicalException
	{
		Scanner s = new Scanner("src/test/data/testScanner/testGenerale.txt");
		assertEquals(s.peekToken().getTipo(), TokenType.INT);
		assertEquals(s.nextToken().getTipo(), TokenType.INT);
		assertEquals(s.peekToken().getTipo(), TokenType.ID);
		assertEquals(s.nextToken().getTipo(), TokenType.ID);
		
		Token t = s.nextToken();
		assertEquals(t.getTipo(), TokenType.SEMI);
		assertEquals(t.getRiga(), 1);
		assertEquals(t.getVal(), null);
	}

}