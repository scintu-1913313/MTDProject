package model;

import java.util.Map;

/**
 * Enum che rappresenta i possibili ruoli dei giocatori nella partita e relativo valore identificativo.
 * UTENTE indica il giocatore locale, PC1, PC2, PC3 indicano i giocatori controllati dalla CPU.
 */
public enum TipoGiocatore {
	/** Giocatore vero. */
	UTENTE(0),
	/** Giocatore IA, PC1. */
    PC1(1),

	/** Giocatore IA, PC2. */
    PC2(2),

	/** Giocatore IA, PC3. */
    PC3(3);
	
	/** Indice numerico del tipo di giocatore. */
    private final int tipo;
    
	/** Mappa statica per convertire TipoGiocatore in stringa. */
    public static final Map<TipoGiocatore, String> mappaToString = Map.of(
    		TipoGiocatore.UTENTE, "Utente",
    		TipoGiocatore.PC1, "Pc1",
    		TipoGiocatore.PC2, "Pc2",
    		TipoGiocatore.PC3, "Pc3"
	);
    
	/**
	 * Costruttore enum che associa un id numerico al tipo di giocatore.
	 * @param tipo indice numerico del tipo
	 */
	TipoGiocatore(int tipo){
		this.tipo = tipo;
	}    
    
	/**
	 * Converte un id numerico in TipoGiocatore (default UTENTE se non valido).
	 * @param tipo id numerico
	 * @return TipoGiocatore corrispondente
	 */
	public static TipoGiocatore fromId(int tipo) {
        for (TipoGiocatore a : values()) {
            if (a.tipo == tipo) {
            	return a;
            }
        }
        return UTENTE;
    }
	
	/**
	 * Restituisce la stringa rappresentativa del tipo (toString).
	 * @param tipo enum da convertire
	 * @return stringa del tipo
	 */
	public static String getTurno(TipoGiocatore tipo) {
		return tipo.toString();
	}
	
	/**
	 * Rappresentazione testuale del tipo di giocatore.
	 * @return nome leggibile del tipo di giocatore
	 */
	@Override
	public String toString() {
		return mappaToString.get(TipoGiocatore.fromId(tipo));
	}
}
