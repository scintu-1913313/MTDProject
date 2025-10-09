package controller;


import model.GiocoJTressette;
import view.View;
import view.PannelloAccount;


@SuppressWarnings("deprecation")
public class Controller {
	
	private GiocoJTressette modello;
	private View vista;

    /**
     * Class constructor.
     */
    public Controller(GiocoJTressette model, View view) {
        this.modello = model;
        this.vista = view;
        
		/*
		 * il pannello statistiche deve osservare il modello del gioco per avere
		 * conteggi aggiornati di partite vinte e giocate
		 */
		modello.addObserver(vista.getPannelloAccount());
		PannelloAccount p = (PannelloAccount) vista.getPannelloAccount(); 
		p.getBottoneSalvataggioDati().addActionListener(e -> { modello.aggiornaDatiGiocatore(p.getAvatar(), p.getNickname());});
    }
    
    public void start() {
    	modello.init();
    }

}