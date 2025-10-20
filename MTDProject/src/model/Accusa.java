package model;

import java.util.Map;

public enum Accusa {
	NAPOLI(3),
	BUONGIOCO(3),
    SUPERBUONGIOCO(4);
	
    private final int accusa;
    
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

