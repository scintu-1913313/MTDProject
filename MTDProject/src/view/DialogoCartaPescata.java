package view;

import carte.Carta;
import java.awt.*;
import javax.swing.*;

/**
 * Dialogo che mostra la carta pescata da un giocatore in un pannello decorato con chiusura automatica a tempo.
 */
public class DialogoCartaPescata extends MioDialog {

	/** Pannello dove verra visualizzata la carta. */
	private JPanel pannelloCartaCentrale;

    /** Pannello centrale del dialogo. */
	private JPanel pannelloCentrale;

	/** Larghezza del dialogo*/
	private int larghezza = 180;
	
	/** Altezza del dialogo*/
	private int altezza = 200;
    /**
     * Costruisce e mostra un dialogo con una carta da mostrare e con chiusura automatica a tempo.
     * @param owner finestra proprietaria
     * @param titolo titolo del dialog
     * @param giocatore il giocatore che ha pescato la carta
     * @param cartaPescata la carta da mostrare
     * @param durataMillis durata in ms prima della chiusura automatica
     */
    public DialogoCartaPescata(Frame owner, String titolo, String giocatore, Carta cartaPescata, int durataMillis) {
    	super(owner, titolo,180,200);
        setUndecorated(true);

        // Pannello con angoli arrotondati e sfondo verde
        pannelloCentrale = new JPanel() {
            /*
             * Disegna il pannello con angoli arrotondati e sfondo semi-trasparente.
             * @param g contesto grafico
             */
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 128, 0, 230)); // verde semi-trasparente
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
            }
        };
       
        // Etichetta del messaggio
        MioLabel labelGiocatore = new MioLabel(giocatore + " pesca:");
        labelGiocatore.setHorizontalAlignment(SwingConstants.CENTER);
        labelGiocatore.setVerticalAlignment(SwingConstants.CENTER);
        labelGiocatore.setFont(View.FONT_GIOCO);
        labelGiocatore.setForeground(Color.BLACK);
       
        pannelloBottoni.setVisible(false);
        pannelloCentrale.setOpaque(false);
        pannelloCentrale.setLayout(new GridBagLayout());
        pannelloCentrale.setPreferredSize(new Dimension(larghezza, altezza));
        pannelloCentrale.setSize(larghezza, altezza);
        pannelloCentrale.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
       
        pannelloCartaCentrale = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pannelloCartaCentrale.setOpaque(false);

        CartaView cartaView = new CartaView(cartaPescata,false,false,false);
		pannelloCartaCentrale.add(cartaView,cartaView.toString());
   	
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        pannelloCentrale.add(labelGiocatore, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        pannelloCentrale.add(pannelloCartaCentrale, gbc);

        setContentPane(pannelloCentrale);
        getRootPane().setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
        
        // Posizionamento leggermente sopra il centro del frame
        int x = owner.getX() + (owner.getWidth() - getWidth()) / 2;
        int y = owner.getY() + (owner.getHeight() - getHeight()) / 2;
        setLocation(x, y);
       
        // Timer per chiusura automatica
        Timer timer = new Timer(durataMillis, e -> dispose());
        timer.setRepeats(false);
        timer.start();
    }
}
