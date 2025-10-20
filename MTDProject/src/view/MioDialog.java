package view;

import javax.swing.*;
import java.awt.*;

/**
 * Classe astratta che estende JDialog per creare un dialogo modale(bloccante)
 * con stile coerente e bottone di chiusura.
 */
public abstract class MioDialog extends JDialog {

    /** Pannello dei bottoni del dialog. */
	protected JPanel pannelloBottoni;

    /**
     * Costruisce un dialogo modale(bloccante) con stile coerente e bottone di chiusura.
     * @param owner finestra proprietaria
     * @param titolo titolo del dialog
     * @param larghezza larghezza del dialog
     * @param altezza altezza del dialog
     */
    public MioDialog(Frame owner, String titolo,int larghezza,int altezza) {
        super(owner, titolo, true); // true = modalità bloccante
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(larghezza, altezza);
        setLocationRelativeTo(owner);
        setResizable(false);

        // Imposta lo stile coerente con PannelloMenu
        getContentPane().setBackground(View.VERDE_PANNELLO);
        getContentPane().setLayout(new BorderLayout());

        // Bottone di chiusura
        MioBottone bottoneOK = new MioBottone("OK");
        bottoneOK.setPreferredSize(new Dimension(100, 40));
        bottoneOK.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneOK.addActionListener(e -> dispose());

        pannelloBottoni = new JPanel();
        pannelloBottoni.setBackground(View.VERDE_PANNELLO);
        pannelloBottoni.setLayout(new FlowLayout(FlowLayout.CENTER));
        pannelloBottoni.add(bottoneOK);
        getContentPane().add(pannelloBottoni, BorderLayout.SOUTH);
    }
}
