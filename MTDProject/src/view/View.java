package view;

import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("deprecation")
public class View extends JFrame implements Observer {
    
    public View() {
        super("JTressette");

        try {
            Image image = ImageIO.read(getClass().getResource("/img/logo.png"));
            setIconImage(image);
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Errore nel caricamento dell'immagine: " + e.getMessage());
        }
		setLocationRelativeTo(null);

        setSize(300, 200);

        
        PannelloRegole pannello = new PannelloRegole();
        add(pannello);

		setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        
    }
}