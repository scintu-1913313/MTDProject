package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Timer;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class DialogoInfoGioco extends MioDialog {

	private PannelloSemplice pannelloCentrale;

	public DialogoInfoGioco(Frame owner, String titolo, String info,int durataMillis) {
        super(owner, titolo,250,80);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0)); // trasparente

        // Pannello con angoli arrotondati
        pannelloCentrale = new PannelloSemplice();
        pannelloCentrale.setLayout(new GridBagLayout());
        pannelloCentrale.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        pannelloCentrale.setPreferredSize(new Dimension(getWidth(), getHeight()));
        // Label del messaggio
        MioLabel labelInfo = new MioLabel(info);
        labelInfo.setHorizontalAlignment(SwingConstants.CENTER);
        labelInfo.setVerticalAlignment(SwingConstants.CENTER);
        labelInfo.setFont(View.FONT_GIOCO);
        labelInfo.setForeground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        pannelloCentrale.add(labelInfo, gbc);

        setContentPane(pannelloCentrale);
        setLocationRelativeTo(owner);

        // Timer per chiusura automatica
        Timer timer = new Timer(durataMillis, e -> dispose());
        timer.setRepeats(false);
        timer.start();

        setVisible(true);
	}
}