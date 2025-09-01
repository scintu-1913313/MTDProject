package carte;

public class Carta {
	private final Seme seme;
	private final Valore valore;
	private final String percorsoImmagine;

	public Carta(Seme seme, Valore valore, String percorsoImmagine){
		this.seme = seme;
		this.valore = valore;
		this.percorsoImmagine = percorsoImmagine;
	}
	
	public Seme getSeme() {
		return seme;
	}
	
	public Valore getValore() {
		return valore;
	}
	
	public String getPercorsoImmagine() {
		return percorsoImmagine;
	}
	
	/**
	 * @return La carta formata da seme e valore
	 */
	@Override
	public String toString() {
		return valore + " di " + seme;
	}
}
