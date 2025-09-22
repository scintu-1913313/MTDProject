package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public abstract class MioSpinner extends JPanel {

    // Editor personalizzato con frecce orizzontali
	public final MioBottone leftButton;
	public final JButton rightButton;
	public final JLabel intestazione;

    public MioSpinner(String titolo){
        setLayout(new BorderLayout(5, 0));
        setBackground(new Color(0, 0, 0, 0));

        ImageIcon iconaOriginaleSx = new ImageIcon(getClass().getResource("/img/frecciaSx.png"));
        Image iconaRidottaSx = iconaOriginaleSx.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        leftButton = new MioBottone(new ImageIcon(iconaRidottaSx));
        leftButton.setPreferredSize(new Dimension(40, 30));

        ImageIcon iconaOriginaleDx = new ImageIcon(getClass().getResource("/img/frecciaDx.png"));
        Image iconaRidottaDx = iconaOriginaleDx.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        rightButton = new MioBottone(new ImageIcon(iconaRidottaDx));
        rightButton.setPreferredSize(new Dimension(40, 30));

        add(leftButton, BorderLayout.WEST);
        add(rightButton, BorderLayout.EAST);
        
        intestazione = new JLabel(titolo, SwingConstants.CENTER);

        intestazione.setFont(new Font("SansSerif", Font.BOLD, 14));
        intestazione.setForeground(Color.BLACK);
        intestazione.setOpaque(true);
        intestazione.setBackground(Pannello.VERDE_PANNELLO);
        add(intestazione, BorderLayout.NORTH);

    }
}

