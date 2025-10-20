package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import model.GestoreAudio;

public class MioBottoneSelezione extends JButton{
	
	private boolean cliccato = false;
	private ImageIcon immagineOff;
	private ImageIcon immagineOn;
	
	/**
	 * Costruisce un bottone di selezione con due icone (off/on) e suono al click.
	 * @param pathImmagineOff percorso immagine stato off
	 * @param pathImmagineOn percorso immagine stato on
	 */
	public MioBottoneSelezione(String pathImmagineOff, String pathImmagineOn) {
        super();
	
        immagineOff = new ImageIcon(getClass().getResource(pathImmagineOff));
        Image iconaVuotaRidotta = immagineOff.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        immagineOff = new ImageIcon(iconaVuotaRidotta);
        
        immagineOn = new ImageIcon(getClass().getResource(pathImmagineOn));
        Image iconaSelezioneRidotta = immagineOn.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        immagineOn = new ImageIcon(iconaSelezioneRidotta);
        
        setIcon(new ImageIcon(iconaVuotaRidotta));
        
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        
        addActionListener(e -> cambioImmagine());
        addActionListener(e -> {
        	GestoreAudio audioManager = GestoreAudio.getInstance();
        	audioManager.playBottone();
        });
	}
	
	/**
	 * Cambia l'immagine del bottone in base allo stato di selezione.
	 */
	private void cambioImmagine()
	{
		cliccato = !cliccato;
		if (cliccato)
		{
			setIcon(immagineOn);
			
		}
		else
		{
			setIcon(immagineOff);			
		}
	}
	
	/**
	 * Imposta lo stato di selezione del bottone e aggiorna l'immagine visualizzata.
	 * @param cliccato true se selezionato, false altrimenti
	 */
	public void setCliccato(boolean cliccato)
	{
		this.cliccato = cliccato;
		if (cliccato)
		{
			setIcon(immagineOn);
		}
		else
		{
			setIcon(immagineOff);			
		}
	}
	
	public boolean getCliccato()
	{
		return cliccato;
	}
}
