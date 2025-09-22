package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import javax.imageio.ImageIO;

public class PannelloMenu extends Pannello {

    public PannelloMenu(View view) {
        super(new BorderLayout());
        setSize(new Dimension(Pannello.LARGHEZZA-400, Pannello.ALTEZZA-300));
        setBackground(VERDE_HOVER);
		setOpaque(false);

        JPanel pannelloInternoMenu = new JPanel();
        pannelloInternoMenu.setBackground(VERDE_HOVER);
        pannelloInternoMenu.setOpaque(true);
        pannelloInternoMenu.setLayout(new BoxLayout(pannelloInternoMenu, BoxLayout.Y_AXIS));
        
        //150px di spazio dall'inizio del pannelloInternoMenu, inizia ad insrire dal 151px qualsiasi oggetto
        //pannelloInternoMenu.add(Box.createVerticalStrut(150));
        
        MioBottone bottoneStart = new MioBottone("Inizio Partita");
        bottoneStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneStart.setPreferredSize(new Dimension(140, 40));
        bottoneStart.setMaximumSize(new Dimension(140, 40));
        bottoneStart.setMargin(new Insets(10, 10, 10, 10));
        bottoneStart.addActionListener(e -> view.showPannelloGioco());

        pannelloInternoMenu.add(bottoneStart);
        
        //dopo il primo bottone aggiunge 50px
        pannelloInternoMenu.add(Box.createVerticalStrut(20));

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
        
        pannelloInternoMenu.add(Box.createVerticalStrut(50));

        MioIntSpinner spinnerNumeroGiocatori = new MioIntSpinner(1,1,4,1);
        spinnerNumeroGiocatori.setAlignmentX(Component.CENTER_ALIGNMENT);
        spinnerNumeroGiocatori.setPreferredSize(new Dimension(140, 40));
        spinnerNumeroGiocatori.setMaximumSize(new Dimension(140, 40));

        pannelloInternoMenu.add(labelGiocatori);
        pannelloInternoMenu.add(Box.createVerticalStrut(10));
        pannelloInternoMenu.add(spinnerNumeroGiocatori);
        
        add(pannelloInternoMenu);

        MioBottone bottoneRegole = new MioBottone("Regole");
        bottoneRegole.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneRegole.setPreferredSize(new Dimension(140, 40));
        bottoneRegole.setMaximumSize(new Dimension(140, 40));
        bottoneRegole.setMargin(new Insets(10, 10, 10, 10));
        bottoneRegole.addActionListener(e -> view.showPannelloRegole());
        pannelloInternoMenu.setVisible(true);
        add(bottoneRegole, BorderLayout.SOUTH);
    }
    
    @Override
    public void update(Observable o, Object arg) {
    }
}
