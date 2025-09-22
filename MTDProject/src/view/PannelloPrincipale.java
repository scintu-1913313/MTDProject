package view;

import java.awt.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PannelloPrincipale extends Pannello {

	private CardLayout layoutPannelloNascosto;
	private JPanel pannelloNascosto;
	private PannelloMenu pannelloMenu;
    private PannelloRegole pannelloRegole;

	public PannelloPrincipale(View view) {
		super(new GridBagLayout());
		// Carica lo sfondo
        try {
            sfondo = ImageIO.read(getClass().getResource("/img/sfondo.png"));
        } catch (IOException | IllegalArgumentException e) {
            sfondo = null;
        }
		setPreferredSize(new Dimension(Pannello.LARGHEZZA, Pannello.ALTEZZA));

		layoutPannelloNascosto = new CardLayout();
        pannelloNascosto = new JPanel(layoutPannelloNascosto);
        pannelloNascosto.setOpaque(false); // trasparente per vedere l'immagine
        pannelloNascosto.setPreferredSize(new Dimension(700, 500));
		
		pannelloMenu = new PannelloMenu(view);
		pannelloRegole = new PannelloRegole(view);

        pannelloNascosto.add(pannelloRegole,"REGOLE");
        pannelloNascosto.add(pannelloMenu,"MENU");
		
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(pannelloNascosto, gbc);
	}

	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (sfondo != null) {
            g.drawImage(sfondo, 0, 0, getWidth(), getHeight(), this);
        }
    }

	public void showPanel(String name) {
        layoutPannelloNascosto.show(pannelloNascosto, name);
    }

    public PannelloMenu getPannelloMenu() {
        return pannelloMenu;
    }
}
