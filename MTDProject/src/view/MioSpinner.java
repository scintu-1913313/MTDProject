package view;

import java.awt.*;
import javax.swing.*;

/**
 * Classe astratta che estende JPanel per creare uno spinner personalizzato
 * con frecce orizzontali per incremento e decremento del valore.
 */
public abstract class MioSpinner extends JPanel {

    /** Bottone sinistro per decrementare il valore. */
	public final MioBottone leftButton;

    /** Bottone destro per incrementare il valore. */
	public final MioBottone rightButton;

    /** Etichetta descrittiva dello spinner. */
	public final MioLabel intestazione;

    /**
     * Crea uno spinner personalizzato con frecce orizzontali.
     * @param titolo etichetta descrittiva
     */
    public MioSpinner(String titolo){
        setLayout(new BorderLayout(5, 0));
        setBackground(new Color(0, 0, 0, 0));

        ImageIcon iconaOriginaleSx = new ImageIcon(getClass().getResource("/img/frecciaSx.png"));
        Image iconaRidottaSx = iconaOriginaleSx.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        leftButton = new MioBottone(new ImageIcon(iconaRidottaSx));
        leftButton.setPreferredSize(new Dimension(40, 30));
        leftButton.setMinimumSize(new Dimension(40, 30));
        leftButton.setMaximumSize(new Dimension(40, 30));

        ImageIcon iconaOriginaleDx = new ImageIcon(getClass().getResource("/img/frecciaDx.png"));
        Image iconaRidottaDx = iconaOriginaleDx.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        rightButton = new MioBottone(new ImageIcon(iconaRidottaDx));
        rightButton.setPreferredSize(new Dimension(40, 30));
        rightButton.setMinimumSize(new Dimension(40, 30));
        rightButton.setMaximumSize(new Dimension(40, 30));

        add(leftButton, BorderLayout.WEST);
        add(rightButton, BorderLayout.EAST);
        
        intestazione = new MioLabel(titolo);
        add(intestazione, BorderLayout.NORTH);
    }
}

