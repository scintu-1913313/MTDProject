package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import carte.Carta;
import model.Accusa;
import model.Pair;

public class DialogoAccuse extends MioDialog {

	private JPanel pannelloCarteCentrale;
	private JPanel pannelloCentrale;

    /**
     * Costruisce un dialog che mostra le accuse e le carte coinvolte.
     * @param owner finestra proprietaria
     * @param titolo titolo del dialog
     * @param giocatore nome del giocatore che effettua l'accusa
     * @param accuse coppia contenente tipo di accusa e lista delle carte associate all'accusa
     */
    public DialogoAccuse(Frame owner, String titolo, String giocatore, Pair<Accusa, List<Carta>> accuse) {
        super(owner, titolo,380,250);
        
        String tipoDiAccusa = accuse.getFirst().toString();
        // Etichetta del messaggio
        MioLabel labelGicoatore = new MioLabel(giocatore + " dichiara: "+tipoDiAccusa +"; +" + accuse.getFirst().getValore()+" punti");
        labelGicoatore.setHorizontalAlignment(SwingConstants.CENTER);
        labelGicoatore.setVerticalAlignment(SwingConstants.CENTER);
        labelGicoatore.setFont(View.FONT_GIOCO);
        labelGicoatore.setForeground(Color.BLACK);
        getContentPane().add(labelGicoatore, BorderLayout.NORTH);
        
        pannelloCentrale = new JPanel();
        pannelloCentrale.setOpaque(false);
        pannelloCentrale.setLayout(new BoxLayout(pannelloCentrale, BoxLayout.Y_AXIS));

        pannelloCarteCentrale = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pannelloCarteCentrale.setOpaque(false);

        List<Carta> carte = accuse.getSecond();
        int numCarte = carte.size();
    	for (int i=0; i<numCarte; i++) {
    		CartaView cartaView = new CartaView(carte.get(i),false,false,false);
    		pannelloCarteCentrale.add(cartaView,cartaView.toString());
    	}
    	
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        pannelloCentrale.add(pannelloCarteCentrale, gbc);
        getContentPane().add(pannelloCentrale, BorderLayout.CENTER);
	}
}
