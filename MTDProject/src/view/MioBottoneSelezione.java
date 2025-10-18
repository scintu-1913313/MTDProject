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
