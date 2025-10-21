package carte;

import java.util.List;
import java.util.ArrayList;

/**
 * Rappresenta un mazzo di carte. Incapsula la lista di carte e fornisce
 * operazioni di base come mescolare e ottenere la lista. 
 */
public class Mazzo {
	/** Lista interna delle carte che compongono il mazzo. */
	private final List<Carta> carte;
	
	/**
	 * Costruttore usato per creare il mazzo.
	 * @param carte la lista di carte
	 */
	public Mazzo(List<Carta> carte) {
		this.carte = carte;
	}
	
	/**
	 * Costruttore di copia che crea una copia profonda delle carte.
	 * @param altro mazzo da copiare
	 */
	public Mazzo(Mazzo altro) {
	    this.carte = new ArrayList<>();
	    for (Carta c : altro.getCarte()) {
	        this.carte.add(new Carta(
	            c.getTipoMazzo(),
	            c.getSeme(),
	            c.getValore(),
	            c.getPercorsoImmagine(),
	            c.getPercorsoImmagineRetro()
	        ));
	    }
	}

	/**
	 * Mescola casualmente le carte nel mazzo.
	 */
	public void mischiaCarte() {
		java.util.Collections.shuffle(this.carte);
	}
	
	/**
	 * Restituisce la lista di carte del mazzo.
	 * @return lista di Carte
	 */
	public List<Carta> getCarte() {
		return this.carte;
	}
}
