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

public class MioImgSpinner extends MioSpinner {

    private JLabel labelImmagineCorrente;
	private ArrayList<Carta> carte;
	private ArrayList<ImageIcon> immaginiCarte;
    private int indiceCorrente;

	public MioImgSpinner(String titolo, ArrayList<Carta> carte){
		super(titolo);
		this.carte = carte;
		indiceCorrente = 0;
		immaginiCarte = new ArrayList<>();
		
		costruisciImmaginiCarte();
        labelImmagineCorrente = new JLabel("", immaginiCarte.get(indiceCorrente), JLabel.CENTER);
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
	        labelImmagineCorrente.setIcon(immaginiCarte.get(indiceCorrente));
		}
		if(indiceCorrente == 0)
		{
			leftButton.setEnabled(false);
			leftButton.setBackground(MioBottone.VERDE_TAVOLO);
		}
	}

	private void incrementaValore() {
		leftButton.setEnabled(true);

		if(indiceCorrente <= carte.size() - 1)
		{
			indiceCorrente+=1;
	        labelImmagineCorrente.setIcon(immaginiCarte.get(indiceCorrente));
		}
		if(indiceCorrente == carte.size() - 1)
		{
			rightButton.setEnabled(false);
			rightButton.setBackground(MioBottone.VERDE_TAVOLO);
		}
	}
	
	private void costruisciImmaginiCarte() {
		
		for(Carta c: carte) {
			String path = c.getPercorsoImmagine();
			try {

				ImageIcon immagineCorrente = new ImageIcon(getClass().getResource(path));
		        Image immagineCorrenteRidotta = immagineCorrente.getImage().getScaledInstance(90, 130, Image.SCALE_SMOOTH);
		        immaginiCarte.addLast(new ImageIcon(immagineCorrenteRidotta));
			} 
			catch 
			(Exception e) {
				System.out.println("Errore nella lettura del file: "+ path+"; Errore: " +e.getMessage());
			}
		}
		if(immaginiCarte.size() != carte.size()) {
			System.out.println("Errore nella costruzione delle immagini");
		}
	}
	
	public Carta getCartaCorrente() {
		return carte.get(indiceCorrente);
	}
}
