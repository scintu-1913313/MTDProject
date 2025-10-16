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

		/*
		 * il pannello statistiche deve osservare il modello del gioco per avere
		 * conteggi aggiornati di partite vinte e giocate
		 */
		modello.addObserver((Observer)pannelloAccount);
		modello.addObserver((Observer)pannelloGioco);
    }
    
    public void start() {
    	modello.init();
    }
    
    public void aggiornaDatiUtente() {
    	modello.aggiornaDatiUtente(pannelloAccount.getAvatar(), pannelloAccount.getNickname());
    }

    public void iniziaGioco() {
		int numGiocatori = pannelloMenu.getNumeroGiocatori();
		int punteggioStabilito = pannelloMenu.getPunteggioStabilito(); 
		TipoMazzo tipoMazzo = pannelloMenu.getTipoMazzo();
		boolean accusa = pannelloMenu.getAccusa();
		pannelloMenu.getBottoneStart().resetToDefault();
    	modello.iniziaGioco(numGiocatori,punteggioStabilito,tipoMazzo,accusa);
    }
    
    public void notificaUscitaForzata() {
    	pannelloGioco.getBottoneUscita().resetToDefault();
    	modello.terminaParitaUscitaForzata();
    }

    public void notificaTerminaPartita() {
    	modello.terminaParitaCompletata();
    }
}