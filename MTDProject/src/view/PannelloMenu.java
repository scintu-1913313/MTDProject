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
        setBackground(VERDE_PANNELLO);
		setOpaque(false);

        JPanel pannelloInternoMenu = new JPanel();
        pannelloInternoMenu.setBackground(VERDE_PANNELLO);
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

        MioIntSpinner spinnerNumeroGiocatori = new MioIntSpinner("Giocatori",1,1,4,1);
        spinnerNumeroGiocatori.setAlignmentX(Component.CENTER_ALIGNMENT);
        spinnerNumeroGiocatori.setPreferredSize(new Dimension(140, 40));
        spinnerNumeroGiocatori.setMaximumSize(new Dimension(140, 40));
        //spinnerNumeroGiocatori.add(labelGiocatori,BorderLayout.NORTH);
        
        pannelloInternoMenu.add(Box.createVerticalStrut(10));
        pannelloInternoMenu.add(spinnerNumeroGiocatori);
        
        MioIntSpinner spinnerTipoCarte = new MioIntSpinner("Tipo Carte",1,1,4,1);
        spinnerNumeroGiocatori.setAlignmentX(Component.CENTER_ALIGNMENT);
        spinnerNumeroGiocatori.setPreferredSize(new Dimension(140, 40));
        spinnerNumeroGiocatori.setMaximumSize(new Dimension(140, 40));
        
        
        add(pannelloInternoMenu);
        
        /*MioBottoneSelezione bottoneSelezione = new MioBottoneSelezione("",pannelloInternoMenu, "/src/img/frecciaSx.png");
        bottoneSelezione.setAlignmentX(Component.RIGHT_ALIGNMENT);
        bottoneSelezione.setPreferredSize(new Dimension(70, 40));
        bottoneSelezione.setMaximumSize(new Dimension(70, 40));
        bottoneSelezione.setMargin(new Insets(10, 10, 10, 10));
        pannelloInternoMenu.setVisible(true);
        */
        
        /*ImageIcon icon = new ImageIcon(getClass().getResource("/img/spunta.png"));
        Image iconaRidottaSx = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);

        System.out.println("Icon width: " + icon.getIconWidth()); // Debug
        MioBottone bottone = new MioBottone(new ImageIcon(iconaRidottaSx));
        //bottone.setIcon(icon);        */
        
        MioBottoneSelezione bottoneSpunta = new MioBottoneSelezione();
        
        pannelloInternoMenu.setVisible(true);

        pannelloInternoMenu.add(Box.createVerticalStrut(20));


        pannelloInternoMenu.add(bottoneSpunta);


               


        MioBottone bottoneRegole = new MioBottone("Regole");
        bottoneRegole.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneRegole.setPreferredSize(new Dimension(8, 40));
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
