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
	 * Costruttore 
	 * @param valore. Il valore del seme
	 */
	Valore(int valore){
		this.valore = valore;
	}
	
	/**
	 * 
	 * @param valore. Il valore da convertire in stringa
	 * @return La stringa associata al valore tramite toString()
	 */
	public static String getStringValore(Valore valore) {
		return valore.toString();
	}
	
	/**
	 * @return Il valore in stringa
	 */
	@Override
	public String toString() {
		return String.valueOf(valore);
	}
}
