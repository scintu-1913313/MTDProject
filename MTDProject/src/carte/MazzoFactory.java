package carte;

/**
 * Classe astratta che definisce una factory per la creazione di mazzi di carte.
 * Le sottoclassi devono implementare il metodo creaMazzo() per generare
 * un insieme di carte.
 * Include metodi di utilità per la generazione dei percorsi alle immagini
 * frontali e retro delle carte.
 */
public abstract class MazzoFactory {
	
	/**
     * Crea e restituisce un mazzo di carte.
     * L'implementazione concreta nelle varie factory deve definire la logica di costruzione.
     *
     * @return un'istanza di Mazzo contenente tutte le carte.
     */
    public abstract Mazzo creaMazzo();
    
    /**
	 * Restituisce il percorso dell'immagine frontale della carta.
	 *
	 * @param seme il seme della carta
	 * @param valore il valore della carta
	 * @param nomeTipoMazzo il tipo di mazzo
	 * @return il percorso relativo dell'immagine della carta
	 */
	protected String getPath(Seme seme, Valore valore, TipoMazzo nomeTipoMazzo) {
		
		return "/img/" + nomeTipoMazzo +"/" + valore + "_" + seme + ".png";
	}
	
	/**
	 * Restituisce il percorso dell'immagine del retro della carta.
	 *
	 * @param nomeTipoMazzo il tipo di mazzo
	 * @return il percorso relativo dell'immagine del retro
	 */
	protected String getPathRetro(TipoMazzo nomeTipoMazzo) {
		
		return "/img/" + nomeTipoMazzo +"/retro.png";
	}
}
