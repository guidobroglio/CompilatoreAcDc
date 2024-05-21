package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

import org.junit.jupiter.api.*;

import scanner.*;
import parser.*;
import ast.*;
import exception‎.*;
import visitor.*;

class TestCodeGeneratorVisitor 
{
	@Test
	void testInt() throws FileNotFoundException, LexicalException, SyntacticException, IOException 
	{
		NodeProgram node = new Parser(new Scanner("src/test/data/testCodeGeneratorVisitor/TestInt.txt")).parse();
		TypeCheckingVisitor visitor = new TypeCheckingVisitor();
		node.accept(visitor);
		CodeGeneratorVisitor codeGeneratorVisitor = new CodeGeneratorVisitor();
		node.accept(codeGeneratorVisitor);
		
		assertEquals("9 sa 0 k 1 sc 0 k ", codeGeneratorVisitor.getCode());
		assertEquals(null, codeGeneratorVisitor.getLog());
		
	}
	
	@Test
	void testOperazioni() throws FileNotFoundException, LexicalException, SyntacticException, IOException 
	{
		NodeProgram node = new Parser(new Scanner("src/test/data/testCodeGeneratorVisitor/Operazioni.txt")).parse();
		TypeCheckingVisitor visitor = new TypeCheckingVisitor();
		node.accept(visitor);
		CodeGeneratorVisitor codeGeneratorVisitor = new CodeGeneratorVisitor();
		node.accept(codeGeneratorVisitor);
		
		assertEquals("1 sa 0 k 2 sb 0 k lb la + sc 0 k la lb - sd 0 k 2 lb * se 0 k 1 la / sf 0 k lc p P ld p P le p P lf p P ", codeGeneratorVisitor.getCode());
		assertEquals(null, codeGeneratorVisitor.getLog());
		
	}
	
	@Test
	void testRegistri() throws FileNotFoundException, LexicalException, SyntacticException, IOException 
	{
		NodeProgram node = new Parser(new Scanner("src/test/data/testCodeGeneratorVisitor/Registri.txt")).parse();
		TypeCheckingVisitor visitor = new TypeCheckingVisitor();
		node.accept(visitor);
		CodeGeneratorVisitor codeGeneratorVisitor = new CodeGeneratorVisitor();
		node.accept(codeGeneratorVisitor);
		assertEquals("", codeGeneratorVisitor.getCode());
		assertEquals("Superato il numero di registri disponibili", codeGeneratorVisitor.getLog());
	}
	
	@Test
	void testFlaot() throws FileNotFoundException, LexicalException, SyntacticException, IOException
	{
		NodeProgram node = new Parser(new Scanner("src/test/data/testCodeGeneratorVisitor/testFloat.txt")).parse();
		TypeCheckingVisitor visitor = new TypeCheckingVisitor();
		node.accept(visitor);
		CodeGeneratorVisitor codeGeneratorVisitor = new CodeGeneratorVisitor();
		node.accept(codeGeneratorVisitor);
		assertEquals("10 5 k 2.0 / sa 0 k la p P ", codeGeneratorVisitor.getCode());
		assertEquals(null, codeGeneratorVisitor.getLog());
	}
	
}