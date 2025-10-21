package view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import model.EsitoPartita;

/**
 * Dialogo che mostra l' esito della partita con animazione.
 */
public class DialogoFinePartita extends MioDialog {

    /**
     * Costruisce il dialogo di fine partita con animazione e esito della partita.
     * @param owner finestra proprietaria
     * @param titolo titolo del dialog
     * @param esito esito della partita
     * @param mioPunteggio punteggio del giocatore
     */
    public DialogoFinePartita(Frame owner, String titolo, EsitoPartita esito, int mioPunteggio) {
        super(owner, titolo, 380, 250);

        // Pannello con OverlayLayout per sovrapporre animazione e contenuto
        JPanel pannelloOverlay = new JPanel();
        pannelloOverlay.setLayout(new OverlayLayout(pannelloOverlay));
        pannelloOverlay.setOpaque(false);

        // Pannello verticale per le label
        JPanel pannelloLabel = new JPanel();
        pannelloLabel.setLayout(new BoxLayout(pannelloLabel, BoxLayout.Y_AXIS));
        pannelloLabel.setOpaque(false);
        pannelloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        pannelloLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        // Messaggio di esito
        String messaggio;
        if (esito.equals(EsitoPartita.VINTA)) {
            messaggio = "Hai vinto!";
        } else if (esito.equals(EsitoPartita.PAREGGIATA)) {
            messaggio = "Hai pareggiato!";
        } else {
            messaggio = "Hai perso!";
        }

        MioLabel labelEsito = new MioLabel(messaggio);
        labelEsito.setFont(View.FONT_GIOCO.deriveFont(32f));
        labelEsito.setForeground(Color.BLACK);
        labelEsito.setOpaque(false);
        labelEsito.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Label del punteggio
        JLabel punteggioLabel = new JLabel("Punteggio: " + mioPunteggio, SwingConstants.CENTER);
        punteggioLabel.setFont(View.FONT_GIOCO.deriveFont(20f));
        punteggioLabel.setForeground(Color.BLACK);
        punteggioLabel.setOpaque(false);
        punteggioLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Spaziatura tra le label
        pannelloLabel.add(Box.createVerticalGlue());
        pannelloLabel.add(labelEsito);
        pannelloLabel.add(Box.createVerticalStrut(10));
        pannelloLabel.add(punteggioLabel);
        pannelloLabel.add(Box.createVerticalGlue());

        // Aggiunta al pannello sovrapposto
        pannelloOverlay.add(pannelloLabel);

        // Animazione se hai vinto
        if (esito.equals(EsitoPartita.VINTA)) {
        	PannelloFuocoArtificio fireworks = new PannelloFuocoArtificio();
            fireworks.setAlignmentX(Component.CENTER_ALIGNMENT);
            fireworks.setAlignmentY(Component.CENTER_ALIGNMENT);
            pannelloOverlay.add(fireworks);
        }

        getContentPane().add(pannelloOverlay);
    }
}

/**
 * Rappresenta una singola particella di un fuoco d'artificio.
 * Ogni particella ha una posizione, una velocità, un colore e una durata.
 */
class Particella  {
	
	/** Coordinata orizzontale della particella. */
	private double x;

	/** Coordinata verticale della particella. */
	private double y;

	/** Velocità orizzontale della particella. */
	private double vx;

	/** Velocità verticale della particella. */
	private double vy;

	/** Colore della particella, assegnato casualmente alla creazione. */
	private final Color color;

	/** 
	 * Durata residua della particella, espressa in frame. 
	 * Quando raggiunge zero, la particella viene considerata "morta".
	 */
	private int life = 100;

    /**
     * Costruisce una particella con posizione iniziale e direzione casuale.
     * @param x coordinata X iniziale
     * @param y coordinata Y iniziale
     */
    public Particella(int x, int y) {
        this.x = x;
        this.y = y;
        double angle = Math.random() * 2 * Math.PI;
        double speed = Math.random() * 4 + 1;
        vx = Math.cos(angle) * speed;
        vy = Math.sin(angle) * speed;
        color = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
    }

    /**
     * Aggiorna la posizione e lo stato della particella.
     */
    public void update() {
        x += vx;
        y += vy;
        vy += 0.05; // gravità
        life--;
    }

    /**
     * Disegna la particella sullo schermo.
     * @param g contesto grafico
     */
    public void draw(Graphics2D g) {
        if (life > 0) {
            float alpha = Math.max(0, life / 100f);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g.setColor(color);
            g.fillOval((int)x, (int)y, 4, 4);
        }
    }

    /**
     * Verifica se la particella è ancora visibile.
     * @return true se la particella è viva, false altrimenti
     */
    public boolean isAlive() {
        return life > 0;
    }
}

/**
 * Rappresenta un singolo fuoco d'artificio composto da più particelle.
 * Viene generato in una posizione casuale all'interno del pannello e si anima nel tempo.
 */
class FuocoArtificio {
	
	/** Lista delle particelle che compongono il fuoco d'artificio. */
    private final List<Particella> particelle = new ArrayList<>();

    /**
     * Costruisce un fuoco d'artificio in una posizione casuale all'interno del pannello.
     * @param panelWidth larghezza del pannello contenitore
     * @param panelHeight altezza del pannello contenitore
     */
    public FuocoArtificio(int panelWidth, int panelHeight) {
        int x = (int) (Math.random() * panelWidth);
        int y = (int) (Math.random() * panelHeight / 2);
        for (int i = 0; i < 50; i++) {
        	particelle.add(new Particella(x, y));
        }
    }

    /**
     * Aggiorna lo stato di tutte le particelle e rimuove quelle non più visibili.
     */
    public void update() {
    	particelle.removeIf(p -> !p.isAlive());
        for (Particella p : particelle) {
            p.update();
        }
    }

    /**
     * Disegna tutte le particelle del fuoco d'artificio sul contesto grafico fornito.
     * @param g contesto grafico su cui disegnare
     */
    public void draw(Graphics2D g) {
        for (Particella p : particelle) {
            p.draw(g);
        }
    }

    /**
     * Verifica se il fuoco d'artificio è terminato (tutte le particelle sono scomparse).
     * @return true se non ci sono più particelle attive, false altrimenti
     */
    public boolean isFinished() {
        return particelle.isEmpty();
    }
}

/**
 * Pannello Swing che gestisce e visualizza l'animazione dei fuochi d'artificio.
 * Utilizza un Timer per aggiornare e ridisegnare le particelle nel tempo.
 */
class PannelloFuocoArtificio extends JPanel {
	
	/** Lista dei fuochi d'artificio attivi nel pannello. */
    private final List<FuocoArtificio> fuochiArtificio = new ArrayList<>();
    
    /** Timer Swing che gestisce l'aggiornamento dell'animazione. */
    private Timer timer;

    /** Contatore dei frame, utile per eventuali effetti ciclici o temporizzati.*/
    private int frameCount = 0;

    /**
     * Costruttore del pannello.
     */
    public PannelloFuocoArtificio() {
        setOpaque(false);
    }

    /**
     * Metodo chiamato automaticamente quando il pannello viene aggiunto al contenitore.
     * Avvia l'animazione dei fuochi d'artificio.
     */
    @Override
    public void addNotify() {
        super.addNotify();
        startAnimation();
    }

    /**
     * Avvia l'animazione creando i fuochi d'artificio e impostando il timer di aggiornamento.
     * L'animazione viene fermata automaticamente dopo 3 secondi.
     */
    private void startAnimation() {
        generateFireworks();
        timer = new Timer(30, e -> {
            updateFireworks();
            repaint();
        });
        timer.start();

        // Ferma l’animazione dopo 3 secondi
        new Timer(3000, e -> timer.stop()).start();
    }

    /**
     * Genera un insieme di fuochi d'artificio in posizioni casuali all'interno del pannello.
     */
    private void generateFireworks() {
        for (int i = 0; i < 5; i++) {
        	fuochiArtificio.add(new FuocoArtificio(getWidth(), getHeight()));
        }
    }

    /**
     * Aggiorna lo stato di tutti i fuochi d'artificio e rimuove quelli terminati.
     */
    private void updateFireworks() {
    	fuochiArtificio.removeIf(FuocoArtificio::isFinished);
        for (FuocoArtificio f : fuochiArtificio) {
            f.update();
        }
    }

    /**
     * Disegna tutti i fuochi d'artificio attivi sul pannello.
     * @param g contesto grafico su cui disegnare
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        for (FuocoArtificio f : fuochiArtificio) {
            f.draw(g2d);
        }
        g2d.dispose();
    }
}

