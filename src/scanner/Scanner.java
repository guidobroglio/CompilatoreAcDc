package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.*;

import exception‎.*;
import token.*;

/**
 * La classe Scanner implementa un analizzatore lessicale per un file.
 * Analizza l'input carattere per carattere e produce token corrispondenti.
 * 
 * @see exception.LexicalException
 * @see token.Token
 * @see token.TokenType
 * 
 * @autor Guido Broglio 20043973
 */

public class Scanner 
{
    final char EOF = (char) -1;
    private int riga;
    private PushbackReader buffer;

    // skpChars: insieme caratteri di skip (include EOF) e inizializzazione
    private List<Character> skipChars;

    // letters: insieme lettere e inizializzazione
    private List<Character> letters;

    // digits: cifre e inizializzazione
    private List<Character> digits;

    // char_type_Map: mapping fra caratteri '+', '-', '*', '/', ';', '=',  e il TokenType corrispondente
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
        this.keyWordsMap.put("float", TokenType.TYFLOAT);
        this.keyWordsMap.put("int", TokenType.TYINT);
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
     * Questo metodo si occupa di analizzare il testo e restituire il token per un numero.
     * Se il numero trovato è un intero, restituisce un token INT.
     * Se il numero trovato è un float, restituisce un token FLOAT.
     * 
     * @return Il token per il numero analizzato.
     * 
     * @throws LexicalException se si verifica un errore nel processo di scansione, ad esempio se viene trovato un carattere non valido.
     * @throws IOException se si verifica un errore di I/O durante la lettura del testo.
     */
    private Token scanNumber() throws IOException, LexicalException 
    {
        try 
        {
            // Stringa temporanea per memorizzare il numero in fase di analisi
            StringBuilder temp = new StringBuilder();
            
            // Carattere corrente durante l'analisi
            char c = readChar();
            
            // Carattere di errore (se presente)
            char errChar = '\0';
            
            // Aggiungiamo il primo carattere alla stringa temporanea
            temp.append(c);
            
            // Se il primo carattere è '0'
            if (c == '0') 
            {
                // Verifichiamo il carattere successivo
                c = peekChar();
                
                // Se è un punto, significa che abbiamo un float iniziato con 0.
                if (c == '.') 
                {
                    // Impostiamo il flag float a true e aggiungiamo il punto alla stringa temporanea
                    errChar = '\0';
                    temp.append(c);
                    readChar();
                    
                    // Chiamiamo il metodo scanFloat per analizzare il float
                    return scanFloat(temp.toString());
                } 
                // Se è un numero, allora abbiamo un intero iniziato con 0.
                else if (digits.contains(c)) 
                {
                    errChar = '\0';
                    // Continuiamo a leggere i numeri fino a quando non troviamo un altro carattere
                    do 
                    {
                        c = peekChar();
                        if (digits.contains(c)) 
                        {
                            temp.append(c);
                            readChar();
                        } 
                        else if (!skipChars.contains(c) && !char_type_Map.containsKey(c) && c != '.') 
                        {
                            errChar = c;
                            readChar();
                        }
                    } 
                    while (!skipChars.contains(c) && !char_type_Map.containsKey(c) && c != '.');
                    
                    // Se è presente un carattere di errore, lanciamo un'eccezione
                    if (errChar != '\0') 
                    {
                        throw new LexicalException("Eccezione alla riga " + riga + " data dal carattere " + "'" + errChar + "'");
                    }
                    
                    // Se abbiamo trovato un punto, chiamiamo il metodo scanFloat
                    if (c == '.') 
                    {
                        temp.append(c);
                        readChar();
                        return scanFloat(temp.toString());
                    } 
                    // Altrimenti restituiamo un token INT
                    else 
                    {
                        throw new LexicalException("Eccezione alla riga " + riga + " data dal carattere " + "'" + temp.charAt(temp.length() - 1) + "'");
                    }
                } 
                // Se è uno dei caratteri speciali o un carattere non valido, restituiamo un token INT
                else if (skipChars.contains(c) || char_type_Map.containsKey(c)) 
                {
                    return new Token(TokenType.INT, riga, temp.toString());
                } 
                // Se non è né un numero né uno dei caratteri speciali, lanciamo un'eccezione
                else 
                {
                    throw new LexicalException("Eccezione alla riga " + riga + " data dal carattere " + "'" + c + "'");
                }
            } 
            // Se il primo carattere non è '0'
            else 
            {
                // Continuiamo a leggere i numeri fino a quando non troviamo un altro carattere
                do 
                {
                    c = peekChar();
                    if (digits.contains(c)) 
                    {
                        temp.append(c);
                        readChar();
                    } 
                    else if (!skipChars.contains(c) && !char_type_Map.containsKey(c) && c != '.') 
                    {
                        errChar = c;
                        readChar();
                    }
                } 
                while (!skipChars.contains(c) && !char_type_Map.containsKey(c) && c != '.');
                
                // Se è presente un carattere di errore, lanciamo un'eccezione
                if (errChar != '\0') 
                {
                    throw new LexicalException("Eccezione alla riga " + riga + " data dal carattere " + "'" + errChar + "'");
                }
                
                // Se non è presente un punto, restituiamo un token INT
                if (c != '.') 
                {
                    return new Token(TokenType.INT, riga, temp.toString());
                } 
                // Se è presente un punto, chiamiamo il metodo scanFloat
                else 
                {
                    temp.append('.');
                    readChar();
                    return scanFloat(temp.toString());
                }
            }
        } 
        // Gestione delle eccezioni di I/O
        catch (IOException e) 
        {
            throw new LexicalException("Errore di IO alla riga " + riga, e);
        }
    }
	
    /**
     * Metodo che si occupa di analizzare i valori float.
     * 
     * @param temp La stringa temporanea contenente la parte float da analizzare.
     * @return Il token per il float analizzato.
     * @throws LexicalException se si verifica un errore nel processo di scansione, ad esempio se viene trovato un carattere non valido.
     * @throws IOException se si verifica un errore di I/O durante la lettura del testo.
     */
    private Token scanFloat(String temp) throws IOException, LexicalException 
    {
        // Carattere corrente durante l'analisi
        char c;
        
        // Carattere di errore (se presente)
        char errChar = '\0';
        
        // Contatore per le cifre decimali
        int counter = 0;
        
        // Continuiamo a leggere finché non troviamo un carattere che non appartiene a un float
        do 
        {
            c = peekChar();
            
            // Se il carattere è una cifra, aggiungiamolo alla stringa temporanea e incrementiamo il contatore
            if (digits.contains(c)) 
            {
                temp += c;
                readChar();
                counter++;
            } 
            // Se il carattere non è una cifra e non è uno dei caratteri speciali, lo consideriamo un errore
            else if (!skipChars.contains(c) && !char_type_Map.containsKey(c)) 
            {
                errChar = c;
                readChar();
            }
        } 
        while (!skipChars.contains(c) && !char_type_Map.containsKey(c) && counter <= 5);
        
        // Se è presente un carattere di errore, lanciamo un'eccezione
        if (errChar != '\0') 
        {
            throw new LexicalException("Eccezione alla riga " + riga + " data dal carattere " + "'" + errChar + "'");
        }
        
        // Se abbiamo trovato più di 5 cifre decimali, lanciamo un'eccezione
        if (counter > 5) 
        { 
            throw new LexicalException("Eccezione alla riga " + riga + ", cifre decimali maggiori di 5");
        } 
        // Altrimenti, restituiamo un token FLOAT
        else 
        {
            return new Token(TokenType.FLOAT, riga, temp);
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
