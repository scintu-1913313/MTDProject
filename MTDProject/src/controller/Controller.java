package controller;


import java.util.Observer;

import carte.TipoMazzo;
import model.Model;
import view.View;
import view.PannelloAccount;
import view.PannelloGioco;
import view.PannelloMenu;


@SuppressWarnings("deprecation")
public class Controller {
	
	private Model modello;
	private View vista;
	private PannelloMenu pannelloMenu;
	private PannelloAccount pannelloAccount;
	private PannelloGioco pannelloGioco;

    /**
     * Class constructor.
     */
    public Controller(Model model, View view) {
        this.modello = model;
        this.vista = view;
        this.pannelloMenu = vista.getPannelloMenu();
        this.pannelloAccount = vista.getPannelloAccount(); 
        this.pannelloGioco = vista.getPannelloGioco();


		//il pannello account(con i dati del giocatore) deve osservare il modello del gioco per avere
		//conteggi aggiornati di partite vinte e giocate
		modello.addObserver((Observer)pannelloAccount);

		//il pannello gioco(con la parte grafica di gioco di una partita) deve osservare il modello
		//per visualizzare e aggiornare lo stato di gioco e le carte
		modello.addObserver((Observer)pannelloGioco);
    }
    
	/**
	 * Avvia il modello (inizializza lo stato e notifica la vista).
	 */
	public void start() {
		modello.init();
	}
    
	/**
	 * Raccoglie i dati dall'interfaccia account e richiede l'aggiornamento al modello.
	 */
	public void aggiornaDatiUtente() {
		modello.aggiornaDatiUtente(pannelloAccount.getAvatar(), pannelloAccount.getNickname());
	}

	/**
	 * Preleva le impostazioni della partita dalla view e ordina al modello di iniziare la partita.
	 */
	public void iniziaGioco() {
		int numGiocatori = pannelloMenu.getNumeroGiocatori();
		int punteggioStabilito = pannelloMenu.getPunteggioStabilito(); 
		TipoMazzo tipoMazzo = pannelloMenu.getTipoMazzo();
		boolean accusa = pannelloMenu.getAccusa();
		pannelloMenu.getBottoneStart().resetToDefault();
		modello.iniziaGioco(numGiocatori,punteggioStabilito,tipoMazzo,accusa);
	}
    
	/**
	 * Gestisce la notifica di uscita forzata durante una partita: resetta UI e notifica il modello.
	 */
	public void notificaUscitaForzata() {
		pannelloGioco.getBottoneUscita().resetToDefault();
		modello.terminaParitaUscitaForzata();
	}

	/**
	 * Notifica al modello che la partita è terminata correttamente.
	 */
	public void notificaTerminaPartita() {
		modello.terminaParitaCompletata();
	}
}