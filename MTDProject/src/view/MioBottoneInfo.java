package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import model.GestoreAudio;

public class MioBottoneInfo extends MioBottone{
	
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
