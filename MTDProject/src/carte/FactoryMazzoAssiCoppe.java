package carte;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory concreta per la creazione di un mazzo di soli assi di coppe per ogni tipo di mazzo.
 * Utilizza sia il tipo di mazzo NAPOLETANE che PIACENTINE e costuisce il mazzo.
 */
public class FactoryMazzoAssiCoppe extends MazzoFactory {

	/**
     * Crea e restituisce un mazzo di soli ASSI DI COPPE per ogni tipo di mazzo disponibile
     * @return un'istanza di Mazzo contenente tutti gli assi.
     */
    @Override
    public Mazzo creaMazzo() {
        List<Carta> carte = new ArrayList<>();
        for(TipoMazzo nomeTipoMazzo: TipoMazzo.values()) {
			carte.add(new Carta(nomeTipoMazzo, Seme.COPPE,Valore.ASSO,getPath(Seme.COPPE,Valore.ASSO,nomeTipoMazzo),getPathRetro(nomeTipoMazzo)));
        }
        return new Mazzo(carte);
    }
}