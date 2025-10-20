package carte;

public enum TipoMazzo {

	NAPOLETANTE("napoletane"),
	PIACENTINE("piacentine");
	
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
