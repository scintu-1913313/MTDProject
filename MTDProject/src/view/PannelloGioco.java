package view;

import carte.Carta;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import javax.imageio.ImageIO;
import javax.swing.*;
import model.*;

/**
 * Pannello principale di gioco che gestisce l'interfaccia utente durante una partita.
 * Contiene i sottopannelli per i giocatori, il banco e le informazioni di gioco.
 */
public class PannelloGioco extends Pannello {

	/** Tempo di attesa di default tra le azioni. */
	private static int TEMPO_ATTESA_DEFAULT= 1000; //1000ms -> 1s

	/** Tempo di attesa alla fine del turno. */
	private static int TEMPO_ATTESA_FINE_TURNO= 3000; //3000ms -> 3s

	/** Tempo di attesa alla fine del turno. */
	private static int TEMPO_ATTESA_TRA_GIOCATORI= 2000; //2000ms -> 2s

	/** Riferimento alla View principale. */
	private View view;

	/** Pannello principale del gioco. */
	private JPanel pannelloPrincipaleDelGioco; 
	
	/** Pannello in alto con bottoni e informazioni. */
	private JPanel pannelloInAlto;

	/** Bottone per uscire dalla partita. */
	private MioBottone bottoneExit;

	/** Bottone per attivare/disattivare la musica. */
	private MioBottoneSelezione bottoneMusica;

	/** Label per mostrare il round corrente. */
	private MioLabel labelRound;
	
	/** Label per mostrare i punti del giocatore Utente o della squadra1 (Utente + Pc1) */
	private MioLabel label1Punti;

	/** Label per mostrare i punti del Pc1 o della squadra2 (Pc2+Pc3) */
    private MioLabel label2Punti;

	/** Nome del giocatore Utente o della squadra1 (Utente + Pc1). */
    private String nomeGiocatori1;

	/** Nome del Pc1 o della squadra2 (Pc2+Pc3). */
    private String nomeGiocatori2;

	/** Label per il giocatore 1 (Utente). */
    private MioLabel labelGiocatore1;

	/** Label per il giocatore 2 (Pc1). */
    private MioLabel labelGiocatore2;

	/** Label per il giocatore 3 (Pc2). */
    private MioLabel labelGiocatore3;

	/** Label per il giocatore 4 (Pc3). */
    private MioLabel labelGiocatore4;

	/** Pannello sotto per il Giocatore */
	private JPanel pannelloSotto;
	/** Pannello delle carte del giocatore sotto. */
	private JPanel pannelloCarteGiocatoreSotto;
	
    /**pannello sopra per il Pc1.*/
	private JPanel pannelloSopra;
	/** Pannello delle carte del Pc1 sopra. */
	private JPanel pannelloCartePc1Sopra;
	
    /**pannello destra per il Pc2.*/
	private JPanel pannelloDestra;
	/** Pannello delle carte del Pc2 destra. */
	private JPanel pannelloCartePc2Destra;
	
    /**pannello sinistra per il Pc3.*/
	private JPanel pannelloSinistra;
	/** Pannello delle carte del Pc3 sinistra. */
	private JPanel pannelloCartePc3Sinistra;
	
	/** Pannello centrale per il banco. */
	private JPanel pannelloCentrale;
	/** Pannello delle carte del banco interno. */
	private JPanel pannelloCarteBancoInterno;
	
	/** Lista delle carte del giocatore Utente ad ogni turno.*/
    private List<CartaView> carteGiocatore;
	/** Lista delle carte del Pc1 ad ogni turno. */
    private List<CartaView> cartePc1;
	/** Lista delle carte del Pc2 ad ogni turno. */
    private List<CartaView> cartePc2;
	/** Lista delle carte del Pc3 ad ogni turno.*/
    private List<CartaView> cartePc3;
	/** Lista delle carte del banco ad ogni turno.*/
    private List<CartaView> carteBanco;

	/** Riferimento alla partita in corso. */
	private PartitaTressette partitaInCorso;

	/** Indica se è il turno del giocatore Utente. */
	private boolean turnoDelGiocatore;

	/**
	 * Costruisce il pannello di gioco completo con tutti i sottopannelli e componenti.
	 * @param view riferimento alla View principale
	 */
	public PannelloGioco(View view) {
		super(new BorderLayout());
		this.view = view;
		// Carica lo sfondo
        try {
            sfondo = ImageIO.read(getClass().getResource("/img/sfondoGioco.png"));
        } catch (IOException | IllegalArgumentException e) {
            sfondo = null;
        }
        carteGiocatore = new ArrayList<>();
        cartePc1 = new ArrayList<>();
        cartePc2 = new ArrayList<>();
        cartePc3 = new ArrayList<>();
        carteBanco = new ArrayList<>();
        
        pannelloInAlto = new JPanel(new BorderLayout());
        pannelloInAlto.setOpaque(false);

        // Pannello sinistro con bottone Esci
        JPanel pannelloInAltoSinistra = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pannelloInAltoSinistra.setOpaque(false);
        bottoneExit = new MioBottone("Esci");
        bottoneExit.setPreferredSize(new Dimension(50, 50));
        bottoneExit.setMaximumSize(new Dimension(50, 50));
        bottoneExit.setMinimumSize(new Dimension(50, 50));
        bottoneExit.setMargin(new Insets(10, 10, 10, 10));
        bottoneExit.addActionListener(e -> uscitaForzataDalGiocatore());
        pannelloInAltoSinistra.add(bottoneExit);

        // Pannello centrale con le due label
        JPanel pannelloInAltroCentrale = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pannelloInAltroCentrale.setOpaque(false);
        nomeGiocatori1="";
        nomeGiocatori2="";
        label1Punti = new MioLabel("0");
        label2Punti = new MioLabel("0");
        pannelloInAltroCentrale.add(label1Punti);
        pannelloInAltroCentrale.add(Box.createHorizontalStrut(30)); // Spazio tra le label
        pannelloInAltroCentrale.add(label2Punti);
        pannelloInAltroCentrale.add(Box.createHorizontalStrut(30)); // Spazio tra le label
        labelRound = new MioLabel("Round: 1");
        pannelloInAltroCentrale.add(labelRound);

        
        //pannelloCarteBancoCentrale
        JPanel pannelloInAltoDestra = new JPanel();
        pannelloInAltoDestra.setOpaque(false);
                
        bottoneMusica = new MioBottoneSelezione("/img/soundOff.png","/img/soundOn.png");
        bottoneMusica.setPreferredSize(new Dimension(50, 50));
        bottoneMusica.setMaximumSize(new Dimension(50, 50));
        bottoneMusica.setMinimumSize(new Dimension(50, 50));
        bottoneMusica.setMargin(new Insets(10, 10, 10, 10));
        bottoneMusica.addActionListener(e ->{ GestoreAudio.getInstance().cambiaStatoMusica();
        										view.getPannelloMenu().aggiornaStatoBottoneMusica();
        									});
        if(GestoreAudio.getInstance().isMusicaAbilitata()) {
            bottoneMusica.setCliccato(true);
        }
        else
        {
            bottoneMusica.setCliccato(false);
        }
        pannelloInAltoDestra.add(bottoneMusica);

        // Componi il pannello in alto
        pannelloInAlto.add(pannelloInAltoSinistra, BorderLayout.WEST);
        pannelloInAlto.add(pannelloInAltroCentrale, BorderLayout.CENTER);
        pannelloInAlto.add(pannelloInAltoDestra, BorderLayout.EAST);

        add(pannelloInAlto, BorderLayout.NORTH);
        //costurisco e aggiungo i vari pannelli per le carte dei vari giocatori
        //metto tutto in un borderLayout che poi vado ad aggiungere al centro di me stesso\
        pannelloPrincipaleDelGioco = new JPanel(new BorderLayout());
        pannelloPrincipaleDelGioco.setOpaque(false);

        //pannello sopra con carte e label.
        pannelloSopra = new JPanel();
        pannelloSopra.setOpaque(false);
        pannelloSopra.setLayout(new GridBagLayout());
        labelGiocatore2 = new MioLabel(TipoGiocatore.PC1.toString());
        pannelloCartePc1Sopra = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pannelloCartePc1Sopra.setOpaque(false);
        GridBagConstraints gbcSopra = new GridBagConstraints();
        gbcSopra.gridx = 0;
        gbcSopra.gridy = 0;
        gbcSopra.anchor = GridBagConstraints.CENTER;
        gbcSopra.insets = new Insets(0, 0, 5, 0);
        pannelloSopra.add(labelGiocatore2, gbcSopra);
        gbcSopra.gridy = 1;
        pannelloSopra.add(pannelloCartePc1Sopra, gbcSopra);
        pannelloPrincipaleDelGioco.add(pannelloSopra, BorderLayout.NORTH);


        //pannello destra con carte e label.
        pannelloDestra = new JPanel();
        pannelloDestra.setOpaque(false);
        pannelloDestra.setLayout(new BoxLayout(pannelloDestra, BoxLayout.X_AXIS));
        pannelloCartePc2Destra = new JPanel();
        pannelloCartePc2Destra.setLayout(new BoxLayout(pannelloCartePc2Destra, BoxLayout.Y_AXIS));
        pannelloCartePc2Destra.setOpaque(false);
        pannelloDestra.add(pannelloCartePc2Destra);
        pannelloDestra.add(Box.createHorizontalStrut(5));
        labelGiocatore3 = new MioLabel(TipoGiocatore.PC2.toString());
        pannelloDestra.add(labelGiocatore3);
        pannelloPrincipaleDelGioco.add(pannelloDestra, BorderLayout.EAST);

        
        //pannello sinistra con carte e label.
        pannelloSinistra = new JPanel();
        pannelloSinistra.setOpaque(false);
        pannelloSinistra.setLayout(new BoxLayout(pannelloSinistra, BoxLayout.X_AXIS));
        
        labelGiocatore4 = new MioLabel(TipoGiocatore.PC3.toString());
        pannelloSinistra.add(labelGiocatore4);
        pannelloSinistra.add(Box.createHorizontalStrut(5));
        pannelloCartePc3Sinistra = new JPanel();
        pannelloCartePc3Sinistra.setLayout(new BoxLayout(pannelloCartePc3Sinistra, BoxLayout.Y_AXIS));
        pannelloCartePc3Sinistra.setOpaque(false);
        pannelloSinistra.add(pannelloCartePc3Sinistra);
        pannelloPrincipaleDelGioco.add(pannelloSinistra, BorderLayout.WEST);

        
        //pannello sotto con carte e label
        pannelloSotto = new JPanel();
        pannelloSotto.setOpaque(false);
        pannelloSotto.setLayout(new GridBagLayout());
        pannelloCarteGiocatoreSotto = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pannelloCarteGiocatoreSotto.setOpaque(false);
        labelGiocatore1 = new MioLabel("");
        pannelloPrincipaleDelGioco.add(pannelloSotto, BorderLayout.SOUTH);  
        GridBagConstraints gbcsotto = new GridBagConstraints();
        gbcsotto.gridx = 0;
        gbcsotto.gridy = 0;
        gbcsotto.anchor = GridBagConstraints.CENTER;
        pannelloSotto.add(labelGiocatore1, gbcsotto);
        gbcsotto.gridy = 1;
        gbcsotto.insets = new Insets(5, 0, 0, 0);
        pannelloSotto.add(pannelloCarteGiocatoreSotto, gbcsotto);
        
        
        //costruisco e aggiungo il banco al centro dove verrano messe le carte selezionate da giocatore e pc
        pannelloCentrale = new JPanel(new GridBagLayout());
        pannelloCentrale.setOpaque(false);
        pannelloCarteBancoInterno = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pannelloCarteBancoInterno.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        pannelloCentrale.add(pannelloCarteBancoInterno, gbc);
        pannelloPrincipaleDelGioco.add(pannelloCentrale, BorderLayout.CENTER);

        add(pannelloPrincipaleDelGioco,BorderLayout.CENTER);
        this.turnoDelGiocatore = false;
	}
	
	/**
	 * Disegna il pannello di gioco con lo sfondo(immagine banco da gioco).
	 * @param g contesto grafico
	 */
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (sfondo != null) {
            g.drawImage(sfondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
	
	/**
	 * Aggiorna l'aspetto del bottone della musica in base allo stato corrente.
	 */
	public void aggiornaStatoBottoneMusica() {
		boolean stato = GestoreAudio.getInstance().isMusicaAbilitata();
        bottoneMusica.setCliccato(stato);
	}
	
	/**
	 * Gestisce l'uscita forzata del giocatore dalla partita con conferma tramite un dialogo.
	 */
	private void uscitaForzataDalGiocatore() {
		
		int scelta = JOptionPane.showOptionDialog(
		        null,
		        "Sei sicuro di voler uscire?\nLa partita verrà considerata persa.",
		        "Conferma uscita",
		        JOptionPane.YES_NO_OPTION,
		        JOptionPane.WARNING_MESSAGE,
		        null,
		        new Object[] {"Sì", "No"},
		        "No"
		    );

		    if (scelta == JOptionPane.YES_OPTION) {
		        // Logica per gestire la sconfitta e chiudere la finestra
		        System.out.println("Partita persa. Uscita dal gioco.");
		        view.uscitaForzataDalGiocatore();
		    }
	}
	
	/**
	 * Gestisce gli aggiornamenti dal modello(Partita o giocatori).
	 * @param o oggetto osservato
	 * @param arg argomento dell'aggiornamento
	 */
    @Override
    public void update(Observable o, Object arg) {
    	if (!(o instanceof Model))
    	{
             return;
    	}

    	if(arg instanceof PartitaTressette){
    		gestisciNuovaPartita(arg);
    	}
    	else if (arg instanceof TipoGiocatore) {
    		gestisciCartaScelta(arg);
    	}
    }
    
	/**
	 * Gestisce l'inizio di una nuova partita.
	 * @param arg la partita PartitaTressette
	 */
    private void gestisciNuovaPartita(Object arg) {
    	//nuova partita
    	partitaInCorso = (PartitaTressette) arg;
    	
    	inizializzaNomiGiocatori();
        aggiornaLabelGiocatori("0","0");

    	iniziaPartita();
    }
    
	/**
	 * Inizializza i nomi e le Label dei giocatori in base al numero di giocatori nella partita.
	 */
    private void inizializzaNomiGiocatori() {
    	int numGiocatori = partitaInCorso.getNumeroGiocatori();
    	labelGiocatore1.setText(view.getPannelloAccount().getNickname());
    	
    	if(numGiocatori == 2) {
    		this.nomeGiocatori1 = view.getPannelloAccount().getNickname();
    		this.nomeGiocatori2 = TipoGiocatore.PC1.toString();
    		labelGiocatore1.setVisible(true);
    		labelGiocatore2.setVisible(true);
    		labelGiocatore3.setVisible(false);
    		labelGiocatore4.setVisible(false);
    	}
    	else if(numGiocatori == 3)
    	{
    		this.nomeGiocatori1 = view.getPannelloAccount().getNickname() +" + "+ TipoGiocatore.PC1.toString();
    		this.nomeGiocatori2 = TipoGiocatore.PC2.toString()+ " + morto";
    		labelGiocatore1.setVisible(true);
    		labelGiocatore2.setVisible(true);
    		labelGiocatore3.setVisible(true);
    		labelGiocatore4.setVisible(true);
    	}
    	else if(numGiocatori == 4)
    	{
    		this.nomeGiocatori1 = view.getPannelloAccount().getNickname() +" + "+ TipoGiocatore.PC1.toString();
    		this.nomeGiocatori2 = TipoGiocatore.PC2.toString() + " + " + TipoGiocatore.PC3.toString();
    		labelGiocatore1.setVisible(true);
    		labelGiocatore2.setVisible(true);
    		labelGiocatore3.setVisible(true);
    		labelGiocatore4.setVisible(true);
    	}
    }

	/**
	 * Aggiorna la Label del round corrente.
	 * @param round il round corrente
	 */
    private void aggiornaLabelRound(int round) {
    	labelRound.setText("Round: "+ round);
    }
    
	/**
	 * Aggiorna la Label dei punteggi dei giocatori.
	 * @param punteggio1 punteggio dell'utente o della squadra1(Utente + Pc1)
	 * @param punteggio2 punteggio del Pc1 o della squadra2(Pc2 + Pc3)
	 */
    private void aggiornaLabelGiocatori(String punteggio1, String punteggio2) {
    	label1Punti.setText(nomeGiocatori1 + ": "+ punteggio1);
    	label2Punti.setText(nomeGiocatori2 + ": "+ punteggio2);
    }
    
	/** 
	 * Gioca il turno del giocatore o del pc con un'attesa predefinita(1s) attraverso un Timer.
	*/
    private void giocaTurnoConAttesa() {
		new javax.swing.Timer(TEMPO_ATTESA_DEFAULT, e -> {
            ((javax.swing.Timer) e.getSource()).stop(); // ferma il timer dopo l'esecuzione
            giocaTurno();
        }).start();
    }
    
	/**
	 * Gestisce la carta giocata da un giocatore.
	 * Sulla base del tipo di giocatore, aggiorna le carte visualizzate e gestisce il flusso del gioco.
	 * @param arg il tipo di giocatore che ha giocato la carta (TipoGiocatore)
	 */
    private void gestisciCartaScelta(Object arg) {
    	TipoGiocatore giocatoreCheHaGiocatoLaCarta = (TipoGiocatore) arg;
    	
    	GestoreAudio.getInstance().playCarta();
    	if(giocatoreCheHaGiocatoLaCarta.equals(TipoGiocatore.UTENTE))
    	{
			turnoDelGiocatore = false; //il giocatore ha giocato
			aggiornaCarteUtente(partitaInCorso.getGiocatoreVero().getCarte());
			aggiornaCarteBanco(partitaInCorso.getCarteNelBanco());
	        
			if(partitaInCorso.isManoCompletata())
    		{
    			new javax.swing.Timer(TEMPO_ATTESA_DEFAULT, e -> {
    	            ((javax.swing.Timer) e.getSource()).stop(); // ferma il timer dopo l'esecuzione
        		    gestisciFineDellaMano();
    	        }).start();
    		}
			else
			{
				giocaTurnoConAttesa();
			}
    	}
    	else
    	{
    		List<Carta> carte = partitaInCorso.getPc(giocatoreCheHaGiocatoLaCarta).getCarte();
    		System.out.println("aggiorno le carte del "+ giocatoreCheHaGiocatoLaCarta);
			switch (giocatoreCheHaGiocatoLaCarta) {
				case PC1:
					aggiornaCartePc1(carte);
					break;
				case PC2:
					aggiornaCartePc2(carte);
					break;
				case PC3:
					aggiornaCartePc3(carte);
					break;
				default:
					throw new IllegalArgumentException("Tipo non gestito: " + giocatoreCheHaGiocatoLaCarta);
			}
			aggiornaCarteBanco(partitaInCorso.getCarteNelBanco());

    		if(partitaInCorso.isManoCompletata())
    		{
    			new javax.swing.Timer(TEMPO_ATTESA_DEFAULT, e -> {
    	            ((javax.swing.Timer) e.getSource()).stop(); // ferma il timer dopo l'esecuzione
        		    gestisciFineDellaMano();
    	        }).start();
    		}
    		else
    		{
    			if(!partitaInCorso.getTurnoGiocatore().equals(TipoGiocatore.UTENTE))
    			{
    				turnoDelGiocatore = false;
    			}
    			else
    			{
    				turnoDelGiocatore = true;
    			}
    			giocaTurnoConAttesa();
    		}
    	}
    	
    }

	/**
	 * Aggiorna le carte visualizzate per l'utente.
	 * Aggiorna il pannello delle carte del giocatore e aggiunge i listener per la selezione delle carte da parte dell'utente.
	 * @param carte Lista delle carte del giocatore utente.
	 */
    private void aggiornaCarteUtente(List<Carta> carte) {
        carteGiocatore.clear();
        pannelloCarteGiocatoreSotto.removeAll();
        
        int numCarte = Math.min(PartitaTressette.NUM_CARTE_PER_GIOCATORE, carte.size());
    	for (int i=0; i<numCarte; i++) {
    		CartaView cartaView = new CartaView(carte.get(i),false,false,false);
    		// Aggiungi un listener per il clic sulla carta
    		cartaView.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                	if(turnoDelGiocatore)
                	{
                		if(partitaInCorso.verificaCartaScelta(cartaView.getCarta()))
                		{
                			partitaInCorso.giocaCartaUtente(cartaView.getCarta());// Gioca la carta
                		}
                		else
                		{
                			cartaView.startShakeAnimation();
                			JOptionPane.showMessageDialog(
                		            null,                      
                		            "La carta selezionata non e' ammissibile. Scegliere una carta con lo stesso seme del palo",             
                		            "Carta invalida",           
                		            JOptionPane.INFORMATION_MESSAGE
                		        );
                		}
                	}
                };
                @Override
                public void mouseEntered(MouseEvent e) {
                	if(turnoDelGiocatore)
                	{
                		cartaView.setMouseOver(true);
                		cartaView.repaint();
                	}
                }

                @Override
                public void mouseExited(MouseEvent e) {
                	cartaView.setMouseOver(false);
            		cartaView.repaint();
                };

            });
    		carteGiocatore.add(cartaView);
    		pannelloCarteGiocatoreSotto.add(cartaView,cartaView.toString());
    	}
    	
		// Forza un repaint completo del contenitore principale per ripulire cose vecchie
    	pannelloCarteGiocatoreSotto.revalidate();
    	pannelloCarteGiocatoreSotto.repaint();
    	pannelloSotto.revalidate();
    	pannelloSotto.repaint();
		pannelloPrincipaleDelGioco.revalidate();
		pannelloPrincipaleDelGioco.repaint();
		//questo serve per ottenere la finestra che contiene questo pannello
		//faccio questa cosa per forzare il repaint del pannello dopo che ho aggiornato le carte nel pannello
		Window windowAncestor = SwingUtilities.getWindowAncestor(this);
		if (windowAncestor != null) {
			windowAncestor.validate();
			windowAncestor.repaint();
		}
    }
    
	/**
	 * Aggiorna il pannello delle carte da visualizzare nel banco.
	 * @param carte Lista delle carte da visualizzare nel banco.
	 */
    private void aggiornaCarteBanco(List<Carta> carte) {
    	carteBanco.clear();
    	pannelloCarteBancoInterno.removeAll();
    	
        if(carte != null) {
        	int numCarte = carte.size();
        	for (int i=0; i<numCarte; i++) {
        		CartaView cartaView = new CartaView(carte.get(i),false,false,false);
        		carteBanco.add(cartaView);
        		pannelloCarteBancoInterno.add(cartaView,cartaView.toString());
        	}
        }
        
		// Forza un repaint completo del contenitore principale per ripulire cose vecchie
    	pannelloCarteBancoInterno.revalidate();
    	pannelloCarteBancoInterno.repaint();
		// Forza repaint dell'area centrale
		pannelloCentrale.revalidate();
		pannelloCentrale.repaint();
		Window w2 = SwingUtilities.getWindowAncestor(this);
		if (w2 != null) {
			w2.validate();
			w2.repaint();
		}
    }
    
	/**
	 * Aggiorna le carte di tutti i giocatori in base al numero di giocatori nella partita.
	 */
    private void aggiornaCarteGiocatori() {
		aggiornaCarteUtente(partitaInCorso.getGiocatoreVero().getCarte());
    	if(partitaInCorso.getNumeroGiocatori() == 2)
    	{
    		//carte pc 1
    		aggiornaCartePc1(partitaInCorso.getPc(TipoGiocatore.PC1).getCarte());
    	}
    	else
    	{
    		//carte pc 1,2,3
    		aggiornaCartePc1(partitaInCorso.getPc(TipoGiocatore.PC1).getCarte());
    		aggiornaCartePc2(partitaInCorso.getPc(TipoGiocatore.PC2).getCarte());
    		aggiornaCartePc3(partitaInCorso.getPc(TipoGiocatore.PC3).getCarte());
    	}
    }
    
	/**
	 * Aggiorna il pannello delle carte del giocatore PC1.
	 * @param carte Lista delle carte del giocatore PC1.
	 */
    private void aggiornaCartePc1(List<Carta> carte) {
        cartePc1.clear();
        pannelloCartePc1Sopra.removeAll();
        boolean ridotta = true;
        if(partitaInCorso.getNumeroGiocatori()==2) {
        	ridotta = false;
        }
        int numCarte = Math.min(PartitaTressette.NUM_CARTE_PER_GIOCATORE, carte.size());
    	for (int i=0; i<numCarte; i++) {
    		CartaView cartaView = new CartaView(carte.get(i),true,false,ridotta);
    		cartePc1.add(cartaView);
    		pannelloCartePc1Sopra.add(cartaView,cartaView.toString());
    	}
    	
		// Forza un repaint completo del contenitore principale per ripulire cose vecchie
    	pannelloCartePc1Sopra.revalidate();
    	pannelloCartePc1Sopra.repaint();
    	pannelloSopra.revalidate();
    	pannelloSopra.repaint();
    	//questo serve per ottenere la finestra che contiene questo pannello
		//faccio questa cosa per forzare il repaint del pannello dopo che ho aggiornato le carte nel pannello
		Window windowAncestor = SwingUtilities.getWindowAncestor(this);
		if (windowAncestor != null) {
			windowAncestor.validate();
			windowAncestor.repaint();
		}
    }
    
	/**
	 * Aggiorna il pannello delle carte del giocatore PC2.
	 * @param carte Lista delle carte del giocatore PC2.
	 */
    private void aggiornaCartePc2(List<Carta> carte) {
        cartePc2.clear();
        pannelloCartePc2Destra.removeAll();
        
        int numCarte = Math.min(PartitaTressette.NUM_CARTE_PER_GIOCATORE, carte.size());
    	for (int i=0; i<numCarte; i++) {
    		CartaView cartaView = new CartaView(carte.get(i),true,true,true);
    		cartePc2.add(cartaView);
    		pannelloCartePc2Destra.add(cartaView,cartaView.toString());
			pannelloCartePc2Destra.add(Box.createVerticalStrut(5));
    	}
    	
		// Forza un repaint completo del contenitore principale per ripulire cose vecchie
    	pannelloCartePc2Destra.revalidate();
    	pannelloCartePc2Destra.repaint();
		pannelloDestra.revalidate();
		pannelloDestra.repaint();
		//questo serve per ottenere la finestra che contiene questo pannello
		//faccio questa cosa per forzare il repaint del pannello dopo che ho aggiornato le carte nel pannello
		Window windowAncestor = SwingUtilities.getWindowAncestor(this);
		if (windowAncestor != null) {
			windowAncestor.validate();
			windowAncestor.repaint();
		}
    }
    
	/**
	 * Aggiorna il pannello delle carte del giocatore PC3.
	 * @param carte Lista delle carte del giocatore PC3.
	 */
    private void aggiornaCartePc3(List<Carta> carte) {
        cartePc3.clear();
        pannelloCartePc3Sinistra.removeAll();
        
        int numCarte = Math.min(PartitaTressette.NUM_CARTE_PER_GIOCATORE, carte.size());
    	for (int i=0; i<numCarte; i++) {
    		CartaView cartaView = new CartaView(carte.get(i),true,true,true);
    		cartePc3.add(cartaView);
    		pannelloCartePc3Sinistra.add(cartaView,cartaView.toString());
			pannelloCartePc3Sinistra.add(Box.createVerticalStrut(5));
    	}
    	
		// Forza un repaint completo del contenitore principale per ripulire cose vecchie
		pannelloCartePc3Sinistra.revalidate();
		pannelloCartePc3Sinistra.repaint();
		pannelloSinistra.revalidate();
		pannelloSinistra.repaint();
		
		//questo serve per ottenere la finestra che contiene questo pannello
		//faccio questa cosa per forzare il repaint del pannello dopo che ho aggiornato le carte nel pannello
		Window windowAncestor = SwingUtilities.getWindowAncestor(this);
		if (windowAncestor != null) {
			windowAncestor.validate();
			windowAncestor.repaint();
		}
    }
    
	/**
	 * Resetta tutti i pannelli dei giocatori e le carte visualizzate nei pannelli di gioco.
	 */
    public void resetCartePannelloGioco() {
    	carteGiocatore.clear();
        pannelloCarteGiocatoreSotto.removeAll();
        
    	cartePc1.clear();
        pannelloCartePc1Sopra.removeAll();
        
        cartePc2.clear();
        pannelloCartePc2Destra.removeAll();
        
        cartePc3.clear();
        pannelloCartePc3Sinistra.removeAll();
        
        carteBanco.clear();
        pannelloCarteBancoInterno.removeAll();

        pannelloPrincipaleDelGioco.revalidate();
		pannelloPrincipaleDelGioco.repaint();
		
		//questo serve per ottenere la finestra che contiene questo pannello
		//faccio questa cosa per forzare il repaint del pannello dopo che ho aggiornato le carte nel pannello
		Window windowAncestor = SwingUtilities.getWindowAncestor(this);
		if (windowAncestor != null) {
			windowAncestor.validate();
			windowAncestor.repaint();
		}
    }
    
	/**
	 * Inizia la partita, resettando le carte e aggiornando i pannelli dei giocatori e del banco.
	 * Avvia il primo turno di gioco con un'attesa predefinita.
	 */
	private void iniziaPartita() {
		resetCartePannelloGioco();
		aggiornaCarteBanco(partitaInCorso.getCarteNelBanco());
		aggiornaCarteUtente(partitaInCorso.getGiocatoreVero().getCarte());
		aggiornaCarteGiocatori();
		System.out.println("Inizia il "+partitaInCorso.getTurnoGiocatore());

		giocaTurnoConAttesa();
	}

	/**
	 * Gestisce il turno di gioco.
	 * Per ogni turno, mostra un dialogo informativo sul giocatore che deve giocare il turno, 
	 * aggiorna il round e gestisce le accuse se abilitate in quel round.
	 * Ogni turno viene giocato dal giocatore o dal pc in base a quale giocatore deve giocare.
	 */
	private void giocaTurno() {
		TipoGiocatore turnoGiocatore = partitaInCorso.getTurnoGiocatore();
		
		System.out.println("Turno del giocatore" + turnoGiocatore);
		String giocatore = calcolaNomeGiocatore(partitaInCorso.getNumeroGiocatori(), turnoGiocatore);
		new DialogoInfoGioco(view, 250, 60, "Turno" , "Turno del giocatore " + giocatore, TEMPO_ATTESA_TRA_GIOCATORI);
		int roundAttuale = partitaInCorso.getRound();
		aggiornaLabelRound(roundAttuale);
		if(partitaInCorso.isAccusaAbilitata()) {
			gestioneAccuse(turnoGiocatore);
		}
		if(turnoGiocatore == TipoGiocatore.UTENTE) //sono il giocatore vero
		{
			turnoDelGiocatore = true;
		}
		else
		{
			turnoDelGiocatore = false;
			// Simula attesa di 1 secondo (1000 ms)
	        new javax.swing.Timer(TEMPO_ATTESA_DEFAULT, e -> {
	            ((javax.swing.Timer) e.getSource()).stop(); // ferma il timer dopo l'esecuzione
	            giocaTurnoPc(turnoGiocatore);
	        }).start();
		}
	}
	
	/**
	 * Calcola il nome del giocatore in base al tipo di giocatore e al numero di giocatori nella partita.
	 * @param numGiocatori numero di giocatori nella partita
	 * @param giocatore tipo di giocatore da calcolare(TipoGiocatore)
	 * @return
	 */
	private String calcolaNomeGiocatore(int numGiocatori, TipoGiocatore giocatore) {
		String risultato = giocatore.toString();
		if(giocatore.equals(TipoGiocatore.UTENTE)) {
			risultato = view.getPannelloAccount().getNickname();
		}
		if(giocatore.equals(TipoGiocatore.PC3) && numGiocatori == 3) {
			risultato = "Morto";
		}
		return risultato;
	}
	
	/**
	 * Gestisce le accuse durante il turno di un giocatore.
	 * Verifica se ci sono accuse da mostrare in base al numero di giocatori e al round attuale.
	 * Se ci sono accuse, mostra un dialogo per ogni accusa trovata e aggiorna la label dei punteggi dei giocatori.
	 * @param giocatore tipo di giocatore su cui verificare le accuse.
	 */
	private void gestioneAccuse(TipoGiocatore giocatore) {
    	int numGiocatori = partitaInCorso.getNumeroGiocatori();
		int roundAttuale = partitaInCorso.getRound();
		String nomeGiocatore = calcolaNomeGiocatore(numGiocatori,giocatore);

		if((numGiocatori==2 && roundAttuale <= 3) || 
		   ((numGiocatori==3 || numGiocatori==4) && roundAttuale == 1)	) {

			List<Pair<Accusa, List<Carta>>> accuse = partitaInCorso.verificaAccuse();

			System.out.println("Round: "+ roundAttuale+ "; " +accuse.size() + " accuse per il giocatore " + nomeGiocatore);
			for(Pair<Accusa, List<Carta>> a : accuse) {
				new DialogoAccuse(view, "Accuse", nomeGiocatore,a).setVisible(true);
			}
			String punteggio1 = formattaPunteggio(partitaInCorso.getPunteggioTotaleUtenteOCarteSquadra1());
			String punteggio2 = formattaPunteggio(partitaInCorso.getPunteggioTotalePc1OCarteSquadra2());	
	        aggiornaLabelGiocatori(punteggio1,punteggio2);
		}			
	}
	/**
	 * Gestisce la fine della mano di gioco.
	 * Aggiorna le carte dei giocatori, calcola e visualizza i punteggi, verifica se la partita è terminata
	 * e avvia il turno successivo o termina la partita in base alle condizioni di gioco.
	 */
	private void gestisciFineDellaMano() {		
		//True se partita a 3/4 giocatori o se tutte le carte sono state distribuite ai 2 giocatori
		//False se partita a 2 giocatori ma non tutte le carte sono state distribuite
		if(!partitaInCorso.completamentoManoDiGioco())
		{
			aggiornaCarteUtente(partitaInCorso.getGiocatoreVero().getCarte());
			aggiornaCartePc1(partitaInCorso.getPc(TipoGiocatore.PC1).getCarte());
		}
		partitaInCorso.resetPerManoSuccessiva();

		if(partitaInCorso.gestioneFineTurno())
		{	
			partitaInCorso.aggiornaPunteggio(true);

			System.out.println("Utente o Utente+pc: " + partitaInCorso.getPunteggioTotaleUtenteOCarteSquadra1());
			System.out.println("Pc1 o Pc2+Pc3: " + partitaInCorso.getPunteggioTotalePc1OCarteSquadra2());
			String punteggio1 = formattaPunteggio(partitaInCorso.getPunteggioTotaleUtenteOCarteSquadra1());
			String punteggio2 = formattaPunteggio(partitaInCorso.getPunteggioTotalePc1OCarteSquadra2());	
			
	        aggiornaLabelGiocatori(punteggio1,punteggio2);
			if(partitaInCorso.isPartitaTerminata())
			{
				aggiornaCarteBanco(null);
				new DialogoFinePartita(view, "Fine Partita",partitaInCorso.controlloVittoria(),(int)partitaInCorso.getPunteggioTotaleUtenteOCarteSquadra1()).setVisible(true);
				view.terminaParita();
			}
			else
			{
				carteBanco.clear();
		        pannelloCarteBancoInterno.removeAll();
				aggiornaCarteBanco(partitaInCorso.getCarteNelBanco());
				partitaInCorso.resetPerPartitaSuccessiva();
				new DialogoInfoGioco(view, 350,80,"Fine turno" , "Turno terminato. Creazie nuovo turno... ", TEMPO_ATTESA_FINE_TURNO);

				iniziaPartita();
			}
		}
		else
		{
			partitaInCorso.aggiornaPunteggio(false);
			System.out.println("Utente o Utente+pc: " + partitaInCorso.getPunteggioTotaleUtenteOCarteSquadra1());
			System.out.println("Pc1 o Pc2+Pc3: " + partitaInCorso.getPunteggioTotalePc1OCarteSquadra2());
			
	    	String nomeGiocatore = calcolaNomeGiocatore(partitaInCorso.getNumeroGiocatori(),partitaInCorso.getTurnoGiocatore());
			new DialogoInfoGioco(view, 250,60,"Presa" , nomeGiocatore + " prende la mano", TEMPO_ATTESA_TRA_GIOCATORI);

			String punteggio1 = formattaPunteggio(partitaInCorso.getPunteggioTotaleUtenteOCarteSquadra1());
			String punteggio2 = formattaPunteggio(partitaInCorso.getPunteggioTotalePc1OCarteSquadra2());	
			
	        aggiornaLabelGiocatori(punteggio1,punteggio2);
	        
	        carteBanco.clear();
	        pannelloCarteBancoInterno.removeAll();
			aggiornaCarteBanco(partitaInCorso.getCarteNelBanco());
			
			new javax.swing.Timer(TEMPO_ATTESA_DEFAULT, e -> {
	            ((javax.swing.Timer) e.getSource()).stop(); // ferma il timer dopo l'esecuzione
	            partitaInCorso.assegnaCartaDalMazzo();
				aggiornaCarteGiocatori();
		        giocaTurnoConAttesa();
	        }).start();
		}
	}

	/**
	 * Formatta il punteggio in modo che abbia al massimo una cifra decimale.
	 * @param valore il valore del punteggio da formattare.
	 * @return Stringa formattata del punteggio.
	 */
	private static String formattaPunteggio(double valore) {
        BigDecimal bd = new BigDecimal(valore).setScale(1, RoundingMode.HALF_UP);
        return bd.stripTrailingZeros().toPlainString(); // Rimuove lo .0 se non serve
    };
    
	/**
	 * Gioca il turno del PC nella partita in corso.
	 * @param turnoPc tipo di giocatore PC che deve giocare il turno.
	 */
	private void giocaTurnoPc(TipoGiocatore turnoPc) {
		partitaInCorso.giocaCartaPc(turnoPc);
	}
	
	/**
	 * Restituisce il bottone di uscita del pannello di gioco.
	 * @return bottone Esci
	 */
    public MioBottone getBottoneUscita() {
    	return bottoneExit;
    }
}
