package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import javax.imageio.ImageIO;

public class PannelloMenu extends Pannello {

    public PannelloMenu() {
        super(new BorderLayout());
        setBounds(50, 50, 600, 400);

        // Carica lo sfondo
        try {
            sfondo = ImageIO.read(getClass().getResource("/img/sfondo.png"));
        } catch (IOException | IllegalArgumentException e) {
            sfondo = null;
        }

        add(Box.createRigidArea(new Dimension(100, 0)), BorderLayout.WEST);
        add(Box.createRigidArea(new Dimension(100, 0)), BorderLayout.EAST);

        JPanel pannelloInternoMenu = new JPanel();
        pannelloInternoMenu.setBackground(VERDE_HOVER);
        pannelloInternoMenu.setOpaque(true);
        pannelloInternoMenu.setLayout(new BoxLayout(pannelloInternoMenu, BoxLayout.Y_AXIS));
        
        //150px di spazio dall'inizio del pannelloInternoMenu, inizia ad insrire dal 151px qualsiasi oggetto
        pannelloInternoMenu.add(Box.createVerticalStrut(150));
        
        MioBottone bottoneStart = new MioBottone("Inizio Partita");
        bottoneStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneStart.setPreferredSize(new Dimension(200, 40));
        bottoneStart.setMaximumSize(new Dimension(200, 40));
        bottoneStart.setMargin(new Insets(10, 10, 10, 10));
        pannelloInternoMenu.add(bottoneStart);
        
        //dopo il primo bottone aggiunge 50px
        pannelloInternoMenu.add(Box.createVerticalStrut(50));

        //JPanel pannelloGiocatori = new JPanel();
        //pannelloGiocatori.setLayout(new BoxLayout(pannelloGiocatori, BoxLayout.Y_AXIS));
        //pannelloGiocatori.setOpaque(false);
        //pannelloGiocatori.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelGiocatori = new JLabel("Giocatori", SwingConstants.CENTER);
        labelGiocatori.setFont(new Font("SansSerif", Font.BOLD, 14));
        labelGiocatori.setForeground(Color.BLACK);
        labelGiocatori.setOpaque(true);
        labelGiocatori.setBackground(VERDE_HOVER);
        labelGiocatori.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelGiocatori.setPreferredSize(new Dimension(200, 40));
        labelGiocatori.setMaximumSize(new Dimension(200, 40));
        labelGiocatori.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        MioSpinner mioSpinner = new MioSpinner();
        mioSpinner.setAlignmentX(Component.CENTER_ALIGNMENT);
        mioSpinner.setPreferredSize(new Dimension(200, 40));
        mioSpinner.setMaximumSize(new Dimension(200, 40));

        pannelloInternoMenu.add(labelGiocatori);
        pannelloInternoMenu.add(Box.createVerticalStrut(10));
        pannelloInternoMenu.add(mioSpinner);
        
        add(pannelloInternoMenu);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (sfondo != null) {
            g.drawImage(sfondo, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("pannelloMenu aggiornato con: " + arg);
    }
}
