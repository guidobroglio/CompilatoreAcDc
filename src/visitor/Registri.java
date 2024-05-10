package visitor;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Questa classe gestisce un insieme di registri utilizzati per un determinato scopo nel programma.
 * I registri sono rappresentati da caratteri dall' 'a' alla 'z'.
 * 
 * @author Guido Lorenzo Broglio 20043973
 */
public class Registri {

    /** Lista statica contenente i registri disponibili */
    public static ArrayList<Character> registri;

    /**
     * Inizializza l'insieme dei registri con tutte le lettere dall' 'a' alla 'z'.
     */
    public static void init() 
    {
        registri = new ArrayList<Character>(Arrays.asList
        (
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 
            'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
        ));
    }

    /**
     * Restituisce il primo registro disponibile e lo rimuove dall'insieme dei registri.
     * Se non ci sono registri disponibili, ritorna 'a' (il primo della sequenza) e non lo rimuove.
     * 
     * @return Il registro disponibile
     */
    public static char newRegister() 
    {
        char registro;
        if (registri.isEmpty()) 
        {
            // Se non ci sono registri disponibili, ritorna 'a' (il primo della sequenza) e non lo rimuove
            registro = 'a';
        } 
        else 
        {
            // Ottiene il primo registro disponibile e lo rimuove dall'insieme dei registri
            registro = registri.get(0);
            registri.remove(0);
        }
        return registro;
    }
}
