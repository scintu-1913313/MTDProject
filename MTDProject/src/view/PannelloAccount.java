package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import model.Avatar;
import model.AvatarEnum;
import model.Model;
import model.Partita;
import model.Utente;

/**
 * Pannello che permette la gestione dell'account utente: 
 * selezione avatar, inserimento nickname, visualizzazione livello e storico partite.
 */
public class PannelloAccount extends Pannello{

	/** Pannello interno contenente gli elementi dell'account. */
	private JPanel pannelloInternoAccount;

	/** Spinner per la selezione dell'avatar. */
	private MioImgSpinner spinnerTipoAvatar;

	/** Campo di testo per l'inserimento del nickname. */
	private JTextField nicknameField;

	/** Indica se l'utente è registrato. */
	private boolean utenteRegistrato = false;

	/** Label che mostra il livello dell'utente. */
	private MioLabel livello;

	/** Bottone per salvare i dati dell'utente. */
	private MioBottone bottoneSalvataggioDati;

	/** Area di testo che mostra lo storico delle partite giocate. */
	private JTextArea areaTesto;

	/** Riferimento alla View principale. */
	private View view;
	
	/**
	 * Costruisce il pannello account che permette la gestione dell'utente.
	 * @param view riferimento alla View principale
	 */
	public PannelloAccount(View view) {
		super(new BorderLayout());
		this.view = view;
		setBackground(View.VERDE_PANNELLO);
		setOpaque(false);
		
		pannelloInternoAccount = new JPanel();
		pannelloInternoAccount.setBackground(View.VERDE_PANNELLO);
		pannelloInternoAccount.setOpaque(true);
		pannelloInternoAccount.setLayout(new BoxLayout(pannelloInternoAccount, BoxLayout.Y_AXIS));
		
        ArrayList<Object> arrayListAvatar = new ArrayList<>();
        arrayListAvatar.add(new Avatar(AvatarEnum.DEFAULT));
        arrayListAvatar.add(new Avatar(AvatarEnum.AVATAR1));
        arrayListAvatar.add(new Avatar(AvatarEnum.AVATAR2));
        arrayListAvatar.add(new Avatar(AvatarEnum.AVATAR3));
        arrayListAvatar.add(new Avatar(AvatarEnum.AVATAR4));

        spinnerTipoAvatar = new MioImgSpinner("Avatar",arrayListAvatar);
        spinnerTipoAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        pannelloInternoAccount.add(spinnerTipoAvatar);
        pannelloInternoAccount.add(Box.createVerticalStrut(15));

        nicknameField = new JTextField(20);
        nicknameField.setText("");
        nicknameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        nicknameField.setHorizontalAlignment(JTextField.CENTER);
        nicknameField.setPreferredSize(new Dimension(140, 40));
        nicknameField.setMaximumSize(new Dimension(140, 40));
        nicknameField.setMinimumSize(new Dimension(140, 40));
        nicknameField.setFont(view.FONT_GIOCO);

        pannelloInternoAccount.add(nicknameField);

        pannelloInternoAccount.add(Box.createVerticalStrut(15));

        livello = new MioLabel("Livello: N/A");
        livello.setAlignmentX(Component.CENTER_ALIGNMENT);
        pannelloInternoAccount.add(livello);
        
        pannelloInternoAccount.add(Box.createVerticalStrut(15));

        MioLabel labelStoricoPartite = new MioLabel("Storico partite");
        labelStoricoPartite.setAlignmentX(Component.CENTER_ALIGNMENT);
        pannelloInternoAccount.add(labelStoricoPartite);
        
		areaTesto = new JTextArea("");
        areaTesto.setEditable(false);
        areaTesto.setLineWrap(true);
        areaTesto.setWrapStyleWord(true);
        areaTesto.setOpaque(true);
        areaTesto.setBackground(View.VERDE_SCURO);
        areaTesto.setForeground(Color.BLACK); // testo nero per leggibilità        
        areaTesto.setFont(View.FONT_GIOCO);
        // ScrollPane con solo scrollbar verticale visibile
        JScrollPane scrollPane = new JScrollPane(areaTesto);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBackground(new Color(0, 0, 0, 0));
        scrollPane.getViewport().setBackground(new Color(0, 0, 0, 0));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        pannelloInternoAccount.add(scrollPane, BorderLayout.CENTER);
        
		add(pannelloInternoAccount, BorderLayout.CENTER);

		// Bottone in basso a destra
		JPanel downPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		downPanel.setBackground(View.TRASPARENTE);
		downPanel.setOpaque(false);

		bottoneSalvataggioDati = new MioBottone("Salva");
		bottoneSalvataggioDati.setPreferredSize(new Dimension(100, 40));
		bottoneSalvataggioDati.setMaximumSize(new Dimension(100, 40));
		bottoneSalvataggioDati.addActionListener(e -> controlloDati());
		
				
		MioBottone bottoneRitornoMenu = new MioBottone("Menu'");
		bottoneRitornoMenu.setPreferredSize(new Dimension(100, 40));
		bottoneRitornoMenu.setMaximumSize(new Dimension(100, 40));
		bottoneRitornoMenu.addActionListener(e -> view.showPannelloMenu());
		
		downPanel.add(bottoneSalvataggioDati);
		downPanel.add(Box.createHorizontalStrut(160));
		downPanel.add(bottoneRitornoMenu);
		add(downPanel, BorderLayout.SOUTH);

	}
	
	/**
	 * Indica se l'utente corrente è registrato.
	 * @return true se registrato
	 */
	public boolean registrazioneOk() {
		return utenteRegistrato;
	}
	
	/**
	 * Restituisce il bottone di salvataggio dati nella UI.
	 * @return il bottone "Salva"
	 */
	public MioBottone getBottoneSalvataggioDati() {
		return bottoneSalvataggioDati;
	}
	
	/*
	 * Aggiornamento ricevuto dal modello; aggiorna i campi utente.
	 */
    @Override
    public void update(Observable o, Object arg) {
    	if (!(o instanceof Model && arg instanceof Utente))
    	{
             return;
    	}
    	Utente g = (Utente) arg;
    	nicknameField.setText(g.getNickname());
    	spinnerTipoAvatar.vaiAdOggettoSpecifico(new Avatar(g.getAvatarEnum()));
    	utenteRegistrato = g.isRegistrato();
    	if(g.isRegistrato())
    	{
            livello.setText("Livello: " + String.valueOf(g.getLivello()));
    	}
    	else
    	{
    		livello.setText("Livello: N/A");
    	}
    	String partite = "";
    	for (Partita p : g.getPartite()) {
    		partite += p.toString()+"\n";
    	}
    		areaTesto.setText(partite);
    	
    }

	/**
	 * Restituisce l'Avatar selezionato nello spinner.
	 * @return Avatar corrente
	 */
    public Avatar getAvatar() {
    	return  (Avatar) spinnerTipoAvatar.getOggettoCorrente();

    }

	/**
	 * Restituisce il testo presente nel campo nickname.
	 * @return nickname inserito dall'utente
	 */
    public String getNickname() {
    	return nicknameField.getText();
    }
    
	/**
	 * Valida e salva localmente i dati dell'utente; notifica la View.
	 */
    public void controlloDati() {
    	String nome = nicknameField.getText();
    	if (nome.isEmpty()) {
    		JOptionPane.showMessageDialog(null, "Il nickname non puo' essere vuoto", "Warning", JOptionPane.WARNING_MESSAGE);
    		return;
    	} 
    	else
    	{
    		JOptionPane.showMessageDialog(null, "Dati salvati correttamente!", "Salvataggio", JOptionPane.INFORMATION_MESSAGE);
    		view.aggiornaDatiUtente();
    	}
    }
}
