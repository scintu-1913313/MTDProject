package view;

import java.awt.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Contenitore principale che raggruppa i pannelli navigabili dell'interfaccia
 * (MENU, REGOLE, ACCOUNT) e gestisce il layout a schede.
 */
public class PannelloPrincipale extends Pannello {

    /**
     * Layout a schede per i pannelli navigabili.
     */
	private CardLayout layoutPannelloNascosto;

    /** Pannello che contiene i pannelli navigabili usato per intercambiare i pannelli di MENU/REGOLE/ACCOUNT. */
	private JPanel pannelloNascosto;

    /** Pannello Menu. */
	private PannelloMenu pannelloMenu;

    /** Pannello Regole. */
    private PannelloRegole pannelloRegole;

    /** Pannello Account. */
    private PannelloAccount pannelloAccount;

    /**
     * Costruisce il contenitore principale che raggruppa i pannelli navigabili(MENU/REGOLE/ACCOUNT).
     * @param view riferimento alla finestra principale
     */
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
		pannelloAccount = new PannelloAccount(view);

        pannelloNascosto.add(pannelloRegole,"REGOLE");
        pannelloNascosto.add(pannelloMenu,"MENU");
        pannelloNascosto.add(pannelloAccount,"ACCOUNT");

        
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(pannelloNascosto, gbc);
	}

    /**
     * Disegna lo sfondo del pannello principale(immagine sfondo).
     * @param g contesto grafico
     */
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (sfondo != null) {
            g.drawImage(sfondo, 0, 0, getWidth(), getHeight(), this);
        }
    }

    /**
     * Mostra uno dei pannelli contenuti (MENU/REGOLE/ACCOUNT).
     * @param name nome del pannello da visualizzare
     */
	public void showPanel(String name) {
        layoutPannelloNascosto.show(pannelloNascosto, name);
    }

    /**
     * Restituisce il pannello Menu.
     * @return pannelloMenu
     */
    public PannelloMenu getPannelloMenu() {
        return pannelloMenu;
    }
    
    /**
     * Restituisce il pannello Account.
     * @return pannelloAccount
     */
    public PannelloAccount getPannelloAccount() {
        return pannelloAccount;
    }
}
