package model;

import java.util.Map;

public enum TipoGiocatore {
	UTENTE(0),
    PC1(1),
    PC2(2),
    PC3(3);
	
    private final int tipo;
    
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
