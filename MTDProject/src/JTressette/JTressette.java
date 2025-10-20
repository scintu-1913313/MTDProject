package JTressette;

import controller.Controller;
import model.Model;
import view.View;

/**
 * Classe principale che avvia l'applicazione JTressette(main).
 * Crea le istanze di View, Model e Controller e avvia il gioco.
 */
public class JTressette{

    /**
     * Costruttore di default che inizializza il gioco Tressette.
     */
    public JTressette() {
    }
    
	/**
	 * Entry point dell'applicazione: costruisce View, Model e Controller e avvia il gioco.
	 * @param args argomenti da linea di comando (ignorati)
	 */
	public static void main(String[] args) {
		System.out.println("MAIN");
		View view = new View();
		Controller c = new Controller(new Model(), view);
		view.setController(c);
		c.start();
	}
}
