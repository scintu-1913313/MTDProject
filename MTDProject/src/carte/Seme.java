package carte;

public enum Seme {
	COPPE("coppe"),
	DENARI("denari"),
	SPADE("spade"),
	BASTONI("bastoni");

	private final String nomeSeme;
	
	/**
	 * Costruttore enum che associa la rappresentazione testuale al seme.
	 * @param nomeSeme nome testuale del seme
	 */
	Seme(String nomeSeme){
		this.nomeSeme = nomeSeme;
	}
	
	/**
	 * Restituisce la stringa descrittiva del seme.
	 * @param seme enum Seme da convertire
	 * @return stringa rappresentativa del seme
	 */
	public static String getNomeSeme(Seme seme) {
		return seme.toString();
	}
	
	/**
	 * Rappresentazione testuale del seme.
	 * @return nome del seme
	 */
	@Override
	public String toString() {
		return nomeSeme;
	}
}
