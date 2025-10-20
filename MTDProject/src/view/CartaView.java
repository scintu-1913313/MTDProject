package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;
import javax.swing.Timer;
import carte.*;

/**
 * Classe che rappresenta la vista grafica per una singola carta, con supporto per hover, animazione di shaking,
 * visualizzazione fronte/retro e rotazione.
 */
public class CartaView extends JPanel {

    /** Riferimento alla carta da visualizzare. */
    private Carta carta;

    /** Immagine della carta. */
    private Image immagine;

    /** Larghezza della carta in modalità ridotta (PC). */
    private static int CARTA_LARGHEZZA_PC = 50;

    /** Altezza della carta in modalità ridotta (PC). */
    private static int CARTA_ALTEZZA_PC = 90;

    /** Larghezza  della carta in modalità normale (utente). */
    private static int CARTA_LARGHEZZA_UTENTE = 70;

    /** Altezza della carta in modalità normale (utente). */
    private static int CARTA_ALTEZZA_UTENTE = 130;

    /** Stato di hover del mouse sulla carta. */
    private boolean mouseOver = false;

    /** Indica se la carta e' mostrata coperta (retro). */
    private boolean coperta;

    /** Indica se la carta e' ruotata di 90 gradi. */
    private boolean ruotata;

    /** Offset orizzontale per l'animazione di shaking. */
    private int shakeOffsetX = 0;

    /** Timer per l'animazione di shaking. */
    private Timer shakeTimer;

    /** Passo corrente dell'animazione di shaking. */
    private int shakeStep = 0;

    /** Durata dell'animazione di shaking. */
    private final int SHAKE_DURATION = 10; // numero di oscillazioni

    /** Ampiezza dell'animazione di shaking. */
    private final int SHAKE_AMPLITUDE = 5; // pixel di spostamento

    /**
     * Crea una vista grafica per una singola carta.
     * @param carta l'oggetto Carta da visualizzare
     * @param coperta true se la carta deve essere mostrata sul retro
     * @param ruotata true se la carta deve essere ruotata di 90 gradi
     * @param ridotta true per usare dimensioni ridotte
     */
    public CartaView(Carta carta, boolean coperta, boolean ruotata, boolean ridotta) {
        this.carta = carta;
        this.coperta = coperta;
        this.ruotata = ruotata;

        int larghezza;
        int altezza;
        String path = "";

        if(coperta) {
            path = carta.getPercorsoImmagineRetro();
        }
        else
        {
            path = carta.getPercorsoImmagine();
        }
        if (ridotta) {
            larghezza = CARTA_LARGHEZZA_PC;
            altezza = CARTA_ALTEZZA_PC;
        } else {
            larghezza = CARTA_LARGHEZZA_UTENTE;
            altezza = CARTA_ALTEZZA_UTENTE;
        }

        setPreferredSize(new Dimension(larghezza, altezza));
        setOpaque(false);

        if (ruotata) {
            setPreferredSize(new Dimension(altezza, larghezza));
        } else {
            setPreferredSize(new Dimension(larghezza, altezza));
        }

        try {
            ImageIcon immagineCorrente = new ImageIcon(getClass().getResource(path));
            Image immagineCorrenteRidotta = immagineCorrente.getImage()
                    .getScaledInstance(larghezza, altezza, Image.SCALE_SMOOTH);
            immagine = immagineCorrenteRidotta;
        } catch (Exception e) {
            System.out.println("Errore nella lettura del file: " + path + "; Errore: " + e.getMessage());
        }
    }

    /**
     * Imposta lo stato di hover della carta (bordo evidenziato).
     * @param value true se il mouse è sopra la carta
     */
    public void setMouseOver(boolean value) {
    	mouseOver = value;
    }

    /**
     * Disegna la carta con angoli stondati e bordo evidenziato se in hover.
     * @param g contesto grafico
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (immagine != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Shape clip = new RoundRectangle2D.Double(
                    0, 0, getWidth(), getHeight(), 20, 20);

            // Clip per angoli stondati
            g2d.setClip(clip);

            // Disegna immagine
            g2d.drawImage(immagine, shakeOffsetX, 0, getWidth(), getHeight(), this);

            // Disegna bordo stondato solo se mouseOver
            if (mouseOver) {
                g2d.setClip(null); // reset clip per disegnare sopra
                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(2));
                g2d.draw(new RoundRectangle2D.Double(
                        0, 0, getWidth() - 1, getHeight() - 1, 20, 20));
            }

            g2d.dispose();
        }
    }

    /**
     * Avvia una breve animazione di "shaking" della carta.
     */
    public void startShakeAnimation() {
        if (shakeTimer != null && shakeTimer.isRunning()) return;

        shakeStep = 0;
        shakeTimer = new Timer(30, e -> {
            shakeOffsetX = (shakeStep % 2 == 0) ? SHAKE_AMPLITUDE : -SHAKE_AMPLITUDE;
            repaint();
            shakeStep++;
            if (shakeStep >= SHAKE_DURATION) {
                shakeTimer.stop();
                shakeOffsetX = 0;
                repaint();
            }
        });
        shakeTimer.start();
    }

    /**
     * Restituisce l'oggetto Carta associato a questa vista.
     * @return la Carta visualizzata
     */
    public Carta getCarta() {
        return carta;
    }
}
