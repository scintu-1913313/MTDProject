package controller;


import java.util.Observer;

import carte.TipoMazzo;
import model.Model;
import view.View;
import view.PannelloAccount;
import view.PannelloMenu;


@SuppressWarnings("deprecation")
public class Controller {
	
	private Model modello;
	private View vista;
	private PannelloMenu pannelloMenu;
	private PannelloAccount pannelloAccount;
    /**
     * Class constructor.
     */
    public Controller(Model model, View view) {
        this.modello = model;
        this.vista = view;
        this.pannelloMenu = vista.getPannelloMenu();
        this.pannelloAccount = vista.getPannelloAccount(); 

		/*
		 * il pannello statistiche deve osservare il modello del gioco per avere
		 * conteggi aggiornati di partite vinte e giocate
		 */
		modello.addObserver((Observer)pannelloAccount);
		pannelloAccount.getBottoneSalvataggioDati().addActionListener(e -> { aggiornaDatiUtente();});
		vista.getPannelloMenu().getBottoneStart().addActionListener(e -> { iniziaGioco();});
		
    }
    
    public void start() {
    	modello.init();
    }
    
    private void aggiornaDatiUtente() {
    	modello.aggiornaDatiUtente(pannelloAccount.getAvatar(), pannelloAccount.getNickname());
    }

    private void iniziaGioco() {
		int numGiocatori = pannelloMenu.getNumeroGiocatori();
		int punteggioStabilito = pannelloMenu.getPunteggioStabilito(); 
		TipoMazzo tipoMazzo = pannelloMenu.getTipoMazzo();
		boolean accusa = pannelloMenu.getAccusa();
    	modello.iniziaGioco(numGiocatori,punteggioStabilito,tipoMazzo,accusa);
    }
}