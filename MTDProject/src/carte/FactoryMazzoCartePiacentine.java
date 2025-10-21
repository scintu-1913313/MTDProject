package carte;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory concreta per la creazione di un mazzo completo di carte piacentine.
 * Utilizza il tipo di mazzo PIACENTINE e costuisce il mazzo.
 */
public class FactoryMazzoCartePiacentine extends MazzoFactory {

    /** Il tipo di mazzo gestito da questa factory ovvero PIACENTINE. */
	private final TipoMazzo tipoMazzoPiacentine = TipoMazzo.PIACENTINE;
	
	/**
     * Crea e restituisce un mazzo completo di carte piacentine.
     * @return un'istanza di Mazzo contenente tutte le carte piacentine.
     */
    @Override
    public Mazzo creaMazzo() {
        List<Carta> carte = new ArrayList<>();
        for(Seme seme: Seme.values()) {
			for(Valore valore: Valore.values()){
				carte.add(new Carta(tipoMazzoPiacentine,seme,valore,getPath(seme,valore,tipoMazzoPiacentine),getPathRetro(tipoMazzoPiacentine)));
			}
		}
        return new Mazzo(carte);
    }
}