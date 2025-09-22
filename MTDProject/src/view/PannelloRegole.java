package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.*;

public class PannelloRegole extends Pannello{
	
	public PannelloRegole(View view) {
		super(new BorderLayout());
		//setBounds(50, 50, 600, 400);
		setBackground(Pannello.VERDE_HOVER);
		setOpaque(false);

		// Bottone in basso a destra
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		topPanel.setBackground(TRASPARENTE);
		
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
        areaTesto.setBackground(Pannello.VERDE_HOVER); // VERDE chiaro
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