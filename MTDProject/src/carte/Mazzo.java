package carte;

import java.util.List;
import java.util.ArrayList;

/**
 * Rappresenta un mazzo di carte. Incapsula la lista di carte e fornisce
 * operazioni di base come mescolare e ottenere la lista. La creazione
 * avviene tramite il {@link Mazzo.MazzoBuilder}.
 */
public class Mazzo {
	/** Lista interna delle carte che compongono il mazzo. */
	private final List<Carta> carte;
	
	/**
	 * Costruttore privato usato dal builder per creare il mazzo.
	 * @param builder builder contenente la lista di carte
	 */
	private Mazzo(MazzoBuilder builder) {
		this.carte = builder.carte;
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
	
	/**
	 * Builder per la creazione e configurazione di un mazzo di carte.
	 * Permette di generare un mazzo completo di carte di uno specifico tipo di mazzo
	 * o di costruire un mazzo di soli assi.
	 */
	public static class MazzoBuilder {
		/**
		 * Lista interna delle carte generate per il mazzo.
		 */
		private final List<Carta> carte = new ArrayList<>();
		
		/**
		 * Genera tutte le carte standard per il tipo di mazzo specificato.
		 * Crea un mazzo completo di carte.
		 *
		 * @param nomeTipoMazzo il tipo di mazzo da cui derivare le carte.
		 * @return il builder stesso con il mazzo generato.
		 */
		public MazzoBuilder generaCarte(TipoMazzo nomeTipoMazzo) {
			for(Seme seme: Seme.values()) {
				for(Valore valore: Valore.values()){
					carte.add(new Carta(nomeTipoMazzo,seme,valore,getPath(seme,valore,nomeTipoMazzo),getPathRetro(nomeTipoMazzo)));
				}
			}
			return this;
		}

		/**
		 * Costurisce un mazzo di soli assi di coppe, per ogni tipo di mazzo disponibile.
		 *
		 * @return il builder stesso con il mazzo di carte di soli assi di coppe.
		 */
		public MazzoBuilder generaAssoDeiMazzi() {
			for(TipoMazzo nomeTipoMazzo: TipoMazzo.values()) {
					carte.add(new Carta(nomeTipoMazzo, Seme.COPPE,Valore.ASSO,getPath(Seme.COPPE,Valore.ASSO,nomeTipoMazzo),getPathRetro(nomeTipoMazzo)));
			}
			
			return this;

		}	

		/**
		 * Restituisce il percorso dell'immagine frontale della carta.
		 *
		 * @param seme il seme della carta
		 * @param valore il valore della carta
		 * @param nomeTipoMazzo il tipo di mazzo
		 * @return il percorso relativo dell'immagine della carta
		 */
		public String getPath(Seme seme, Valore valore, TipoMazzo nomeTipoMazzo ) {
			
			return "/img/" + nomeTipoMazzo +"/" + valore + "_" + seme + ".png";
		}
		
		/**
		 * Restituisce il percorso dell'immagine del retro della carta.
		 *
		 * @param nomeTipoMazzo il tipo di mazzo
		 * @return il percorso relativo dell'immagine del retro
		 */
		public String getPathRetro(TipoMazzo nomeTipoMazzo ) {
			
			return "/img/" + nomeTipoMazzo +"/retro.png";
		}

		/**
		 * Mescola casualmente le carte attualmente presenti nel builder.
		 *
		 * @return il builder stesso per chiamate fluide
		 */
		public MazzoBuilder mescola() {
			java.util.Collections.shuffle(carte);
			return this;
		}
		
		/**
		 * Costruisce l'oggetto {@link Mazzo} finale con le carte generate.
		 *
		 * @return una nuova istanza di {@link Mazzo}
		 */
		public Mazzo build() {
			return new Mazzo(this);
		}
	}
}
