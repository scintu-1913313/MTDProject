package model;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import javax.swing.JOptionPane;
import carte.*;

@SuppressWarnings("deprecation")
public class Model extends Observable implements Observer {
	
	private GestoreUtente gestoreUtente;
    private PartitaTressette partitaCorrente;

    public Model() {
    	this.gestoreUtente = new GestoreUtente();
    }

    public void init() {
    	setChanged();
    	notifyObservers(gestoreUtente.getUtente());
    }
    
    @Override
    public void update(Observable o, Object arg) {
        // Implementazione vuota o logica di aggiornamento
    }
    
    public void aggiornaDatiUtente(Avatar a, String nickname) {
    	
    	if (nickname.isEmpty()) {
    		return;
    	}
    	gestoreUtente.aggiornaDatiUtente(a.getValore(),nickname);
    }
    
    public void iniziaGioco(int numGiocatori, int punteggio, TipoMazzo tipoMazzo, boolean accusa) {

        Mazzo mazzo = new Mazzo.MazzoBuilder().generaCarte(tipoMazzo).mescola().build();
        partitaCorrente = new PartitaTressette(this, mazzo,numGiocatori,punteggio,accusa);
        setChanged();
        notifyObservers(partitaCorrente);
    }
    
    public Utente getUtente() {
    	return this.gestoreUtente.getUtente();
    }
}