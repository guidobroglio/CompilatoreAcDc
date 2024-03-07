package parser;

import java.io.*;
import java.util.*;

import scanner.Scanner;
import token.*;
import exception‎.*;

public class Parser 
{
	private Scanner scanner;
	private StringBuilder log;
	
	public Parser(Scanner scanner)
	{
		this.scanner=scanner;
		this.log=new StringBuilder();
	}
}
