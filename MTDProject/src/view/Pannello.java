package view;

import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public abstract class Pannello extends JPanel implements Observer {

	public static final Color TRASPARENTE = new Color(0, 0, 0, 0);
	public static final Color ARANCIONE = Color.decode("#f08c00");
	
	static {
		// impostazione colori di default sui bottoni
		UIManager.put("Button.highlight", ARANCIONE);
		UIManager.put("Button.select", ARANCIONE);
		UIManager.put("Button.focus", TRASPARENTE);
		UIManager.put("Button.background", ARANCIONE);
	}
	
	public Pannello(LayoutManager layout) {
		super(layout);
		setBorder(new EmptyBorder(10, 20, 10, 20));
	}
	
    
    @Override
	public void update(Observable o, Object arg) {
	    // Puoi lasciare vuoto o aggiungere logica specifica
	    System.out.println("pannelloMenu aggiornato con: " + arg);
	}
}
