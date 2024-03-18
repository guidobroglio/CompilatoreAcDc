package test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.*;
import org.junit.jupiter.api.*;
import parser.Parser;
import scanner.Scanner;
import token.*;
import java.util.*;

import exception‎.*;

class TestParser 
{
	@Test
	void testParseCorretto1() throws IOException, LexicalException, SyntacticException
	{
		String path="src/test/data/testParser/testParserCorretto1.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
	}
	
	@Test
	void testParseCorretto2() throws IOException, LexicalException, SyntacticException
	{
		String path="src/test/data/testParser/testParserCorretto2.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		
	}
	
	@Test
	void testParseEcc_0() throws IOException, LexicalException, SyntacticException
	{
		String path="src/test/data/testParser/testParserEcc_0.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		SyntacticException ex1 = assertThrows(SyntacticException.class, ()->{parser.parse();});
		assertEquals("Errore sintattico: token 'ID' non valido alla riga 1", ex1.getMessage());	
	}
	
	@Test
	void testParseEcc_1() throws IOException, LexicalException, SyntacticException
	{
		String path="src/test/data/testParser/testParserEcc_1.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		SyntacticException ex1 = assertThrows(SyntacticException.class, ()->{parser.parse();});
		assertEquals("Errore sintattico: token 'ID' non valido alla riga 2", ex1.getMessage());		
	}
	
	@Test
	void testParseEcc_2() throws IOException, LexicalException, SyntacticException
	{
		String path="src/test/data/testParser/testParserEcc_2.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		SyntacticException ex1 = assertThrows(SyntacticException.class, ()->{parser.parse();});
		assertEquals("Errore sintattico: token 'ID' non valido alla riga 2", ex1.getMessage());
	}
	
	@Test
	void testParseEcc_3() throws IOException, LexicalException, SyntacticException
	{
		String path="src/test/data/testParser/testParserEcc_3.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		SyntacticException ex1 = assertThrows(SyntacticException.class, ()->{parser.parse();});
		assertEquals("Errore sintattico: token 'ID' non valido alla riga 2", ex1.getMessage());
	}
	
	@Test
	void testParseEcc_4() throws IOException, LexicalException, SyntacticException
	{
		String path="src/test/data/testParser/testParserEcc_4.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		SyntacticException ex1 = assertThrows(SyntacticException.class, ()->{parser.parse();});
		assertEquals("Errore sintattico: token 'ID' non valido alla riga 2", ex1.getMessage());		
	}
	
	@Test
	void testParseEcc_5() throws IOException, LexicalException, SyntacticException
	{
		String path="src/test/data/testParser/testParserEcc_5.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		SyntacticException ex1 = assertThrows(SyntacticException.class, ()->{parser.parse();});
		assertEquals("Errore sintattico: token 'ID' non valido alla riga 2", ex1.getMessage());
	}
	
	@Test
	void testParseEcc_6() throws IOException, LexicalException, SyntacticException
	{
		String path="src/test/data/testParser/testParserEcc_6.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		SyntacticException ex1 = assertThrows(SyntacticException.class, ()->{parser.parse();});
		assertEquals("Errore sintattico: token 'ID' non valido alla riga 4", ex1.getMessage());		
	}
	
	@Test
	void testParseEcc_7() throws IOException, LexicalException, SyntacticException
	{
		String path="src/test/data/testParser/testParserEcc_7.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		SyntacticException ex1 = assertThrows(SyntacticException.class, ()->{parser.parse();});
		assertEquals("Errore sintattico: token 'ID' non valido alla riga 2", ex1.getMessage());
	}
	
	@Test
	void testSoloDichPrint1() throws IOException, LexicalException, SyntacticException
	{
		String path="src/test/data/testParser/testSoloDichPrint1.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		assertDoesNotThrow(() -> {parser.parse();});
	}	
}
