package view;

import java.awt.*;
import javax.swing.JPanel;

/** 
 * Pannello semplice con angoli arrotondati e sfondo custom.
 */
public class PannelloSemplice extends JPanel {

    /** Larghezza dell'arco degli angoli arrotondati. */
	private int arcWidth = 30;

    /** Altezza dell'arco degli angoli arrotondati. */
    private int arcHeight = 30;

    /**
     * Costruisce un pannello semplice con angoli arrotondati e sfondo custom.
     */
    public PannelloSemplice() {
        setOpaque(false);
    }

    /**
     * Disegna il pannello con angoli arrotondati e sfondo custom.
     * @param g contesto grafico
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Cancella tutto con trasparenza
        g2.setComposite(AlphaComposite.Clear);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Ripristina composizione normale
        g2.setComposite(AlphaComposite.SrcOver);
        g2.setColor(View.VERDE_PANNELLO); // es. bianco semi-trasparente
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);

        g2.dispose();
    }
}