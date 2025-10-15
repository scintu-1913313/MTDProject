package model;
import java.time.LocalDateTime;
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
    	//notifico alla vista lo stato iniziale dell'utente per far aggiornare i dati sul pannello utente 
    	notificaAgliObserver(gestoreUtente.getUtente());
    	GestoreAudio.getInstance().setMusicEnabled(true);
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
    	//costruisco una nuova partita e lo notifico alla vista per far aggiornare i pannelli delle carte
        Mazzo mazzo = new Mazzo.MazzoBuilder().generaCarte(tipoMazzo).mescola().build();
        partitaCorrente = new PartitaTressette(this, mazzo,numGiocatori,punteggio,accusa);
        notificaAgliObserver(partitaCorrente);
    }
    
    public void terminaParitaUscitaForzata() {
    	scriviEsitoPartita(false,0); //partita persa per reset con punteggio 0
    	partitaCorrente = null;
    }
    
    public void scriviEsitoPartita(boolean vinta, int punteggioOttenuto) {
    	//aggiorno le partite dell'utente e lo notifico alla vista per far aggiornare le statische sul pannello utente 
    	LocalDateTime dataFine = LocalDateTime.now();
    	Partita p =new Partita(vinta,punteggioOttenuto,dataFine);
    	gestoreUtente.aggiornaDatiUtente(p);
    	notificaAgliObserver(gestoreUtente.getUtente());
    }
    
    public Utente getUtente() {
    	return this.gestoreUtente.getUtente();
    }
    
    public void notificaAgliObserver(Object o) {
		setChanged();
		notifyObservers(o);
    }
}