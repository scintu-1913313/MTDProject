package carte;

public enum Seme {
	COPPE("coppe"),
	DENARI("denari"),
	SPADE("spade"),
	BASTONI("bastoni");

	private final String nomeSeme;
	
	/**
	 * Costruttore 
	 * @param nomeSeme. La stringa del seme
	 */
	Seme(String nomeSeme){
		this.nomeSeme = nomeSeme;
	}
	
	/**
	 * 
	 * @param seme. Il seme da convertire in stringa
	 * @return La stringa associata al seme tramite toString()
	 */
	public static String getNomeSeme(Seme seme) {
		return seme.toString();
	}
	
	/**
	 * @return Il nome in stringa del seme
	 */
	@Override
	public String toString() {
		return nomeSeme;
	}
}
