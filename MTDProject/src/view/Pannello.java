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
	public static final int ALTEZZA = 800;
	public static final int LARGHEZZA = 1200;

	public Pannello(LayoutManager layout) {
		super(layout);
		setBorder(new EmptyBorder(20, 20, 20, 20));
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        g2.dispose();
    }
	
    @Override
	public void update(Observable o, Object arg) {
	    // Puoi lasciare vuoto o aggiungere logica specifica
	}
}
