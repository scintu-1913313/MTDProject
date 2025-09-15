package view;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.*;
import java.util.Observable;

public class PannelloRegole extends Pannello{
	
	private static final long serialVersionUID = 1L;

	
	public PannelloRegole() {
		
		 super(new BorderLayout());
	        setBounds(50, 50, 600, 400);
	        setBackground(Color.WHITE);

	        add(Box.createRigidArea(new Dimension(50, 0)), BorderLayout.WEST);
	        add(Box.createRigidArea(new Dimension(50, 0)), BorderLayout.EAST);
	        
	        // Bottone in alto a sinistra
	        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	        topPanel.setBackground(TRASPARENTE);
	        MioBottone bottoneRitornoMenu = new MioBottone("Menu'");

	        topPanel.add(bottoneRitornoMenu);
	        add(topPanel, BorderLayout.NORTH);
	        
	        Path path = Paths.get("MTDProject/doc/Tressette.txt");
	        String contenuto = "Regole non disponibili";
	        try {
	            contenuto = Files.readString(path);
	        } catch (IOException e) {
	            System.out.println("Errore nella lettura del file: " + e.getMessage());
	        }
	        
	        // Area di testo scorrevole al centro
	        JTextArea areaTesto = new JTextArea();
	        areaTesto.setText(contenuto);
	        areaTesto.setEditable(false);
	        areaTesto.setLineWrap(true);
	        areaTesto.setWrapStyleWord(true);

	        JScrollPane scrollPane = new JScrollPane(areaTesto);
	        add(scrollPane, BorderLayout.CENTER);

	        setVisible(true);
	}
}