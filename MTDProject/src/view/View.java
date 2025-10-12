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

@SuppressWarnings("deprecation")
public class View extends JFrame implements Observer {
    
    //private PannelloMenu pannelloMenu;
    //private PannelloRegole pannelloRegole;
    private PannelloPrincipale pannelloPrincipale;
    private PannelloGioco pannelloGioco;
    //private PannelloAccount pannelloAccount;

    public View() {
        super("JTressette");
        //FlatLightLaf.setup();
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
    
    
    public PannelloAccount getPannelloAccount() {
    	return pannelloPrincipale.getPannelloAccount();
    }
    
    public PannelloMenu getPannelloMenu() {
    	return pannelloPrincipale.getPannelloMenu();
    }
    
    @Override
    public void update(Observable o, Object arg) {
        // Implementazione Observer se necessaria
    }
}