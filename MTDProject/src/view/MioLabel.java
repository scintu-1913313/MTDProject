package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MioLabel extends JLabel {

	/**
	 * Crea una label con stile dell'app (centrata e formattata).
	 * @param titolo il testo da mostrare
	 */
	public MioLabel(String titolo){
		super(titolo, SwingConstants.CENTER);
		setFont(new Font("SansSerif", Font.BOLD, 14));
        setForeground(Color.BLACK);
        setOpaque(true);
        setBackground(View.VERDE_PANNELLO);
	}
}
