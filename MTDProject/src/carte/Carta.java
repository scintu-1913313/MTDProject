package carte;

import model.Pair;

/**
 * Rappresenta una singola carta da gioco con seme, valore, tipo di mazzo
 * e percorsi alle immagini fronte/retro.
 */
public class Carta {
	/** Il seme della carta (coppe, denari, spade, bastoni). */
	private final Seme seme;
	/** Il valore della carta (asso, due, tre, ..., re). */
	private final Valore valore;
	/** Percorso relativo alla risorsa immagine del fronte della carta. */
	private final String percorsoImmagine;
	/** Percorso relativo alla risorsa immagine del retro della carta. */
	private final String percorsoImmagineRetro;
	/** Il tipo di mazzo a cui appartiene la carta (es. napoletane, piacentine). */
	private final TipoMazzo tipoMazzo;
	
	/**
	 * Costruisce una carta con tipo di mazzo, seme, valore e percorsi immagine.
	 * @param tipoMazzo tipo del mazzo a cui appartiene la carta
	 * @param seme il seme della carta
	 * @param valore il valore della carta
	 * @param percorsoImmagine path dell'immagine fronte
	 * @param percorsoImmagineRetro path dell'immagine retro
	 */
	public Carta(TipoMazzo tipoMazzo, Seme seme, Valore valore, String percorsoImmagine, String percorsoImmagineRetro){
		this.tipoMazzo = tipoMazzo;
		this.seme = seme;
		this.valore = valore;
		this.percorsoImmagine = percorsoImmagine;
		this.percorsoImmagineRetro = percorsoImmagineRetro;
	}
	
	/**
	 * Restituisce il tipo di mazzo di questa carta.
	 * @return il TipoMazzo associato
	 */
	public TipoMazzo getTipoMazzo() {
		return tipoMazzo;
	}

	/**
	 * Restituisce il seme della carta.
	 * @return il Seme della carta
	 */
	public Seme getSeme() {
		return seme;
	}
	
	/**
	 * Restituisce il valore della carta.
	 * @return il Valore della carta
	 */
	public Valore getValore() {
		return valore;
	}
	
	/**
	 * Restituisce il percorso dell'immagine fronte della carta.
	 * @return path immagine fronte
	 */
	public String getPercorsoImmagine() {
		return percorsoImmagine;
	}
	
	/**
	 * Restituisce il percorso dell'immagine retro della carta.
	 * @return path immagine retro
	 */
	public String getPercorsoImmagineRetro() {
		return percorsoImmagineRetro;
	}
	
	/**
	 * Restituisce una stringa che rappresenta la carta.
	 * Il formato è: "valore di seme".
	 *
	 * @return la rappresentazione testuale della carta
	 */
	@Override
	public String toString() {
		return valore + " di " + seme;
	}
	
	/**
	 * Confronto basato su tipo di mazzo, seme e valore.
	 * @param obj oggetto da confrontare
	 * @return true se rappresentano la stessa carta
	 */
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
        if (!(obj instanceof Carta)) return false;

	    Carta other = (Carta) obj;
	    return seme == other.seme && valore == other.valore && tipoMazzo == other.tipoMazzo;
	}

	/**
	 * Calcola il codice hash dell'oggetto Carta basato su seme e valore.
	 * Garantisce coerenza con equals() per l'utilizzo in strutture hash.
	 *
	 * @return il codice hash calcolato
	 */
	@Override
	public int hashCode() {
	    int result = seme != null ? seme.hashCode() : 0;
	    result = 31 * result + (valore != null ? valore.hashCode() : 0);
	    return result;
	}

}
