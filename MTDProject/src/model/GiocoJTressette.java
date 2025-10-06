package model;
import java.util.Observable;
import java.util.Observer;


@SuppressWarnings("deprecation")
public class GiocoJTressette extends Observable implements Observer {
	private GestoreUtente gestoreUtente;
    public GiocoJTressette() {
    	this.gestoreUtente = new GestoreUtente();
    	notifyObservers(gestoreUtente.getGiocatore());
    }

    @Override
    public void update(Observable o, Object arg) {
        // Implementazione vuota o logica di aggiornamento
    }
}