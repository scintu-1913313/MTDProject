package carte;

public class Carta {
	private final Seme seme;
	private final Valore valore;
	private final String percorsoImmagine;
	private final String percorsoImmagineRetro;
	private final TipoMazzo tipoMazzo;
	
	public Carta(TipoMazzo tipoMazzo, Seme seme, Valore valore, String percorsoImmagine, String percorsoImmagineRetro){
		this.tipoMazzo = tipoMazzo;
		this.seme = seme;
		this.valore = valore;
		this.percorsoImmagine = percorsoImmagine;
		this.percorsoImmagineRetro = percorsoImmagineRetro;
	}
	
	public TipoMazzo getTipoMazzo() {
		return tipoMazzo;
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
	
	public String getPercorsoImmagineRetro() {
		return percorsoImmagineRetro;
	}
	
	/**
	 * @return La carta formata da seme e valore
	 */
	@Override
	public String toString() {
		return valore + " di " + seme;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;

	    Carta other = (Carta) obj;
	    return seme == other.seme && valore == other.valore;
	}

	@Override
	public int hashCode() {
	    int result = seme != null ? seme.hashCode() : 0;
	    result = 31 * result + (valore != null ? valore.hashCode() : 0);
	    return result;
	}

}
