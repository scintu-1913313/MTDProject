package view;

import java.awt.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.*;
import controller.Controller;


/**
 * Finestra principale dell'applicazione che estende il Frame. Inizializza e gestisce i pannelli
 * principali (menu, account, gioco) e funge da punto di collegamento tra
 * la UI e il Controller.
 */
@SuppressWarnings("deprecation")
public class View extends JFrame implements Observer {

    /** Colore verde del tavolo da gioco. */
	public static final Color VERDE_TAVOLO = new Color(2, 86, 14);
    /** Colore verde usato per l'effetto hover sui bottoni. */
    public static final Color VERDE_HOVER = new Color(70, 180, 70);
    /** Colore chiaro usato per i bordi dei bottoni e pannelli. */
    public static final Color BORDO_CHIARO = new Color(255, 255, 255);
    /** Colore trasparente usato per sfondi. */
    public static final Color TRASPARENTE = new Color(0, 0, 0, 0);
    /** Colore verde chiaro usato per i pannelli. */
    public static final Color VERDE_PANNELLO = new Color(0, 128, 0);
    /** Colore verde scuro usato per testi e dettagli. */
    public static final Color VERDE_SCURO= new Color(0, 90, 0);
    /** Font principale usato nell'applicazione. */
    public static final Font FONT_GIOCO = new Font("Georgia", Font.BOLD, 14);

    /** Riferimento al controller dell'applicazione. */
    private Controller controller;

    /** Riferimento al pannello principale che raggruppa i pannelli navigabili. */
    private PannelloPrincipale pannelloPrincipale;

    /** Riferimento al pannello di gioco. */
    private PannelloGioco pannelloGioco;

    /**
     * Costruisce la finestra principale dell'applicazione e inizializza pannelli interni.
     * Viene visualizzato il pannello menu all'avvio.
     */
    public View() {
        super("JTressette");

        try {
            Image image = ImageIO.read(getClass().getResource("/img/logo.png"));
            if (Taskbar.isTaskbarSupported()) {
                Taskbar taskbar = Taskbar.getTaskbar();
                taskbar.setIconImage(image);
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Errore nel caricamento dell'immagine: " + e.getMessage());
        }

		setLocationRelativeTo(null);
        setLayout(new CardLayout());
        setSize(Pannello.LARGHEZZA, Pannello.ALTEZZA);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        pannelloPrincipale = new PannelloPrincipale(this);
        pannelloGioco = new PannelloGioco(this);

        setContentPane(pannelloPrincipale);
        showPannelloMenu();
        setVisible(true);
    }

    /**
     * Imposta il controller dell'applicazione usato per le azioni di alto livello.
     * @param controller il Controller
     */
    public void setController(Controller controller) {
    	this.controller = controller;
    }

    /**
     * Mostra il pannello menu.
     */
    public void showPannelloMenu() 
    {
        pannelloPrincipale.showPanel("MENU");
        setContentPane(pannelloPrincipale);
        revalidate();
        repaint();
    }

    /**
     * Mostra il pannello regole.
     */
    public void showPannelloRegole() 
    {
        pannelloPrincipale.showPanel("REGOLE");
        setContentPane(pannelloPrincipale);
        revalidate();
        repaint();
    }
    
    /**
     * Mostra il pannello account.
     */
    public void showPannelloAccount() {
    	pannelloPrincipale.showPanel("ACCOUNT");
        setContentPane(pannelloPrincipale);
        revalidate();
        repaint();
    }

    /**
     * Mostra il pannello di gioco.
     */
    public void showPannelloGioco() {
    	setContentPane(pannelloGioco);
        revalidate();
        repaint();
    }

    /**
     * Restituisce il pannello di gioco.
     * @return pannello di gioco
     */
    public PannelloGioco getPannelloGioco() {
    	return pannelloGioco;
    }

    /**
     * Restituisce il pannello account.
     * @return pannello account
     */
    public PannelloAccount getPannelloAccount() {
    	return pannelloPrincipale.getPannelloAccount();
    }
    
    /**
     * Restituisce il pannello menu.
     * @return pannello menu
     */
    public PannelloMenu getPannelloMenu() {
    	return pannelloPrincipale.getPannelloMenu();
    }
    
    /**
     * Richiama il Controller per aggiornare i dati dell'utente.
     */
    public void aggiornaDatiUtente() {
    	controller.aggiornaDatiUtente();
    }
    
    /**
     * Invial al controller la richiesta per iniziare una nuova partita.
     */
    public void iniziaGioco() {
    	controller.iniziaGioco();
    }
    
    /**
     * Notifica al controller l'uscita forzata dalla partita e torna al menu.
     */
	public void uscitaForzataDalGiocatore() {
		controller.notificaUscitaForzata();
		ritotnoAlMenu();
	}

    /**
     * Notifica al controller la fine di una partita e torna al menu.
     */
	public void terminaParita() {
		controller.notificaTerminaPartita();
		ritotnoAlMenu();
	}
	
    /**
     * Torna al menu principale visualizzando pannello menu.
     */
	public void ritotnoAlMenu() {
		showPannelloMenu();
	}
	
	/**
	 * override opzionale per l'aggiornamento della vista in base ai cambiamenti del modello.
	 */
    @Override
	public void update(Observable o, Object arg) {
	    // vuoto
	}
}