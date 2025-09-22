package view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PannelloGioco extends Pannello {

	public PannelloGioco(View view) {
		super(new BorderLayout());
		// Carica lo sfondo
        try {
            sfondo = ImageIO.read(getClass().getResource("/img/sfondoGioco.png"));
        } catch (IOException | IllegalArgumentException e) {
            sfondo = null;
        }
		// TODO Auto-generated constructor stub
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (sfondo != null) {
            g.drawImage(sfondo, 0, 0, getWidth(), getHeight(), this);
        }
    }

}
