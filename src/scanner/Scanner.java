package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.*;

import exception‎.*;
import token.*;

/**
 * 
 * @author Guido
 *
 */

public class Scanner 
{
    final char EOF = (char) -1;
    private int riga;
    private PushbackReader buffer;
    private Token token;

    // skpChars: insieme caratteri di skip (include EOF) e inizializzazione
    private List<Character> skipChars;

    // letters: insieme lettere e inizializzazione
    private List<Character> letters;

    // digits: cifre e inizializzazione
    private List<Character> digits;

    // char_type_Map: mapping fra caratteri '+', '-', '*', '/', ';', '=', ';' e il TokenType corrispondente
    private Map<Character, TokenType> char_type_Map;

    // keyWordsMap: mapping fra le stringhe "print", "float", "int" e il TokenType corrispondente
    private Map<String, TokenType> keyWordsMap;

    private Token nextTk = null;

    public Scanner(String fileName) throws FileNotFoundException {

        this.buffer = new PushbackReader(new FileReader(fileName));
        if(this.buffer==null)
        {
        	throw new FileNotFoundException("File non trovato");
        }
        riga = 1;

        // inizializzare campi che non hanno inizializzazione
        this.skipChars = new ArrayList<>(Arrays.asList(' ', '\t', '\n', '\r', EOF));

        this.letters = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++) {
            letters.add(c);
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            letters.add(c);
        }

        this.digits = new ArrayList<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));

        this.char_type_Map = new HashMap<>();
        this.char_type_Map.put('+', TokenType.PLUS);
        this.char_type_Map.put('-', TokenType.MINUS);
        this.char_type_Map.put('*', TokenType.TIMES);
        this.char_type_Map.put('/', TokenType.DIVIDE);
        this.char_type_Map.put(';', TokenType.SEMI);
        this.char_type_Map.put('=', TokenType.OP_ASSIGN);

        this.keyWordsMap = new HashMap<>();
        this.keyWordsMap.put("print", TokenType.PRINT);
        this.keyWordsMap.put("float", TokenType.FLOAT);
        this.keyWordsMap.put("int", TokenType.INT);
    }

    public Token nextToken() throws LexicalException {
        if (nextTk != null) {
            Token next = nextTk;
            nextTk = null;
            return next;
        }
        
        char nextChar;
        try {
            nextChar = peekChar();
            char skip;
            
            while (this.skipChars.contains(nextChar)) {
                skip = readChar();
                
                if (skip == '\n') {
                    nextChar = peekChar();
                    riga++;
                } else {
                    if (skip == EOF) {
                        Token token_eof = new Token(TokenType.EOF, riga);
                        return token_eof;
                    } else {
                        nextChar = peekChar();
                    }
                }
            }
            
            if (this.digits.contains(nextChar)) {
                return scanNumber();
            }
            
            if (this.letters.contains(nextChar)) {
                return scanId();
            }
            
            if (this.char_type_Map.containsKey(nextChar)) 
            {
                char op = readChar();
                if (nextChar == '+' || nextChar == '-' || nextChar == '*' || nextChar == '/') 
                {
                    if (peekChar() == '=') 
                    {
                        op = readChar(); // Consuma '='
                        return new Token(TokenType.OP_ASSIGN, riga, nextChar + "=");
                    }
                }
                return new Token(this.char_type_Map.get(nextChar), riga);
            }
        } 
        catch (IOException e) 
        {
            throw new LexicalException("Lettura della riga errata " + riga + "\n", e);
        }
        
        return null;
    }

    public Token peekToken() throws LexicalException, IOException
    {
    	if(nextTk==null)
    	{
    		nextTk=this.nextToken();
    	}
    	return nextTk;
    }
  
    private Token scanNumber() throws LexicalException, IOException {
        // Definisci i limiti MIN e MAX per la gestione dei float
        int MIN = 0;
        int MAX = 5;

        try {
            // Inizializza il StringBuffer per contenere il numero
            StringBuffer res = new StringBuffer("");
            char num = readChar();
            res.append(num);

            // Crea una variabile per il token
            Token t_number;

            // Leggi tutte le cifre prima del punto decimale
            while (this.digits.contains(peekChar())) {
                res.append(readChar());
            }

            // Controlla la presenza del punto decimale
            if (peekChar() != '.') {
                // Se non c'è un punto decimale, il numero è un intero
                t_number = new Token(TokenType.INT, riga, res.toString());
                return t_number;
            } else {
                // Se c'è un punto decimale, continua la lettura
                res.append(readChar());
                int index = res.length() - 1;
                int count = 0;

                // Leggi le cifre dopo il punto decimale
                while (this.digits.contains(peekChar())) {
                    res.append(readChar());
                    count++;
                }

                // Verifica i limiti per determinare se è un FLOAT o INT
                if ((count <= MAX) && (count >= MIN)) {
                    t_number = new Token(TokenType.FLOAT, riga, res.toString());
                    return t_number;
                } else {
                    // Se il conteggio è fuori dai limiti, solleva un'eccezione LexicalException
                    throw new LexicalException("Errore numerico alla riga " + riga);
                }
            }
        } catch (IOException e) {
            // Gestisci le eccezioni di IO
            throw new LexicalException("Carattere " + peekChar() + " alla riga " + riga + "\n", e);
        }
    }

    private Token scanId() throws IOException, LexicalException 
    {
        StringBuilder temp = new StringBuilder();
        char c;
        do 
        {
            c = peekChar();
            if (letters.contains(c) || digits.contains(c)) 
            {
                temp.append(c);
                readChar();
            } 
            else if (!skipChars.contains(c) && !char_type_Map.containsKey(c)) 
            {
                throw new LexicalException("Eccezione alla riga " + riga + " data dal carattere " + "'" + c + "'");
            }
        } 
        while (!skipChars.contains(c) && !char_type_Map.containsKey(c));

        if (keyWordsMap.containsKey(temp.toString())) 
        {
            return new Token(keyWordsMap.get(temp.toString()), riga);
        } 
        else 
        {
            return new Token(TokenType.ID, riga, temp.toString());
        }
    }

    private char readChar() throws IOException 
    {
    	try 
    	{
            return ((char) this.buffer.read());
 
    	}
    	catch(IOException e)
    	{
    		throw new IOException("Eccezione di I/O\n", e);
    	}
    }
    

    private char peekChar() throws IOException 
    {
        try 
        {
            char c = (char) buffer.read();
            buffer.unread(c);
            return c;
        } 
        catch (IOException e) 
        {
            throw new IOException("Eccezione I/O\n", e);
        }

    }

}
