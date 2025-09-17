package view;

import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.*;

import com.formdev.flatlaf.FlatLightLaf;

@SuppressWarnings("deprecation")
public class View extends JFrame implements Observer {
    
    public View() {
        super("JTressette");
        FlatLightLaf.setup();
        try {
            Image image = ImageIO.read(getClass().getResource("/img/logo.png"));
            setIconImage(image);
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Errore nel caricamento dell'immagine: " + e.getMessage());
        }
		setLocationRelativeTo(null);

        setSize(800, 600);

        
        //PannelloRegole pannelloRegole = new PannelloRegole();
        //add(pannelloRegole);
        
        PannelloMenu pannelloMenu = new PannelloMenu();
        add(pannelloMenu);

		setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        
    }
}