package view;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.*;

/** 
 * Pannello che mostra le regole del gioco e il bottone di ritorno al menu.
 */
public class PannelloRegole extends Pannello{
	
	/**
	 * Costruisce il pannello che mostra le regole del gioco e il bottone di ritorno al menu.
	 * @param view riferimento alla View principale usato per navigazione
	 */
	public PannelloRegole(View view) {
		super(new BorderLayout());
		//setBounds(50, 50, 600, 400);
		setBackground(View.VERDE_PANNELLO);
		setOpaque(false);

		// Bottone in basso a destra
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		topPanel.setBackground(View.TRASPARENTE);
		
		MioBottone bottoneRitornoMenu = new MioBottone("Menu'");
		bottoneRitornoMenu.setPreferredSize(new Dimension(140, 40));
		bottoneRitornoMenu.setMaximumSize(new Dimension(140, 40));
		bottoneRitornoMenu.addActionListener(e -> view.showPannelloMenu());

		topPanel.add(bottoneRitornoMenu);
		add(topPanel, BorderLayout.SOUTH);

		Path path = Paths.get("MTDProject/doc/Tressette.txt");
		String contenuto = "Regole non disponibili";
		try {
			contenuto = Files.readString(path);
		} catch (IOException e) {
			System.out.println("Errore nella lettura del file: " + e.getMessage());
		}
	        
		JTextArea areaTesto = new JTextArea(contenuto);
        areaTesto.setEditable(false);
        areaTesto.setLineWrap(true);
        areaTesto.setWrapStyleWord(true);
        areaTesto.setOpaque(true);
        areaTesto.setBackground(View.VERDE_PANNELLO); // VERDE chiaro
        areaTesto.setForeground(Color.BLACK); // testo nero per leggibilità
        
        //areaTesto.setForeground(Color.WHITE);
        areaTesto.setFont(new Font("SansSerif", Font.BOLD, 14));

        // ScrollPane con solo scrollbar verticale visibile
        JScrollPane scrollPane = new JScrollPane(areaTesto);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBackground(new Color(0, 0, 0, 0));
        scrollPane.getViewport().setBackground(new Color(0, 0, 0, 0));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane, BorderLayout.CENTER);
	}
}