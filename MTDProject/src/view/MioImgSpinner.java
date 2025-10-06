package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import carte.*;
import model.Avatar;
import model.AvatarEnum;

public class MioImgSpinner extends MioSpinner {

    private JLabel labelImmagineCorrente;
	private ArrayList<Object> oggetti;
	private ArrayList<ImageIcon> immaginiOggetti;
    private int indiceCorrente;
    public final Class type;
    
	public MioImgSpinner(String titolo, ArrayList<Object> oggetti){
		super(titolo);
		this.oggetti = oggetti;
		this.type = oggetti.get(0).getClass();
		
		indiceCorrente = 0;
		immaginiOggetti = new ArrayList<>();
		
		costruisciImmaginiCarte();
        labelImmagineCorrente = new JLabel("", immaginiOggetti.get(indiceCorrente), JLabel.CENTER);
        add(labelImmagineCorrente, BorderLayout.CENTER);
        
		leftButton.addActionListener((ActionEvent e) -> decrementaValore());
        rightButton.addActionListener((ActionEvent e) -> incrementaValore());
        
        leftButton.setEnabled(false);
        setPreferredSize(new Dimension(180, 150));
        setMaximumSize(new Dimension(180,150));
	}
	
	private void decrementaValore() {
		rightButton.setEnabled(true);
		if(indiceCorrente > 0)
		{
			indiceCorrente-=1;
	        labelImmagineCorrente.setIcon(immaginiOggetti.get(indiceCorrente));
		}
		if(indiceCorrente == 0)
		{
			leftButton.setEnabled(false);
			leftButton.setBackground(MioBottone.VERDE_TAVOLO);
		}
	}

	private void incrementaValore() {
		leftButton.setEnabled(true);

		if(indiceCorrente <= oggetti.size() - 1)
		{
			indiceCorrente+=1;
	        labelImmagineCorrente.setIcon(immaginiOggetti.get(indiceCorrente));
		}
		if(indiceCorrente == oggetti.size() - 1)
		{
			rightButton.setEnabled(false);
			rightButton.setBackground(MioBottone.VERDE_TAVOLO);
		}
	}
	
	private void vaiAdIndice(int indice) {
		
		if(indice <= oggetti.size() - 1 && indice >= 0)
		{
			indiceCorrente=indice;
	        labelImmagineCorrente.setIcon(immaginiOggetti.get(indiceCorrente));
		}
		if(indiceCorrente == oggetti.size() - 1)
		{
			rightButton.setEnabled(false);
			rightButton.setBackground(MioBottone.VERDE_TAVOLO);
		}
		else
		{
			rightButton.setEnabled(true);
		}
		
		if(indiceCorrente == 0)
		{
			leftButton.setEnabled(false);
			leftButton.setBackground(MioBottone.VERDE_TAVOLO);
		}
		else
		{
			leftButton.setEnabled(true);
		}
	}
	
	private void costruisciImmaginiCarte() {
		
		for(Object ogg: oggetti) {
			String path="";
			int altezza = 0;
			int larghezza = 0;
			if(ogg.getClass() == Carta.class)
			{
				Carta c = (Carta) ogg;
				path = c.getPercorsoImmagine();
				altezza = 130;
				larghezza = 90;
			}
			else if(ogg.getClass() == Avatar.class)
			{
				Avatar avatar = (Avatar) ogg;
				path = avatar.getPercorsoImmagine();
				altezza = 90;
				larghezza = 90;
			}
			else
			{
				System.out.println("Classe non riconosciuta");
			}
			try {

				ImageIcon immagineCorrente = new ImageIcon(getClass().getResource(path));
		        Image immagineCorrenteRidotta = immagineCorrente.getImage().getScaledInstance(larghezza, altezza, Image.SCALE_SMOOTH);
		        immaginiOggetti.addLast(new ImageIcon(immagineCorrenteRidotta));
			} 
			catch 
			(Exception e) {
				System.out.println("Errore nella lettura del file: "+ path+"; Errore: " +e.getMessage());
			}
		}
		if(immaginiOggetti.size() != oggetti.size()) {
			System.out.println("Errore nella costruzione delle immagini");
		}
	}
	
	public Object getOggettoCorrente() {
		return oggetti.get(indiceCorrente);
	}
	
	public void vaiAdOggettoSpecifico(Object obj)
	{
		if(obj.getClass() != this.type)
		{
			System.out.println("Classe non riconosciuta");
			return;
		}
		
		int indice = oggetti.indexOf(obj);
		if (indice != -1) {
			vaiAdIndice(indice);
		} else {
		    System.out.println("Oggetto non trovato.");
		}
	}
}
