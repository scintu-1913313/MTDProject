package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import model.EsitoPartita;

public class PannelloEsitoPartita {

	public static void mostraEsito(Component parent, String nomeGiocatore, int mioPunteggio, EsitoPartita esito) {
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(parent), "Fine Partita", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout());

        // Determina messaggio
        String messaggio;
        if (esito.equals(EsitoPartita.VINTA)) {
            messaggio = "Hai vinto!";
        } else if (esito.equals(EsitoPartita.PAREGGIATA)) {
            messaggio = "Hai pareggiato!";
        } else {
            messaggio = "Hai perso!";
        }

        // Pannello centrale
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel esitoLabel = new JLabel(messaggio, SwingConstants.CENTER);
        esitoLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        JLabel punteggioLabel = new JLabel("Punteggio: " + mioPunteggio, SwingConstants.CENTER);
        punteggioLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        JLabel infoLabel = new JLabel("Grazie per aver giocato!", SwingConstants.CENTER);
        infoLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));

        panel.add(esitoLabel);
        panel.add(punteggioLabel);
        panel.add(infoLabel);

        dialog.add(panel, BorderLayout.CENTER);

        // Pulsante chiusura
        JButton chiudi = new JButton("Torna al menu'");
        chiudi.addActionListener(e -> dialog.dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(chiudi);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true); // blocca finché non chiudi
    }
}
