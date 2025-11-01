package view;

import java.awt.*;
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

    /** Larghezza della carta in modalità ridotta. */
    private static int CARTA_LARGHEZZA_RIDOTTA = 50;

    /** Altezza della carta in modalità ridotta. */
    private static int CARTA_ALTEZZA_RIDOTTA =90;

    /** Larghezza  della carta in modalità normale (utente). */
    private static int CARTA_LARGHEZZA_UTENTE = 60;

    /** Altezza della carta in modalità normale (utente). */
    private static int CARTA_ALTEZZA_UTENTE = 90;

    /** Stato di hover del mouse sulla carta. */
    private boolean mouseOver = false;

    /** Indica se la carta e' mostrata coperta (retro). */
    private boolean coperta;

    /** Indica se la carta e' ruotata di 90 gradi. */
    private boolean ruotata;

    /** Offset orizzontale per l'animazione di shaking. */
    private int shakeOffsetX = 0;

    /** Dimensione preferita fissata per questa vista. */
    private Dimension dimensionePreferita;

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
            larghezza = CARTA_LARGHEZZA_RIDOTTA;
            altezza = CARTA_ALTEZZA_RIDOTTA;
        } else {
            larghezza = CARTA_LARGHEZZA_UTENTE;
            altezza = CARTA_ALTEZZA_UTENTE;
        }

        // Impostiamo una dimensione fissa per evitare che la layout manager la ridimensioni
        Dimension dim;
        if (ruotata) {
            dim = new Dimension(altezza, larghezza);
        } else {
            dim = new Dimension(larghezza, altezza);
        }
        this.dimensionePreferita = dim;
        setPreferredSize(dim);
        setMinimumSize(dim);
        setMaximumSize(dim);
        setOpaque(false);

        // Con BoxLayout è utile centrare l'allineamento per evitare stretch orizzontali
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setAlignmentY(Component.CENTER_ALIGNMENT);

        try {
            ImageIcon immagineCorrente = new ImageIcon(getClass().getResource(path));
            Image src = immagineCorrente.getImage();

            // Creiamo un BufferedImage di destinazione con qualità elevata
            int targetW = dim.width;
            int targetH = dim.height;
            java.awt.image.BufferedImage dest = new java.awt.image.BufferedImage(targetW, targetH, java.awt.image.BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = dest.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (ruotata) {
                // Ruota il contesto di 90 gradi e disegna l'immagine scalata
                g2.translate(targetW / 2.0, targetH / 2.0);
                g2.rotate(Math.toRadians(90));
                g2.translate(-targetH / 2.0, -targetW / 2.0);
                g2.drawImage(src, 0, 0, targetH, targetW, null);
            } else {
                g2.drawImage(src, 0, 0, targetW, targetH, null);
            }

            g2.dispose();
            immagine = dest;
        } catch (Exception e) {
            System.out.println("Errore nella lettura del file: " + path + "; Errore: " + e.getMessage());
        }
    }

    /**
     * Ritorna la dimensione preferita impostata nella carta, che corrisponde alla dimensione fissata per costruire la carta.
     * @return la dimensione preferia.
     */
    @Override
    public Dimension getPreferredSize() {
        return dimensionePreferita != null ? new Dimension(dimensionePreferita) : super.getPreferredSize();
    }

    /**
     * Ritorna la dimensione minima impostata nella carta, che corrisponde alla dimensione fissata per costruire la carta.
     * @return la dimensione minima.
     */
    @Override
    public Dimension getMinimumSize() {
        return dimensionePreferita != null ? new Dimension(dimensionePreferita) : super.getMinimumSize();
    }

    /**
     * Ritorna la dimensione massima impostata nella carta, che corrisponde alla dimensione fissata per costruire la carta.
     * @return la dimensione massima.
     */
    @Override
    public Dimension getMaximumSize() {
        return dimensionePreferita != null ? new Dimension(dimensionePreferita) : super.getMaximumSize();
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

            //di default il bordo e' diegnato grigio
            g2d.setClip(null); // reset clip per disegnare sopra
            g2d.setColor(Color.GRAY);
            g2d.setStroke(new BasicStroke(2));
            g2d.draw(new RoundRectangle2D.Double(
                    0, 0, getWidth() - 1, getHeight() - 1, 20, 20));
            
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
