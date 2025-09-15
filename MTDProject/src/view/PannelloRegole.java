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
	private BufferedImage sfondo;
	
	private static final long serialVersionUID = 1L;

	
	public PannelloRegole() {
		super(new BorderLayout());
		setBounds(50, 50, 600, 400);
		setBackground(Color.WHITE);
		
		// Carica lo sfondo
		try {
			sfondo = ImageIO.read(getClass().getResource("/img/sfondo.png"));
		} catch (IOException | IllegalArgumentException e) {
			sfondo = null;
		}

		add(Box.createRigidArea(new Dimension(100, 0)), BorderLayout.WEST);
		add(Box.createRigidArea(new Dimension(100, 0)), BorderLayout.EAST);

		// Bottone in alto a sinistra
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		topPanel.setBackground(TRASPARENTE);
		MioBottone bottoneRitornoMenu = new MioBottone("Menu'");

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
        areaTesto.setOpaque(false);
        areaTesto.setBackground(new Color(0, 0, 0, 0));
        areaTesto.setForeground(Color.WHITE);
        areaTesto.setFont(new Font("SansSerif", Font.PLAIN, 14));

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
        setVisible(true);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (sfondo != null) {
			g.drawImage(sfondo, 0, 0, getWidth(), getHeight(), this);
		}
	}
	}