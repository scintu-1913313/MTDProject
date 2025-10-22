package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import carte.*;
import model.GestoreAudio;

/** 
 * Pannello che espone il menu principale del gioco: impostazioni partita,
 * avvio partita, scelta mazzo, punteggio e opzioni di musica/accuse.
 */
public class PannelloMenu extends Pannello {

    /** Riferimento alla View principale. */
	private View view;

    /** Bottone per avviare la partita. */
	private MioBottone bottoneStart;

    /** Spinner per selezionare il numero di giocatori. */
	private MioIntSpinner spinnerNumeroGiocatori;

    /** Spinner per selezionare il punteggio di vittoria. */
	private MioIntSpinner spinnerPunteggio;

    /** Spinner per selezionare il tipo di mazzo di carte. */
	private MioImgSpinner spinnerTipoCarte;

    /** Bottone per abilitare/disabilitare l'opzione accusa. */
	private MioBottoneSelezione bottoneAccusa;

    /** Bottone per attivare/disattivare la musica di sottofondo. */
    private MioBottone bottoneMusica;

    /**
     * Costruisce il pannello principale del menu' dell'applicazione.
     * @param view riferimento alla View principale
     */
    public PannelloMenu(View view) {
        super(new BorderLayout());
    	this.view = view;
        setSize(new Dimension(Pannello.LARGHEZZA-400, Pannello.ALTEZZA-300));
        setBackground(View.VERDE_PANNELLO);
		setOpaque(false);

        JPanel pannelloInternoMenu = new JPanel();
        pannelloInternoMenu.setBackground(View.VERDE_PANNELLO);
        pannelloInternoMenu.setOpaque(true);
        pannelloInternoMenu.setLayout(new BoxLayout(pannelloInternoMenu, BoxLayout.Y_AXIS));
        
        //150px di spazio dall'inizio del pannelloInternoMenu, inizia ad insrire dal 151px qualsiasi oggetto
        
        // Pannello superiore con layout personalizzato
        JPanel pannelloInAlto = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pannelloInAlto.setBackground(View.VERDE_PANNELLO);
        pannelloInAlto.setOpaque(true);
        
        MioBottone bottoneExit= new MioBottone("Esci");
        bottoneExit.setPreferredSize(new Dimension(50, 50));
        bottoneExit.setMaximumSize(new Dimension(50, 50));
        bottoneExit.setMinimumSize(new Dimension(50, 50));
        bottoneExit.setMargin(new Insets(10, 10, 10, 10));
        bottoneExit.addActionListener(e -> {System.exit(0);});        
        
        bottoneStart = new MioBottone("Inizio Partita");
        bottoneStart.setPreferredSize(new Dimension(140, 40));
        bottoneStart.setMaximumSize(new Dimension(140, 40));
        bottoneStart.setMinimumSize(new Dimension(140, 40));
        bottoneStart.setMargin(new Insets(10, 10, 10, 10));
        bottoneStart.addActionListener(e -> iniziaPartita());
    
        
        MioBottoneInfo bottoneAccount= new MioBottoneInfo();
        bottoneAccount.setPreferredSize(new Dimension(50, 50));
        bottoneAccount.setMaximumSize(new Dimension(50, 50));
        bottoneAccount.setMinimumSize(new Dimension(50, 50));
        bottoneAccount.setMargin(new Insets(10, 10, 10, 10));
        bottoneAccount.addActionListener(e -> view.showPannelloAccount());

        pannelloInAlto.add(bottoneExit);
        pannelloInAlto.add(Box.createHorizontalStrut(197));
        pannelloInAlto.add(bottoneStart);
        //UNAA VOLTA SCELTA L'IMMAGINE CREARE LO SPAZIO
        pannelloInAlto.add(Box.createHorizontalStrut(197));
        pannelloInAlto.add(bottoneAccount);


        pannelloInternoMenu.add(pannelloInAlto, BorderLayout.CENTER);
                   
        //dopo il primo bottone aggiunge 10px
        pannelloInternoMenu.add(Box.createVerticalStrut(10));

        spinnerNumeroGiocatori = new MioIntSpinner("Giocatori",2,2,4,1);
        spinnerNumeroGiocatori.setAlignmentX(Component.CENTER_ALIGNMENT);
        spinnerNumeroGiocatori.setPreferredSize(new Dimension(140, 40));
        spinnerNumeroGiocatori.setMaximumSize(new Dimension(140, 40));     
        pannelloInternoMenu.add(spinnerNumeroGiocatori);

        pannelloInternoMenu.add(Box.createVerticalStrut(10));
        
        spinnerPunteggio = new MioIntSpinner("Punteggio",11,11,41,10);
        spinnerPunteggio.setAlignmentX(Component.CENTER_ALIGNMENT);
        spinnerPunteggio.setPreferredSize(new Dimension(140, 40));
        spinnerPunteggio.setMaximumSize(new Dimension(140, 40));
        pannelloInternoMenu.add(spinnerPunteggio);

        pannelloInternoMenu.add(Box.createVerticalStrut(10));
        
        MazzoFactory factory = new FactoryMazzoAssiCoppe();
        
        Mazzo mazzo = factory.creaMazzo();
        ArrayList<Object> arrayListCarte = new ArrayList<>(mazzo.getCarte());
        
        spinnerTipoCarte = new MioImgSpinner("Tipo Carte",arrayListCarte);
        spinnerTipoCarte.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        pannelloInternoMenu.add(spinnerTipoCarte);

        pannelloInternoMenu.add(Box.createVerticalStrut(10));

        MioLabel intestazioneBottoneAccusa = new MioLabel("Accusa");
        intestazioneBottoneAccusa.setAlignmentX(Component.CENTER_ALIGNMENT);
        pannelloInternoMenu.add(intestazioneBottoneAccusa);

        bottoneAccusa = new MioBottoneSelezione("/img/checkboxVuota.png","/img/checkboxCliccata.png");
        bottoneAccusa.setAlignmentX(Component.CENTER_ALIGNMENT);
        pannelloInternoMenu.add(bottoneAccusa);
        
        pannelloInternoMenu.add(Box.createVerticalStrut(10));
        
        // Pannello superiore con layout personalizzato
        JPanel pannelloInBasso= new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pannelloInBasso.setBackground(View.VERDE_PANNELLO);
        pannelloInBasso.setOpaque(true);
        
        MioBottone bottoneRegole = new MioBottone("Regole");
        bottoneRegole.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneRegole.setPreferredSize(new Dimension(140, 40));
        bottoneRegole.setMaximumSize(new Dimension(140, 40));
        bottoneRegole.setMinimumSize(new Dimension(140, 40));
        bottoneRegole.setMargin(new Insets(10, 10, 10, 10));
        bottoneRegole.addActionListener(e -> view.showPannelloRegole());
        
        pannelloInBasso.add(bottoneRegole);
        
        // Bottone per attivare/disattivare la musica
        bottoneMusica = new MioBottone(GestoreAudio.getInstance().isMusicaAbilitata() ? "Musica: ON" : "Musica: OFF");
        bottoneMusica.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottoneMusica.setPreferredSize(new Dimension(140, 40));
        bottoneMusica.setMaximumSize(new Dimension(140, 40));
        bottoneMusica.setMinimumSize(new Dimension(140, 40));
        bottoneMusica.setMargin(new Insets(10, 10, 10, 10));
        bottoneMusica.addActionListener(e -> {
            // Riproduci suono del pulsante
            // Cambia stato della musica
        	boolean newState = GestoreAudio.getInstance().cambiaStatoMusica();
            bottoneMusica.setText(newState ? "Musica: ON" : "Musica: OFF");
            view.getPannelloGioco().aggiornaStatoBottoneMusica();
        });
        pannelloInBasso.add(bottoneMusica);

        pannelloInternoMenu.add(pannelloInBasso);

        add(pannelloInternoMenu);
        
        pannelloInternoMenu.setVisible(true);

        pannelloInternoMenu.add(Box.createVerticalStrut(10));        
    }
    
    /**
     * Avvia la procedura di inizio partita controllando i dati dell'utente.
     */
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
    		view.iniziaGioco();
    	}
	}
	
    /**
     * Restituisce il bottone "Inizio Partita" usato per avviare la partita.
     * @return il bottone di start
     */
	public MioBottone getBottoneStart() {
		return this.bottoneStart;
	}
	
    /**
     * Restituisce il numero di giocatori selezionato nello spinner.
     * @return numero di giocatori (2-4)
     */
	public int getNumeroGiocatori() {
		return this.spinnerNumeroGiocatori.getValoreCorrente();
	}
	
    /**
     * Restituisce il punteggio stabilito selezionato nello spinner.
     * @return punteggio di vittoria
     */
	public int getPunteggioStabilito() {
		return this.spinnerPunteggio.getValoreCorrente();
	}
	
    /**
     * Restituisce il tipo di mazzo selezionato tramite lo spinner immagini.
     * @return il TipoMazzo selezionato
     */
	public TipoMazzo getTipoMazzo() {
        Carta c = (Carta) this.spinnerTipoCarte.getOggettoCorrente();
        return c.getTipoMazzo();
	}
	
    /**
     * Indica se è stata selezionata l'opzione "Accusa".
     * @return true se l'accusa è selezionata
     */
	public boolean getAccusa()
	{
		return  this.bottoneAccusa.getCliccato();
	}
	
    /**
     * Aggiorna la label del bottone musica in base allo stato corrente.
     */
	public void aggiornaStatoBottoneMusica() {
		boolean stato = GestoreAudio.getInstance().isMusicaAbilitata();
        bottoneMusica.setText(stato ? "Musica: ON" : "Musica: OFF");
	}
}
