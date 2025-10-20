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

    /**
     * Costruisce il modello dell'applicazione e inizializza il gestore intero dell'utente.
     */
    public Model() {
    	this.gestoreUtente = new GestoreUtente();
    }

    /**
     * Inizializza lo stato del modello e notifica la vista con lo stato iniziale dell'utente.
     * Notifica alla vista lo stato iniziale dell'utente per far aggiornare i dati sul pannello utente
     */
    public void init() {
    	notificaAgliObserver(gestoreUtente.getUtente());
    }
    
    /**
     * Metodo chiamato quando un oggetto osservato si aggiorna.
     * Override opzionale: mantiene comportamento vuoto in questo modello.
     */
    @Override
    public void update(Observable o, Object arg) {
        // Implementazione vuota
    }
    
    /**
     * Aggiorna i dati dell'utente tramite il GestoreUtente.
     * @param a avatar selezionato dall'utente
     * @param nickname nickname scelto dall'utente
     */
    public void aggiornaDatiUtente(Avatar a, String nickname) {
    	
    	if (nickname.isEmpty()) {
    		return;
    	}
    	gestoreUtente.aggiornaDatiUtente(a.getValore(),nickname);
    }
    
    /**
     * Crea e inizializza una nuova partita con le impostazioni fornite.
     * Notifica agli observer la nuova partita creata per visualizzare il banco di gioco
     * @param numGiocatori numero di partecipanti (2-4)
     * @param punteggio punteggio obiettivo per la vittoria (11,21,31,41)
     * @param tipoMazzo tipo di mazzo da usare (Napoletane, Piacentine)
     * @param accusa true per abilitare la modalità con le accuse
     */
    public void iniziaGioco(int numGiocatori, int punteggio, TipoMazzo tipoMazzo, boolean accusa) {
    	//costruisco una nuova partita e lo notifico alla vista per far aggiornare i pannelli delle carte
        Mazzo mazzo = new Mazzo.MazzoBuilder().generaCarte(tipoMazzo).mescola().build();
        partitaCorrente = new PartitaTressette(this, mazzo,numGiocatori,punteggio,accusa);
        notificaAgliObserver(partitaCorrente);
    }
    
    /**
     * Termina la partita corrente come uscita forzata (considerata sconfitta).
     */
    public void terminaParitaUscitaForzata() {
    	scriviEsitoPartita(EsitoPartita.PERSA,0); //partita persa per reset con punteggio 0
    	partitaCorrente = null;
    }
    
    /**
     * Termina la partita corrente determinando l'esito e salvandolo nello storico utente.
     */
    public void terminaParitaCompletata() {
    	EsitoPartita esitoPartita = partitaCorrente.controlloVittoria();
    	int punteggio = (int) partitaCorrente.getPunteggioTotaleUtenteOCarteSquadra1();
    	scriviEsitoPartita(esitoPartita,punteggio); 
    	partitaCorrente = null;
    }

    /**
     * Registra l'esito di una partita nello storico utente.
     * Notifica alla vista i nuovi dati per aggiornare lo storico delle partite dell'utente.
     * @param esitoParita esito della partita
     * @param punteggioOttenuto punteggio ottenuto dal giocatore locale
     */
    public void scriviEsitoPartita(EsitoPartita esitoParita, int punteggioOttenuto) {
    	//aggiorno le partite dell'utente e lo notifico alla vista per far aggiornare le statische sul pannello utente 
    	LocalDateTime dataFine = LocalDateTime.now();
    	Partita p =new Partita(esitoParita,punteggioOttenuto,dataFine);
    	gestoreUtente.aggiornaDatiUtente(p);
    	notificaAgliObserver(gestoreUtente.getUtente());
    }
    
    /**
     * Restituisce l'istanza `Utente` corrente gestita dal modello.
     * @return Utente corrente
     */
    public Utente getUtente() {
    	return this.gestoreUtente.getUtente();
    }
    
    /**
	 * Notifica gli observer registrati passando l'oggetto specificato.
	 * @param o oggetto passato agli observer
	 */
    public void notificaAgliObserver(Object o) {
		setChanged();
		notifyObservers(o);
    }
}