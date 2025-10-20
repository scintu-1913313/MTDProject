package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import model.GestoreAudio;

/**
 * Classe che estende MioBottone per creare un bottone con icona di avatar e suono al click.
 * Usatato nel pannello menu' per mostrare informazioni utente o profilo.
 */
public class MioBottoneInfo extends MioBottone{
	
        /** Icona dell'avatar mostrata nel bottone. */
	private ImageIcon immagineProfilo;
	
        /**
         * Costruisce un bottone con icona di avatr e suono al click.
         */
	public MioBottoneInfo() {
                super("");

                immagineProfilo = new ImageIcon(getClass().getResource("/img/avatar.png"));
                Image iconaRidotta = immagineProfilo.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                immagineProfilo = new ImageIcon(iconaRidotta);

                setIcon(new ImageIcon(iconaRidotta));

                setOpaque(false);
                setContentAreaFilled(false);
                //setBorderPainted(false);
                setFocusPainted(false);

                addActionListener(e -> {
                        GestoreAudio audioManager = GestoreAudio.getInstance();
                        audioManager.playBottone();
                });
                
	}
}
