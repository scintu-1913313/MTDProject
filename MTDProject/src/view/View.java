package view;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.*;
import view.Pannello;
import com.formdev.flatlaf.FlatLightLaf;

import controller.Controller;

@SuppressWarnings("deprecation")
public class View extends JFrame implements Observer {
    
    //private PannelloMenu pannelloMenu;
    //private PannelloRegole pannelloRegole;
    private Controller controller;
    private PannelloPrincipale pannelloPrincipale;
    private PannelloGioco pannelloGioco;
    //private PannelloAccount pannelloAccount;

    public View() {
        super("JTressette");

        try {
            Image image = ImageIO.read(getClass().getResource("/img/logo.png"));
            setIconImage(image);
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
        //pannelloAccount = new PannelloAccount(this);

        setContentPane(pannelloPrincipale);
        showPannelloMenu();
        //pannelloRegole = new PannelloRegole();
        //add(pannelloRegole,"REGOLE");
        
        //pannelloMenu = new PannelloMenu();
        //add(pannelloMenu,"MENU");

        setVisible(true);
    }

    public void setController(Controller controller) {
    	this.controller = controller;
    }
    public void showPannelloMenu() 
    {
        pannelloPrincipale.showPanel("MENU");
        setContentPane(pannelloPrincipale);
        revalidate();
        repaint();
    }

    public void showPannelloRegole() 
    {
        pannelloPrincipale.showPanel("REGOLE");
        setContentPane(pannelloPrincipale);
        revalidate();
        repaint();
    }
    
    public void showPannelloAccount() {
    	pannelloPrincipale.showPanel("ACCOUNT");
        setContentPane(pannelloPrincipale);
        revalidate();
        repaint();
    }

    public void showPannelloGioco() {
    	setContentPane(pannelloGioco);
        revalidate();
        repaint();
    }
    
    public PannelloGioco getPannelloGioco() {
    	return pannelloGioco;
    }
    public PannelloAccount getPannelloAccount() {
    	return pannelloPrincipale.getPannelloAccount();
    }
    
    public PannelloMenu getPannelloMenu() {
    	return pannelloPrincipale.getPannelloMenu();
    }
    
    public void aggiornaDatiUtente() {
    	controller.aggiornaDatiUtente();
    }
    
    public void iniziaGioco() {
    	controller.iniziaGioco();
    }
    
    
	public void uscitaForzataDalGiocatore() {
		controller.notificaUscitaForzata();
		ritotnoAlMenu();
	}

	public void terminaParita() {
		controller.notificaTerminaPartita();
		ritotnoAlMenu();
	}
	
	public void ritotnoAlMenu() {
		showPannelloMenu();
	}

    @Override
    public void update(Observable o, Object arg) {
        // Implementazione Observer se necessaria
    }
}