package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import carte.*;
import model.Avatar;

/**
 * Classe che estende MioSpinner per creare uno spinner di immagini 
 * Si basa su una lista di oggetti (Carte o Avatar).
 */
public class MioImgSpinner extends MioSpinner {

	/** Etichetta che mostra l'immagine corrente dello spinner. */
    private JLabel labelImmagineCorrente;

	/** Lista degli oggetti (Carte o Avatar) da mostrare nello spinner. */
	private ArrayList<Object> oggetti;

	/** Lista delle immagini degli oggetti per lo spinner. */
	private ArrayList<ImageIcon> immaginiOggetti;

	/** Indice dell'oggetto corrente nello spinner. */
    private int indiceCorrente;

	/** Tipo di oggetto gestito dallo spinner (Carta o Avatar). */
    public final Class type;

	/** Immagine ridotta corrente per la visualizzazione. */
    private Image immagineCorrenteRidotta;

	/** Immagine corrente per la visualizzazione. */
    private ImageIcon immagineCorrente;
    
	/**
	 * Crea uno spinner di immagini basato su una lista di oggetti (Carte o Avatar).
	 * @param titolo etichetta descrittiva dello spinner
	 * @param oggetti lista di oggetti da mostrare con le rispettive immagini
	 */
	public MioImgSpinner(String titolo, ArrayList<Object> oggetti){
		super(titolo);
		this.oggetti = oggetti;
		this.type = oggetti.get(0).getClass();
		
		indiceCorrente = 0;
		immaginiOggetti = new ArrayList<>();
		
		costruisciImmaginiCarte();
        labelImmagineCorrente = new JLabel("", immaginiOggetti.get(indiceCorrente), JLabel.CENTER);
        labelImmagineCorrente.setOpaque(false);
        setOpaque(false);
        
        add(labelImmagineCorrente, BorderLayout.CENTER);
        
		leftButton.addActionListener((ActionEvent e) -> decrementaValore());
        rightButton.addActionListener((ActionEvent e) -> incrementaValore());
        
        leftButton.setEnabled(false);
        setPreferredSize(new Dimension(180, 150));
        setMaximumSize(new Dimension(180,150));
	}
	
	/**
	 * Decrementa l'indice corrente(se possibile) e aggiorna l'immagine visualizzata.
	 */
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
			leftButton.setBackground(View.VERDE_TAVOLO);
		}
	}

	/**
	 * Incrementa l'indice corrente(se possibile) e aggiorna l'immagine visualizzata.
	 */
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
			rightButton.setBackground(View.VERDE_TAVOLO);
		}
	}
	
	/**
	 * Naviga direttamente ad un indice specifico nello spinner, se valido, e aggiorna l'immagine visualizzata.
	 * @param indice indice dell'oggetto da selezionare
	 */
	private void vaiAdIndice(int indice) {
		
		if(indice <= oggetti.size() - 1 && indice >= 0)
		{
			indiceCorrente=indice;
	        labelImmagineCorrente.setIcon(immaginiOggetti.get(indiceCorrente));
		}
		if(indiceCorrente == oggetti.size() - 1)
		{
			rightButton.setEnabled(false);
			rightButton.setBackground(View.VERDE_TAVOLO);
		}
		else
		{
			rightButton.setEnabled(true);
		}
		
		if(indiceCorrente == 0)
		{
			leftButton.setEnabled(false);
			leftButton.setBackground(View.VERDE_TAVOLO);
		}
		else
		{
			leftButton.setEnabled(true);
		}
	}
	
	/**
	 * Costruisce le immagini ridotte degli oggetti (Carte o Avatar) per lo spinner.
	 */
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
				larghezza = 80;
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

				immagineCorrente = new ImageIcon(getClass().getResource(path));
		        immagineCorrenteRidotta = immagineCorrente.getImage().getScaledInstance(larghezza, altezza, Image.SCALE_SMOOTH);
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
	
	/**
	 * Restituisce l'oggetto attualmente selezionato nello spinner.
	 * @return l'oggetto corrente(Carta o Avatar)
	 */
	public Object getOggettoCorrente() {
		return oggetti.get(indiceCorrente);
	}
	
	/**
	 * Visualizza uno specifico oggetto nello spinner, se presente.
	 * @param obj oggetto da selezionare(Carta o Avatar)
	 */
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
