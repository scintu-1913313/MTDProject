package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;
import javax.swing.Timer;
import carte.*;

public class CartaView extends JPanel {

    private Carta carta;
    private Image immagine;
    private static int CARTA_LARGHEZZA_PC = 50;
    private static int CARTA_ALTEZZA_PC = 90;
    private static int CARTA_LARGHEZZA_UTENTE = 70;
    private static int CARTA_ALTEZZA_UTENTE = 130;
    private boolean mouseOver = false;
    private boolean coperta;
    private boolean ruotata;

    private int shakeOffsetX = 0;
    private Timer shakeTimer;
    private int shakeStep = 0;
    private final int SHAKE_DURATION = 10; // numero di oscillazioni
    private final int SHAKE_AMPLITUDE = 5; // pixel di spostamento

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

        // se sono il giocatore vero do' la possibilita' di cliccare le carte
        if (!coperta) {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    mouseOver = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    mouseOver = false;
                    repaint();
                }
            });
        }
    }

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

    public Carta getCarta() {
        return carta;
    }
}
