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

	private View view;
	private MioBottone bottoneStart;
	private MioIntSpinner spinnerNumeroGiocatori;
	private MioIntSpinner spinnerPunteggio;
	private MioImgSpinner spinnerTipoCarte;
	private MioBottoneSelezione bottoneAccusa;
	
    public PannelloMenu(View view) {
        super(new BorderLayout());
    	this.view = view;
        setSize(new Dimension(Pannello.LARGHEZZA-400, Pannello.ALTEZZA-300));
        setBackground(VERDE_PANNELLO);
		setOpaque(false);

        JPanel pannelloInternoMenu = new JPanel();
        pannelloInternoMenu.setBackground(VERDE_PANNELLO);
        pannelloInternoMenu.setOpaque(true);
        pannelloInternoMenu.setLayout(new BoxLayout(pannelloInternoMenu, BoxLayout.Y_AXIS));
        
        //150px di spazio dall'inizio del pannelloInternoMenu, inizia ad insrire dal 151px qualsiasi oggetto
        
     // Pannello superiore con layout personalizzato
        JPanel pannelloInAlto = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pannelloInAlto.setBackground(VERDE_PANNELLO);
        pannelloInAlto.setOpaque(true);
        
        bottoneStart = new MioBottone("Inizio Partita");
        //bottoneStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneStart.setPreferredSize(new Dimension(140, 40));
        bottoneStart.setMaximumSize(new Dimension(140, 40));
        bottoneStart.setMinimumSize(new Dimension(140, 40));
        bottoneStart.setMargin(new Insets(10, 10, 10, 10));
        bottoneStart.addActionListener(e -> iniziaPartita());
    
        
        MioBottoneInfo bottoneAccount= new MioBottoneInfo();
        //bottoneInfo.setAlignmentX(Component.RIGHT_ALIGNMENT);
        bottoneAccount.setPreferredSize(new Dimension(50, 50));
        bottoneAccount.setMaximumSize(new Dimension(50, 50));
        bottoneAccount.setMinimumSize(new Dimension(50, 50));
        bottoneAccount.setMargin(new Insets(10, 10, 10, 10));
        bottoneAccount.addActionListener(e -> view.showPannelloAccount());

        
        pannelloInAlto.add(bottoneStart);
        //UNAA VOLTA SCELTA L'IMMAGINE CREARE LO SPAZIO
        pannelloInAlto.add(Box.createHorizontalStrut(197));
        pannelloInAlto.add(bottoneAccount);


        pannelloInternoMenu.add(pannelloInAlto, BorderLayout.CENTER);
           
        //pannelloInternoMenu.add(Box.createVerticalStrut(15));
        
        //dopo il primo bottone aggiunge 40px
        pannelloInternoMenu.add(Box.createVerticalStrut(10));

        spinnerNumeroGiocatori = new MioIntSpinner("Giocatori",2,2,4,1);
        spinnerNumeroGiocatori.setAlignmentX(Component.CENTER_ALIGNMENT);
        spinnerNumeroGiocatori.setPreferredSize(new Dimension(140, 40));
        spinnerNumeroGiocatori.setMaximumSize(new Dimension(140, 40));     
        pannelloInternoMenu.add(spinnerNumeroGiocatori);

        pannelloInternoMenu.add(Box.createVerticalStrut(15));
        
        spinnerPunteggio = new MioIntSpinner("Punteggio",11,11,41,10);
        spinnerPunteggio.setAlignmentX(Component.CENTER_ALIGNMENT);
        spinnerPunteggio.setPreferredSize(new Dimension(140, 40));
        spinnerPunteggio.setMaximumSize(new Dimension(140, 40));
        pannelloInternoMenu.add(spinnerPunteggio);

        pannelloInternoMenu.add(Box.createVerticalStrut(15));
        
        Mazzo mazzo = new Mazzo.MazzoBuilder().generaAssoDeiMazzi().build();
        ArrayList<Object> arrayListCarte = new ArrayList<>(mazzo.getCarte());
        
        spinnerTipoCarte = new MioImgSpinner("Tipo Carte",arrayListCarte);
        spinnerTipoCarte.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        pannelloInternoMenu.add(spinnerTipoCarte);

        pannelloInternoMenu.add(Box.createVerticalStrut(15));

        MioLabel intestazioneBottoneAccusa = new MioLabel("Accusa");
        intestazioneBottoneAccusa.setAlignmentX(Component.CENTER_ALIGNMENT);
        pannelloInternoMenu.add(intestazioneBottoneAccusa);

        bottoneAccusa = new MioBottoneSelezione();
        bottoneAccusa.setAlignmentX(Component.CENTER_ALIGNMENT);
        pannelloInternoMenu.add(bottoneAccusa);
        
        pannelloInternoMenu.add(Box.createVerticalStrut(10));
        
        MioBottone bottoneRegole = new MioBottone("Regole");
        bottoneRegole.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneRegole.setPreferredSize(new Dimension(140, 40));
        bottoneRegole.setMaximumSize(new Dimension(140, 40));
        bottoneRegole.setMinimumSize(new Dimension(140, 40));
        bottoneRegole.setMargin(new Insets(10, 10, 10, 10));
        bottoneRegole.addActionListener(e -> view.showPannelloRegole());
        pannelloInternoMenu.setVisible(true);
        
        pannelloInternoMenu.add(bottoneRegole);

        add(pannelloInternoMenu);
        
        pannelloInternoMenu.setVisible(true);

        pannelloInternoMenu.add(Box.createVerticalStrut(15));


        //add(bottoneRegole, BorderLayout.SOUTH);
        
        
        
    }
    
	private void iniziaPartita()
	{
		PannelloAccount p = (PannelloAccount) view.getPannelloAccount();
		String nickname = p.getNickname();
    	if (nickname.isEmpty()) {
    		JOptionPane.showMessageDialog(null, "utente non registrato, andare nell'account per registrarsi", "Warning", JOptionPane.WARNING_MESSAGE);
    		return;
    	} 
    	else 
    	{
    		view.showPannelloGioco();
    	}
	}
	
	public MioBottone getBottoneStart() {
		return this.bottoneStart;
	}
	
	public int getNumeroGiocatori() {
		return this.spinnerNumeroGiocatori.getValoreCorrente();
	}
	
	public int getPunteggioStabilito() {
		return this.spinnerPunteggio.getValoreCorrente();
	}
	
	public TipoMazzo getTipoMazzo() {
		Carta c = (Carta) this.spinnerTipoCarte.getOggettoCorrente();
		return c.getTipoMazzo();
	}
	
	public boolean getAccusa()
	{
		return  this.bottoneAccusa.getCliccato();
	}
	
    @Override
    public void update(Observable o, Object arg) {
    }
}
