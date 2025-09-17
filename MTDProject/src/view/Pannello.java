package view;

import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Pannello extends JPanel implements Observer {

	protected static final Color TRASPARENTE = new Color(0, 0, 0, 0);
	protected static final Color VERDE_HOVER = new Color(0, 128, 0);
	protected BufferedImage sfondo;

	public Pannello(LayoutManager layout) {
		super(layout);
		setBorder(new EmptyBorder(20, 80, 20, 80));
	}
	
    
    @Override
	public void update(Observable o, Object arg) {
	    // Puoi lasciare vuoto o aggiungere logica specifica
	    System.out.println("pannelloMenu aggiornato con: " + arg);
	}
}
