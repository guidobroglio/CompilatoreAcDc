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
 * @author Guido Broglio 20043973
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

    /**
     * Metodo che si occupa di costruire un oggetto Scanner per eseguire l'analisi lessicale di un file.
     * @param fileName è il nome del file da analizzare
     * @throws FileNotFoundException se il file non viene trovato viene lanciata la eccezione
     */
    public Scanner(String fileName) throws FileNotFoundException 
    {
		// inizializzazione del buffer di lettura per il file
        this.buffer = new PushbackReader(new FileReader(fileName));
        
        // Verifica se il buffer è null, in tal caso viene lanciata l'eccezione
        if(this.buffer==null)
        {
        	throw new FileNotFoundException("File non trovato");
        }
        // inizializzazione il numero di riga ad 1
        riga = 1;

        // inizializzare campi che non hanno inizializzazione
        this.skipChars = new ArrayList<>(Arrays.asList(' ', '\t', '\n', '\r', EOF));

        // inizializzazione dell'insieme di lettere maiuscole e minuscole
        this.letters = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++) 
        {
            letters.add(c);
        }
        for (char c = 'A'; c <= 'Z'; c++) 
        {
            letters.add(c);
        }
        
        // inizializzazione dell'insieme di cifre
        this.digits = new ArrayList<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));

        // inizializzazione del mapping, associa i caratteri ai TokenType corrispondenti
        this.char_type_Map = new HashMap<>();
        this.char_type_Map.put('+', TokenType.PLUS);
        this.char_type_Map.put('-', TokenType.MINUS);
        this.char_type_Map.put('*', TokenType.TIMES);
        this.char_type_Map.put('/', TokenType.DIVIDE);
        this.char_type_Map.put(';', TokenType.SEMI);
        this.char_type_Map.put('=', TokenType.OP_ASSIGN);

		// inizializzazione del mapping, associa le parole chiave ai TokenType corrispondenti
        this.keyWordsMap = new HashMap<>();
        this.keyWordsMap.put("print", TokenType.PRINT);
        this.keyWordsMap.put("float", TokenType.FLOAT);
        this.keyWordsMap.put("int", TokenType.INT);
    }

	/**
	 * Metodo che si occupa di restituire il token successivo nell'input.
	 * @return il token successivo nell'input.
	 * @throws LexicalException se il token successivo non esiste viene lanciata l'eccezione
	 */
    public Token nextToken() throws LexicalException 
    {
    	// Controllo se il tokern successico è già stato letto, in tal caso restituisco il token
        if (nextTk != null) 
        {
            Token next = nextTk;
            nextTk = null;
            return next;
        }
        
        char nextChar;
        try 
        {
        	// Leggo il carattere senza consumarlo
            nextChar = peekChar();
            char skip;
            
            // Salta gli spazi
            while (this.skipChars.contains(nextChar)) 
            {
                skip = readChar();
                
                // Se il carattere letto è un carattere di fine riga, incrementa la riga
                if (skip == '\n') 
                {
                    nextChar = peekChar();
                    riga++;
                } 
                else 
                {
                	// Se il carattere letto è EOF, restituisco il token EOF
                    if (skip == EOF) 
                    {
                        Token token_eof = new Token(TokenType.EOF, riga);
                        return token_eof;
                    } 
                    // Altrimenti legge il prossimo carattere
                    else 
                    {
                        nextChar = peekChar();
                    }
                }
            }
            // Se il carattere letto è un numero chiama scanNumber
            if (this.digits.contains(nextChar)) 
            {
                return scanNumber();
            }
            
            // Se il carattere letto è una lettera chiama scanId
            if (this.letters.contains(nextChar)) 
            {
                return scanId();
            }
            
            // Se il carattere letto è un carattere '=', restituisce il token OP_ASSIGN
            if(peekChar()=='=')
            {
            	readChar();
            	return new Token(TokenType.OP_ASSIGN, riga, "=");
            }
            
            // Se il carattere letto è un carattere '+', '-' o '*', restituisce il token corrispondente
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

	/**
	 * Metodo che si occupa di restituire il token successivo senza consumarlo
	 * @return il token successivo
	 * @throws LexicalException se il token successivo non esiste viene lanciata l'eccezione
	 * @throws IOException se si verifica un errore di I/O viene lanciata l'eccezione
	 */
    public Token peekToken() throws LexicalException, IOException
    {
		// Controllo se il tokern successivo è stago già letto, in tal caso restituisco il token
    	if(nextTk==null)
    	{
    		nextTk=this.nextToken();
    	}
    	return nextTk;
    }
  
    /**
     * Metodo che si occupa di scansionare e restituire il token per un numero
     * @return il token per un numero
     * @throws LexicalException se il token non esiste viene lanciata l'eccezione
     * @throws IOException se si verifica un errore di I/O viene lanciata l'eccezione
     */
    private Token scanNumber() throws LexicalException, IOException 
    {
        // massime cifre decimali
        int MAX_FLOAT_DECIMALS = 5;

        StringBuilder res = new StringBuilder("");

        // legge il carattere
        char num = readChar();
        res.append(num);

        // flag per il tipo di numero (float o intero)
        boolean isFloat = false;

        // controllo se il numero inizia con '0'
        if (num == '0') 
        {
            // Se il prossimo carattere è un punto, allora è sicuramente un float
            if (peekChar() == '.') 
            {
                isFloat = true;
            }
            // Altrimenti, è un intero
            // Non abbiamo bisogno di impostare isFloat a true qui poiché è già false per impostazione predefinita
        }

        // leggo le cifre
        while (this.digits.contains(peekChar())) 
        {
            res.append(readChar());
        }

        // controllo che le cifre siano seguite da lettere, in tal caso lancio l'eccezione
        if (letters.contains(peekChar())) 
        {
            res.append(readChar());
            throw new LexicalException("Errore numerico alla riga " + riga + ": cifre seguite da lettere");
        }

        // controllo che il numero inizi con '0', in tal caso lancio l'eccezione
        else if (!isFloat && res.length() > 1 && res.charAt(0) == '0') 
        {
            throw new LexicalException("Errore numerico alla riga " + riga + ": valore non valido, un intero non può iniziare con '0'");
        }

        // controllo se il numero è un float
        else if (peekChar() != '.') 
        {
            return new Token(isFloat ? TokenType.FLOAT : TokenType.INT, riga, res.toString());
        } 
        else 
        {
            res.append(readChar());

            int count = 0;
            while (this.digits.contains(peekChar())) 
            {
                res.append(readChar());
                count++;
            }
            
            // controllo che le cifre decimali siano seguite da lettere, in tal caso lancio l'eccezione
            if (letters.contains(peekChar())) 
            {
                res.append(readChar());
                throw new LexicalException("Errore numerico alla riga " + riga + ": cifre seguite da lettere");
            }
            
            // controllo che il numero non superi il numero massimo di cifre decimali
            else if (count <= MAX_FLOAT_DECIMALS) 
            {
                return new Token(TokenType.FLOAT, riga, res.toString());
            } 
            else 
            {
                throw new LexicalException("Errore numerico alla riga " + riga + ": troppi decimali");
            }
        }
    }

    
	/**
	 * Metodo che si occupa di scansionare e restituire il token per un identificatore
	 * @return il token per un identificatore
	 * @throws IOException se si verifica un errore di I/O viene lanciata l'eccezione
	 * @throws LexicalException se il token non esiste viene lanciata l'eccezione
	 */
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

        if (temp.length() > 0 && Character.isDigit(temp.charAt(0))) 
        {
            throw new LexicalException("Errore identificatore alla riga " + riga + ": l'identificatore non può iniziare con un numero");
        }

        if (keyWordsMap.containsKey(temp.toString())) 
        {
            return new Token(keyWordsMap.get(temp.toString()), riga);
        } 
        else 
        {
            return new Token(TokenType.ID, riga, temp.toString());
        }
    }

	/**
	 * Metodo che si occupa di leggere un carattere dal buffer e lo restituisce
	 * @return il carattere
	 * @throws IOException se si verifica un errore di I/O viene lanciata l'eccezione
	 */
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
    
	/**
	 * Metodo che restituisce il carattere successivo senza consumarlo
	 * @return il carattere successivo
	 * @throws IOException se si verifica un errore di I/O viene lanciata l'eccezione
	 */
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
