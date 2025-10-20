package carte;

/**
 * Enum che rappresenta i valori delle carte e associa a ciascuno
 * un valore numerico utile per la logica di gioco.
 */
public enum Valore {
	/** Valore asso(1). */
	ASSO(1),
	/** Valore due(2). */
	DUE(2),
	/** Valore tre(3). */
	TRE(3),
	/** Valore quattro(4). */
	QUATTRO(4),
	/** Valore cinque(5). */
	CINQUE(5),
	/** Valore sei(6). */
	SEI(6),
	/** Valore sette(7). */
	SETTE(7),
	/** Valore fante(8). */
	FANTE(8),
	/** Valore cavallo(9). */
	CAVALLO(9),
	/** Valore re(10). */
	RE(10);

	/** Valore numerico associato al nome enum (es. ASSO -> 1). */
	private final int valore;
	
	/**
	 * Costruttore enum che associa un valore numerico al valore della carta enum.
	 * @param valore valore numerico associato al nome enum
	 */
	Valore(int valore){
		this.valore = valore;
	}
	
	/**
	 * Restituisce la rappresentazione testuale del valore (toString).
	 * @param valore enum Valore da convertire
	 * @return stringa rappresentativa del valore
	 */
	public static String getStringValore(Valore valore) {
		return valore.toString();
	}
	
	/**
	 * Restituisce il valore numerico come stringa.
	 * @return rappresentazione testuale del valore
	 */
	@Override
	public String toString() {
		return String.valueOf(valore);
	}
	
	/**
	 * Restituisce il valore numerico.
	 * @return valore numerico associato alla carta
	 */
    public int getValoreNumerico() {
        return valore;
    }
}
