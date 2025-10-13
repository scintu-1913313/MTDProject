package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import carte.*;

public class CartaView extends JPanel{
	
	private Carta carta;
    private Image immagine;
    private static int CARTA_LARGHEZZA_PC = 50;
    private static int CARTA_ALTEZZA_PC = 90;
    private static int CARTA_LARGHEZZA_UTENTE = 80;
    private static int CARTA_ALTEZZA_UTENTE = 130;
    private boolean mouseOver = false;
    private boolean coperta;
    private boolean ruotata;

    public CartaView(Carta carta, boolean coperta, boolean ruotata) {
        this.carta = carta;
        this.coperta = coperta;
        this.ruotata = ruotata;

        int larghezza;
        int altezza;
        String path = "";
        //se la carta e' coperta e di un pc, non del giocatore
    	if(coperta == true){
    		path = carta.getPercorsoImmagineRetro();
    		larghezza = CARTA_LARGHEZZA_PC;
            altezza = CARTA_ALTEZZA_PC;
    	}
    	else {
    		path = carta.getPercorsoImmagine();
    		larghezza = CARTA_LARGHEZZA_UTENTE;
            altezza = CARTA_ALTEZZA_UTENTE;
		}
    	
        setPreferredSize(new Dimension(larghezza, altezza));
        setOpaque(false); // Assicura che il pannello sia opaco
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // top, left, bottom, right
        


    	if (ruotata) {
            setPreferredSize(new Dimension(altezza, larghezza));
        } else {
            setPreferredSize(new Dimension(larghezza, altezza));
        }
        try {

			ImageIcon immagineCorrente = new ImageIcon(getClass().getResource(path));
	        Image immagineCorrenteRidotta = immagineCorrente.getImage().getScaledInstance(larghezza, altezza, Image.SCALE_SMOOTH);
	        immagine = immagineCorrenteRidotta;
		} 
		catch 
		(Exception e) {
			System.out.println("Errore nella lettura del file: "+ path+"; Errore: " +e.getMessage());
		}
        
        //se sono il giocatore vero do' la possibilita' di cliccare le carte
        if(coperta == false) {
	        addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseEntered(MouseEvent e) {
	                mouseOver = true;
	                setBorder(BorderFactory.createLineBorder(Color.RED, 2));
	                repaint();
	            }
	            
	            @Override
	            public void mouseExited(MouseEvent e) {
	                mouseOver = false;
	                setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	                repaint();
	            }
	        });
        }
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int larghezza;
        int altezza;
        if(coperta == true){
    		larghezza = CARTA_LARGHEZZA_PC;
            altezza = CARTA_ALTEZZA_PC;
    	}
    	else {
    		larghezza = CARTA_LARGHEZZA_UTENTE;
            altezza = CARTA_ALTEZZA_UTENTE;
		}
        
        if (immagine != null) {
            Graphics2D g2d = (Graphics2D) g.create();

            if (ruotata) {
                // Trasla e ruota il contesto grafico
                g2d.translate(getWidth() / 2, getHeight() / 2);
                g2d.rotate(Math.toRadians(90));
                g2d.drawImage(immagine, -larghezza / 2, -altezza / 2, this);
            } else {
                g2d.drawImage(immagine, 0, 0, getWidth(), getHeight(), this);
            }

            g2d.dispose();
        }
    }
    
    public Carta getCarta() {
        return carta;
    }
}
