package symbolTable;

import java.util.*;

/**
 * Questa classe rappresenta una tabella dei simboli utilizzata per associare nomi ad attributi.
 * Gli attributi sono oggetti di tipo `Attributes`, che possono contenere informazioni aggiuntive
 * associate a un dato nome.
 * 
 * La classe fornisce metodi per inizializzare la tabella, inserire nuove associazioni, cercare
 * attributi esistenti, ottenere una rappresentazione testuale della tabella e conoscere la sua dimensione.
 * 
 * @see Attributes
 * 
 * @autor Guido Lorenzo Broglio 20043973
 */
public class SymbolTable 
{
	//Tabella dei simboli rappresentata come una mappa che associa nomi a attributi
	private static HashMap<String, Attributes> table;
	
	/**
	 * Inizializza la tabella dei simboli.
	 * Deve essere chiamato prima di qualsiasi operazione sulla tabella.
	 */
	public static void init()
	{
		table = new HashMap<>();
	}
	
	/**
	 * Inserisce un'associazione nome-attributo nella tabella dei simboli se non esiste già un'associazione per la chiave specificata.
	 * 
	 * @param id il nome del simbolo da inserire
	 * @param entry l'attributo da associare al nome del simbolo
	 * @return true se l'associazione è stata inserita con successo (ossia se non esisteva già un'associazione per la chiave specificata), altrimenti false
	 */
	public static boolean enter(String id, Attributes entry) 
	{
	    return table.putIfAbsent(id, entry) == null;
	}
	
	/**
	 * Cerca o restituisce l'attributo associato al nome.
	 * 
	 * @param id il nome da cercare
	 * @return l'attributo associato al nome o null se non esiste nella tabella
	 */
	public static Attributes lookup(String id)
	{
		return table.get(id);
	}
	
	/**
	 * Restituisce una stringa che rappresenta la tabella.
	 * La rappresentazione include tutte le associazioni nome-attributo della tabella.
	 * 
	 * @return la stringa che rappresenta la tabella
	 */
	public static String toStr()
	{
		StringBuilder tableStr = new StringBuilder();
		for (String key : table.keySet())
		{
			tableStr.append(key).append(": ").append(table.get(key).toString()).append("\n");
		}
		return tableStr.toString();
	}
	
	/**
	 * Restituisce il numero di elementi nella tabella.
	 * 
	 * @return il numero di elementi
	 */
	public static int size()
	{
		return table.size();
	}
}
