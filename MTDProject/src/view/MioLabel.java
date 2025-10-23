package view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Classe che estende JLabel per creare una label con stile personalizzato dell'applicazione.
 */
public class MioLabel extends JLabel {

	/**
	 * Crea una label con stile dell'app (centrata e formattata).
	 * @param titolo il testo da mostrare
	 */
	public MioLabel(String titolo){
		super(titolo, SwingConstants.CENTER);
		setFont(View.FONT_GIOCO);
        setForeground(Color.BLACK);
        setOpaque(true);
        setBackground(View.VERDE_PANNELLO);
	}
}
