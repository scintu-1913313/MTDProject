package view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MioBottoneSelezione extends JButton{
	
	private boolean cliccato = false;
	private ImageIcon immagineVuota;
	private ImageIcon immagineSelezione;
	
	public MioBottoneSelezione() {
        super();
	
        immagineVuota = new ImageIcon(getClass().getResource("/img/checkboxVuota.png"));
        Image iconaVuotaRidotta = immagineVuota.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        immagineVuota = new ImageIcon(iconaVuotaRidotta);
        
        immagineSelezione = new ImageIcon(getClass().getResource("/img/checkboxCliccata.png"));
        Image iconaSelezioneRidotta = immagineSelezione.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        immagineSelezione = new ImageIcon(iconaSelezioneRidotta);
        
        setIcon(new ImageIcon(iconaVuotaRidotta));
        
        addActionListener(e -> cambioImmagine());
        
        addActionListener(e -> RiproduttoreAudio.play("/sounds/button.wav"));
	}
	
	private void cambioImmagine()
	{
		if (cliccato)
		{
			cliccato = false;
			setIcon(immagineVuota);
			
		}
		else
		{
			cliccato = true;
			setIcon(immagineSelezione);
			
		}
	
	}
	public boolean getCliccato()
	{
		return cliccato;
	}
}
