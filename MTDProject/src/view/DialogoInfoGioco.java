package view;

import java.awt.*;
import javax.swing.*;

public class DialogoInfoGioco extends MioDialog {

    private JPanel pannelloCentrale;

    public DialogoInfoGioco(Frame owner, String titolo, String info, int durataMillis) {
        super(owner, titolo, 250, 80);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0)); // finestra trasparente

        // Pannello con angoli arrotondati e sfondo verde
        pannelloCentrale = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 128, 0, 230)); // verde semi-trasparente
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
            }
        };
        pannelloCentrale.setOpaque(false);
        pannelloCentrale.setLayout(new GridBagLayout());
        pannelloCentrale.setPreferredSize(new Dimension(getWidth(), getHeight()));
        pannelloCentrale.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Label del messaggio
        MioLabel labelInfo = new MioLabel(info);
        labelInfo.setHorizontalAlignment(SwingConstants.CENTER);
        labelInfo.setVerticalAlignment(SwingConstants.CENTER);
        labelInfo.setFont(View.FONT_GIOCO);
        labelInfo.setForeground(Color.BLACK);
        labelInfo.setOpaque(false); // sfondo trasparente

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        pannelloCentrale.add(labelInfo, gbc);

        setContentPane(pannelloCentrale);
        setSize(250, 60);

        // Posizionamento leggermente sopra il centro del frame
        int x = owner.getX() + (owner.getWidth() - getWidth()) / 2;
        int y = owner.getY() + (owner.getHeight() - getHeight()) / 2 - 100; // 100 pixel sopra il centro
        setLocation(x, y);

        // Timer per chiusura automatica
        Timer timer = new Timer(durataMillis, e -> dispose());
        timer.setRepeats(false);
        timer.start();

        setVisible(true);
    }
}
