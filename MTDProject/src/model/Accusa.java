package model;

import java.util.Map;

/**
 * Enum che rappresenta le possibili accuse nel gioco, ciascuna con un valore numerico associato.
 * Possibili accuse: NAPOLI (3), BUONGIOCO (3), SUPERBUONGIOCO (4).
 */
public enum Accusa {
	/** 
	 * Accusa Napoli: vale 3 punti da aggiungere al giocatore che l'ha chiamata 
	 */
	NAPOLI(3),

	/** 
	 * Accusa Buongioco: vale 3 punti da aggiungere al giocatore che l'ha chiamata
	 */
	BUONGIOCO(3),

	/** 
	 * Accusa SuperBuongioco: vale 4 punti da aggiungere al giocatore che l'ha chiamata
	 */
    SUPERBUONGIOCO(4);
	
	/** Valore numerico associato all'accusa. */
    private final int accusa;
    
	/** Rappresentazioni testuali delle accuse. */
    public static final Map<Accusa, String> mappaToString = Map.of(
    		Accusa.NAPOLI, "Napoli",
    		Accusa.BUONGIOCO, "Buongioco",
    		Accusa.SUPERBUONGIOCO, "SuperBuongioco"
	);
    
	/**
	 * Costruisce un' accusa con il relativo valore numerico.
	 * @param accusa. L' accusa
	 */
    Accusa(int accusa){
		this.accusa = accusa;
	}    
	
    /**
	 *  Restituisce il valore numerico associato all'accusa.
	 *	@return Il valore dell'accusa
	 */
    public int getValore() {
        return accusa;
    }
    
	/**
	 * Restituisce la rappresentazione testuale dell'accusa.
	 * @return Il nome in stringa dell'accusa
	 */
	@Override
	public String toString() {
		return mappaToString.get(this);
	}
}

