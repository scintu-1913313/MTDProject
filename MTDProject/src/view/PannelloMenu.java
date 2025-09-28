package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import javax.imageio.ImageIO;
import carte.*;

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
        
        MioBottone bottoneStart = new MioBottone("Inizio Partita");
        bottoneStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneStart.setPreferredSize(new Dimension(140, 40));
        bottoneStart.setMaximumSize(new Dimension(140, 40));
        bottoneStart.setMinimumSize(new Dimension(140, 40));
        bottoneStart.setMargin(new Insets(10, 10, 10, 10));
        bottoneStart.addActionListener(e -> view.showPannelloGioco());

        pannelloInternoMenu.add(bottoneStart);
        
        //dopo il primo bottone aggiunge 40px
        pannelloInternoMenu.add(Box.createVerticalStrut(35));

        MioIntSpinner spinnerNumeroGiocatori = new MioIntSpinner("Giocatori",1,1,4,1);
        spinnerNumeroGiocatori.setAlignmentX(Component.CENTER_ALIGNMENT);
        spinnerNumeroGiocatori.setPreferredSize(new Dimension(140, 40));
        spinnerNumeroGiocatori.setMaximumSize(new Dimension(140, 40));     
        pannelloInternoMenu.add(spinnerNumeroGiocatori);

        pannelloInternoMenu.add(Box.createVerticalStrut(15));
        
        MioIntSpinner spinnerPunteggio = new MioIntSpinner("Punteggio",11,11,41,10);
        spinnerPunteggio.setAlignmentX(Component.CENTER_ALIGNMENT);
        spinnerPunteggio.setPreferredSize(new Dimension(140, 40));
        spinnerPunteggio.setMaximumSize(new Dimension(140, 40));
        pannelloInternoMenu.add(spinnerPunteggio);

        pannelloInternoMenu.add(Box.createVerticalStrut(15));
        
        Mazzo mazzo = new Mazzo.MazzoBuilder().generaCarte(TipoMazzo.NAPOLETANTE).build();
        ArrayList<Carta> arrayListCarte = new ArrayList<>(mazzo.getCarte());
        
        MioImgSpinner spinnerTipoCarte = new MioImgSpinner("Tipo Carte",arrayListCarte);
        spinnerTipoCarte.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        pannelloInternoMenu.add(spinnerTipoCarte);

        pannelloInternoMenu.add(Box.createVerticalStrut(20));

        MioLabel intestazioneBottoneAccusa = new MioLabel("Accusa");
        intestazioneBottoneAccusa.setAlignmentX(Component.CENTER_ALIGNMENT);
        pannelloInternoMenu.add(intestazioneBottoneAccusa);

        MioBottoneSelezione bottoneAccusa = new MioBottoneSelezione();
        bottoneAccusa.setAlignmentX(Component.CENTER_ALIGNMENT);
        pannelloInternoMenu.add(bottoneAccusa);
        
        pannelloInternoMenu.add(Box.createVerticalStrut(20));

        add(pannelloInternoMenu);
        
        pannelloInternoMenu.setVisible(true);

        pannelloInternoMenu.add(Box.createVerticalStrut(20));

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
