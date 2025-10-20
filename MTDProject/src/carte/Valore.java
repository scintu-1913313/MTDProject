package carte;

public enum Valore {
	ASSO(1),
	DUE(2),
	TRE(3),
	QUATTRO(4),
	CINQUE(5),
	SEI(6),
	SETTE(7),
	FANTE(8),
	CAVALLO(9),
	RE(10);

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
