package model;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;


@SuppressWarnings("deprecation")
public class GiocoJTressette extends Observable implements Observer {
	
	private GestoreUtente gestoreUtente;
	
    public GiocoJTressette() {
    	this.gestoreUtente = new GestoreUtente();
    }

    public void init() {
    	setChanged();
    	notifyObservers(gestoreUtente.getGiocatore());
    }
    
    @Override
    public void update(Observable o, Object arg) {
        // Implementazione vuota o logica di aggiornamento
    }
    
    public void aggiornaDatiGiocatore(Avatar a, String nickname) {
    	
    	if (nickname.isEmpty()) {
    		return;
    	}
    	gestoreUtente.aggiornaDatiUtente(a.getValore(),nickname);
    	System.out.println("ciao sono giocojtresette");
    }
}