
package carte;

/**
 * Tipi di mazzo supportati dal gioco con la relativa rappresentazione testuale.
 */
public enum TipoMazzo {

	NAPOLETANTE("napoletane"),
	PIACENTINE("piacentine");
	
	/** Stringa relativa al tipo di mazzo. */
	private final String nomeTipoMazzo;
	
	/**
	 * Costruttore enum che associa il nome al tipo di mazzo.
	 * @param nomeTipoMazzo nome usato per il tipo di mazzo
	 */
	TipoMazzo(String nomeTipoMazzo){
		this.nomeTipoMazzo = nomeTipoMazzo;
	}
	
	/**
	 * Restituisce la rappresentazione testuale del TipoMazzo.
	 * @param nomeTipoMazzo enum da convertire
	 * @return stringa nome del tipo di mazzo
	 */
	public static String getTipoMazzo(TipoMazzo nomeTipoMazzo) {
		return nomeTipoMazzo.toString();
	}
	
	/**
	 * Rappresentazione testuale del tipo di mazzo.
	 * @return nomeTipoMazzo
	 */
	@Override
	public String toString() {
		return nomeTipoMazzo;
	}
}
