
package carte;

/**
 * Enum che definisce i semi delle carte (coppe, denari, spade, bastoni)
 * con la relativa rappresentazione testuale usata per i nomi dei file immagine.
 */
public enum Seme {
	/** Seme coppe. */
	COPPE("coppe"),
	/** Seme denari. */
	DENARI("denari"),
	/** Seme spade. */
	SPADE("spade"),
	/** Seme bastoni. */
	BASTONI("bastoni");

	/** Nome testuale del seme, usato per path e visualizzazione. */
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
