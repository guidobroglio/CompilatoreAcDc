package symbolTable;

import java.util.*;
import ast.LangType;

/**
 * Questa classe rappresenta una tabella dei simboli utilizzata per associare nomi a attributi.
 * Gli attributi sono oggetti di tipo `Attributes`, che possono contenere informazioni aggiuntive
 * associate a un dato nome.
 * @author Guido Lorenzo Broglio 20043973
 *
 */
public class SymbolTable 
{
	//Tabella dei simboli rappresentata come una mappa che associa nomi a attributi
	private static HashMap<String, Attributes> table;
	
	/**
	 * Inizializza la tabella dei simboli 
	 */
	public static void init()
	{
		table=new HashMap<String, Attributes>();
	}
	
	/**
	 * Inserisce un'associazione nome-attributo nella tabella dei simboli.
	 * Se l'associazione esiste, restituisce false
	 * @param id il nome del simbolo da inserire
	 * @param entry l'attributo da associare
	 * @return true se l'associazione esiste, false altrimenti
	 */
	public static boolean enter(String id, Attributes entry)
	{
		return table.put(id, entry)==null ? true:false;
	}
	
	/**
	 * Cerca o restituisce l'attributo associato al nome
	 * @param id il nome da cercare
	 * @return l'attributo associato al nome o null se non esiste nella tabella
	 */
	public static Attributes lookup(String id)
	{
		return table.get(id);
	}
	
	/**
	 * Restituisce una stringa che rappresenta la tabella
	 * La rappresentazione include tutte le associazioni nome-attributo della tabella
	 * @return la stringa che rappresenta la tabella
	 */
	public static String toStr()
	{
		String tableStr="";
		for(String key: table.keySet())
		{
			tableStr+=key+": "+table.get(key).toString();
		}
		return tableStr;
	}
	
	/**
	 * Restituisce il numero di elementi nella tabella
	 * @return il numero di elementi
	 */
	public static int size()
	{
		return table.size();
	}
}
