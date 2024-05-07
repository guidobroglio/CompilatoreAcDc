package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import exception‎.*;
import scanner.*;
import symbolTable.SymbolTable;
import parser.*;
import ast.*;
import visitor.*;


class TestTypeChecking 
{
	@Test
	void testDicRipetute() throws FileNotFoundException, LexicalException, SyntacticException, IOException
	{
		assertDoesNotThrow(() ->
		{
			NodeProgram node = new Parser(new Scanner("src/test/data/testTypeChecking/1_dicRipetute.txt")).parse();
			var typeCheckingVisitor = new TypeCheckingVisitor();
			node.accept(typeCheckingVisitor);
			TypeDescriptor type = new TypeDescriptor(TipoTD.ERROR, "Variabile già definita");
			assertEquals(type.getTipo(), typeCheckingVisitor.getType().getTipo());
			assertEquals(type.getMsg(), typeCheckingVisitor.getType().getMsg());
		});
	}
	
	@Test
	void testIdNonDec1() throws FileNotFoundException, LexicalException, SyntacticException, IOException
	{
		assertDoesNotThrow(() ->
		{
			NodeProgram node = new Parser(new Scanner("src/test/data/testTypeChecking/2_idNonDec.txt")).parse();
			var typeCheckingVisitor = new TypeCheckingVisitor();
			node.accept(typeCheckingVisitor);
			TypeDescriptor type = new TypeDescriptor(TipoTD.ERROR, "Variabile 'b' non definita");
			assertEquals(type.getTipo(), typeCheckingVisitor.getType().getTipo());
			assertEquals(type.getMsg(), typeCheckingVisitor.getType().getMsg());
		});
	}

	@Test
	void testIdNonDec2() throws FileNotFoundException, LexicalException, SyntacticException, IOException
	{
		assertDoesNotThrow(() ->
		{
			NodeProgram node = new Parser(new Scanner("src/test/data/testTypeChecking/3_idNonDec")).parse();
			var typeCheckingVisitor = new TypeCheckingVisitor();
			node.accept(typeCheckingVisitor);
			TypeDescriptor type = new TypeDescriptor(TipoTD.ERROR, "Variabile 'c' non definita");
			assertEquals(type.getTipo(), typeCheckingVisitor.getType().getTipo());
			assertEquals(type.getMsg(), typeCheckingVisitor.getType().getMsg());
		});
	}
	
	@Test
	void testTipoNonCompatibile() throws FileNotFoundException, LexicalException, SyntacticException, IOException
	{
		assertDoesNotThrow(() ->
		{
			NodeProgram node = new Parser(new Scanner("src/test/data/testTypeChecking/4_tipoNonCompatibile.txt")).parse();
			var typeCheckingVisitor = new TypeCheckingVisitor();
			node.accept(typeCheckingVisitor);
			TypeDescriptor type = new TypeDescriptor(TipoTD.ERROR, "Assegnazione non compatibile");
			assertEquals(type.getTipo(), typeCheckingVisitor.getType().getTipo());
			assertEquals(type.getMsg(), typeCheckingVisitor.getType().getMsg());
		});
	}
	
	@Test
	void testCorretto1() throws FileNotFoundException, LexicalException, SyntacticException, IOException
	{
		assertDoesNotThrow(() -> 
		{
			NodeProgram node = new Parser(new Scanner("src/test/data/testTypeChecking/5_corretto.txt")).parse();
			var typeCheckingVisitor = new TypeCheckingVisitor();
			node.accept(typeCheckingVisitor);
			assertEquals(LangType.FLOAT, SymbolTable.lookup("a").getType());
			assertEquals(LangType.INT, SymbolTable.lookup("b").getType());
			assertEquals("", typeCheckingVisitor.getType().getMsg());
			assertEquals(TipoTD.OK, typeCheckingVisitor.getType().getTipo());
		});
	}
	
	@Test
	void testCorretto2() throws FileNotFoundException, LexicalException, SyntacticException, IOException
	{
		assertDoesNotThrow(() -> 
		{
			NodeProgram node = new Parser(new Scanner("src/test/data/testTypeChecking/5_corretto.txt")).parse();
			var typeCheckingVisitor = new TypeCheckingVisitor();
			node.accept(typeCheckingVisitor);
			assertEquals(LangType.FLOAT, SymbolTable.lookup("a").getType());
			assertEquals(LangType.INT, SymbolTable.lookup("b").getType());
			assertEquals("", typeCheckingVisitor.getType().getMsg());
			assertEquals(TipoTD.OK, typeCheckingVisitor.getType().getTipo());
		});
	}
	
	
	@Test
	void testCorretto3() throws FileNotFoundException, LexicalException, SyntacticException, IOException
	{
		assertDoesNotThrow(() -> 
		{
			NodeProgram node = new Parser(new Scanner("src/test/data/testTypeChecking/5_corretto.txt")).parse();
			var typeCheckingVisitor = new TypeCheckingVisitor();
			node.accept(typeCheckingVisitor);
			assertEquals(LangType.FLOAT, SymbolTable.lookup("a").getType());
			assertEquals(LangType.INT, SymbolTable.lookup("b").getType());
			assertEquals("", typeCheckingVisitor.getType().getMsg());
			assertEquals(TipoTD.OK, typeCheckingVisitor.getType().getTipo());
		});
	}
	
	
}
