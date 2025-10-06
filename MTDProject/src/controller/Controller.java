package controller;


import model.GiocoJTressette;
import view.View;


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
		//vista.getPannelloAccount().bottoneSalvaModifiche.addActionListener(e -> { ...
    }
    
    public void start() {
    	modello.init();
    }

}