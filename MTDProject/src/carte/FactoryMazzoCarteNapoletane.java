package carte;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory concreta per la creazione di un mazzo completo di carte napoletane.
 * Utilizza il tipo di mazzo NAPOLETANE e costuisce il mazzo.
 */
public class FactoryMazzoCarteNapoletane extends MazzoFactory {

	/**
	 * Costruttore di default
	 */
	public FactoryMazzoCarteNapoletane() {
	}
	
    /** Il tipo di mazzo gestito da questa factory ovvero NAPOLETANE. */
	private final TipoMazzo tipoMazzoNapoletane = TipoMazzo.NAPOLETANTE;
	
	/**
     * Crea e restituisce un mazzo completo di carte napoletane.
     * @return un'istanza di Mazzo contenente tutte le carte napoletane.
     */
    @Override
    public Mazzo creaMazzo() {
        List<Carta> carte = new ArrayList<>();
        for(Seme seme: Seme.values()) {
			for(Valore valore: Valore.values()){
				carte.add(new Carta(tipoMazzoNapoletane,seme,valore,getPath(seme,valore,tipoMazzoNapoletane),getPathRetro(tipoMazzoNapoletane)));
			}
		}
        return new Mazzo(carte);
    }
}