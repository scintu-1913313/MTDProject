package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import carte.Carta;
import model.Accusa;
import model.GestoreAudio;
import model.Model;
import model.Pair;
import model.PartitaTressette;
import model.TipoGiocatore;

public class PannelloGioco extends Pannello {
	private static int TEMPO_ATTESA_DEFAULT= 1000; //1000ms -> 1s
	private static int TEMPO_ATTESA_FINE_TURNO= 3000; //3000ms -> 3s
	private static int TEMPO_ATTESA_TRA_GIOCATORI= 2000; //2000ms -> 2s

	private View view;
	private JPanel pannelloPrincipaleDelGioco; 
	
	private JPanel pannelloInAlto;
	private MioBottone bottoneExit;
	private MioBottoneSelezione bottoneMusica;

	JLabel labelRound;
	
	JLabel label1Punti;
    JLabel label2Punti;
    String nomeGiocatori1;
    String nomeGiocatori2;

    JLabel labelGiocatore1;
    JLabel labelGiocatore2;
    JLabel labelGiocatore3;
    JLabel labelGiocatore4;

    //sotto(Giocatore)
	private JPanel pannelloSotto;
	private JPanel pannelloCarteGiocatoreSotto;
	
    //sopra
	private JPanel pannelloSopra;
	private JPanel pannelloCartePc1Sopra;
	
    //destra
	private JPanel pannelloDestra;
	private JPanel pannelloCartePc2Destra;
	
    //sinistra
	private JPanel pannelloSinistra;
	private JPanel pannelloCartePc3Sinistra;
	
	//centro
	private JPanel pannelloCentrale;
	private JPanel pannelloCarteBancoInterno;
	
    private List<CartaView> carteGiocatore;
    private List<CartaView> cartePc1;
    private List<CartaView> cartePc2;
    private List<CartaView> cartePc3;
    private List<CartaView> carteBanco;

	private PartitaTressette partitaInCorso;
	//private boolean cartaInGioco; //viene usata per vedere se e' il turno del giocatore e se puo selezionare la carta
	private boolean turnoDelGiocatore; //viene usata per vedere se e' il turno del giocatore e se puo selezionare la carta

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
        label1Punti = new JLabel("0");
        label2Punti = new JLabel("0");
        label1Punti.setFont(View.FONT_GIOCO);
        label1Punti.setForeground(Color.BLACK);
        label2Punti.setFont(View.FONT_GIOCO);
        label2Punti.setForeground(Color.BLACK);
        pannelloInAltroCentrale.add(label1Punti);
        pannelloInAltroCentrale.add(Box.createHorizontalStrut(30)); // Spazio tra le label
        pannelloInAltroCentrale.add(label2Punti);
        pannelloInAltroCentrale.add(Box.createHorizontalStrut(30)); // Spazio tra le label
        labelRound = new JLabel("Round: 1");
        labelRound.setFont(View.FONT_GIOCO);
        labelRound.setForeground(Color.BLACK);
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
        labelGiocatore2 = new JLabel(TipoGiocatore.PC1.toString());
        labelGiocatore2.setFont(View.FONT_GIOCO);
        labelGiocatore2.setForeground(Color.BLACK);
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
        labelGiocatore3 = new JLabel(TipoGiocatore.PC2.toString());
        labelGiocatore3.setFont(View.FONT_GIOCO);
        labelGiocatore3.setForeground(Color.BLACK);
        pannelloDestra.add(labelGiocatore3);
        pannelloPrincipaleDelGioco.add(pannelloDestra, BorderLayout.EAST);

        
        //pannello sinistra con carte e label.
        pannelloSinistra = new JPanel();
        pannelloSinistra.setOpaque(false);
        pannelloSinistra.setLayout(new BoxLayout(pannelloSinistra, BoxLayout.X_AXIS));
        
        labelGiocatore4 = new JLabel(TipoGiocatore.PC3.toString());
        labelGiocatore4.setFont(View.FONT_GIOCO);
        labelGiocatore4.setForeground(Color.BLACK);
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
        labelGiocatore1 = new JLabel("");
        labelGiocatore1.setFont(View.FONT_GIOCO);
        labelGiocatore1.setForeground(Color.BLACK);
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
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (sfondo != null) {
            g.drawImage(sfondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
	
	public void aggiornaStatoBottoneMusica() {
		boolean stato = GestoreAudio.getInstance().isMusicaAbilitata();
        bottoneMusica.setCliccato(stato);
	}
	
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
    
    private void gestisciNuovaPartita(Object arg) {
    	//nuova partita
    	partitaInCorso = (PartitaTressette) arg;
    	
    	inizializzaNomiGiocatori();
        aggiornaLabelGiocatori("0","0");

    	iniziaPartita();
    }
    
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

    private void aggiornaLabelRound(int round) {
    	labelRound.setText("Round: "+ round);
    }
    
    private void aggiornaLabelGiocatori(String punteggio1, String punteggio2) {
    	label1Punti.setText(nomeGiocatori1 + ": "+ punteggio1);
    	label2Punti.setText(nomeGiocatori2 + ": "+ punteggio2);
    }
    
    private void giocaTurnoConAttesa() {
		new javax.swing.Timer(TEMPO_ATTESA_DEFAULT, e -> {
            ((javax.swing.Timer) e.getSource()).stop(); // ferma il timer dopo l'esecuzione
            giocaTurno();
        }).start();
    }
    
    private void gestisciCartaScelta(Object arg) {
    	TipoGiocatore giocatoreCheHaGiocatoLaCarta = (TipoGiocatore) arg;
    	if(giocatoreCheHaGiocatoLaCarta.equals(TipoGiocatore.UTENTE))
    	{
			turnoDelGiocatore = false; //il giocatore ha giocato
			aggiornaCarteUtente(partitaInCorso.getGiocatoreVero().getCarte());
			aggiornaCarteBanco(partitaInCorso.getCarteNelBanco());
	        
			if(partitaInCorso.isManoCompletata())
    		{
    		    gestisciFineDellaMano();
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
	        	case PC1 -> aggiornaCartePc1(carte);
	        	case PC2 -> aggiornaCartePc2(carte);
	        	case PC3 -> aggiornaCartePc3(carte);
	        	default -> throw new IllegalArgumentException("Tipo non gestito: " + giocatoreCheHaGiocatoLaCarta);
			}
			aggiornaCarteBanco(partitaInCorso.getCarteNelBanco());

    		if(partitaInCorso.isManoCompletata())
    		{
    		    gestisciFineDellaMano();
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

    private void aggiornaCarteUtente(List<Carta> carte) {
        carteGiocatore.clear();
        pannelloCarteGiocatoreSotto.removeAll();
        
        int numCarte = Math.min(PartitaTressette.NUM_CARTE_PER_GIOCATORE, carte.size());
    	for (int i=0; i<numCarte; i++) {
    		CartaView cartaView = new CartaView(carte.get(i),false,false);
    		// Aggiungi un listener per il clic sulla carta
    		cartaView.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                	//TODO
                	if(turnoDelGiocatore)
                	{
                		if(partitaInCorso.verificaCartaScelta(cartaView.getCarta()))
                		{
                			partitaInCorso.giocaCartaUtente(cartaView.getCarta());// Gioca la carta
                		}
                		else
                		{
                			JOptionPane.showMessageDialog(
                		            null,                      
                		            "La carta selezionata non e' ammissibile. Scegliere una carta con lo stesso seme del palo",             
                		            "Carta invalida",           
                		            JOptionPane.INFORMATION_MESSAGE
                		        );
                		}
                	}
                };
            });
    		carteGiocatore.add(cartaView);
    		pannelloCarteGiocatoreSotto.add(cartaView,cartaView.toString());
    	}
    	
    	pannelloCarteGiocatoreSotto.revalidate();
    	pannelloCarteGiocatoreSotto.repaint();
    	pannelloSotto.revalidate();
    	pannelloSotto.repaint();
    }
    
    private void aggiornaCarteBanco(List<Carta> carte) {
    	carteBanco.clear();
    	pannelloCarteBancoInterno.removeAll();
    	
        if(carte != null) {
        	int numCarte = carte.size();
        	for (int i=0; i<numCarte; i++) {
        		CartaView cartaView = new CartaView(carte.get(i),false,false);
        		carteBanco.add(cartaView);
        		pannelloCarteBancoInterno.add(cartaView,cartaView.toString());
        	}
        }
        
    	pannelloCarteBancoInterno.revalidate();
    	pannelloCarteBancoInterno.repaint();
    }
    
    private void aggiornaCartePc1(List<Carta> carte) {
        cartePc1.clear();
        pannelloCartePc1Sopra.removeAll();
        
        int numCarte = Math.min(PartitaTressette.NUM_CARTE_PER_GIOCATORE, carte.size());
    	for (int i=0; i<numCarte; i++) {
    		CartaView cartaView = new CartaView(carte.get(i),true,false);
    		cartePc1.add(cartaView);
    		pannelloCartePc1Sopra.add(cartaView,cartaView.toString());
    	}
    	
    	pannelloCartePc1Sopra.revalidate();
    	pannelloCartePc1Sopra.repaint();
    	pannelloSopra.revalidate();
    	pannelloSopra.repaint();
    }
    
    private void aggiornaCartePc2(List<Carta> carte) {
        cartePc2.clear();
        pannelloCartePc2Destra.removeAll();
        
        int numCarte = Math.min(PartitaTressette.NUM_CARTE_PER_GIOCATORE, carte.size());
    	for (int i=0; i<numCarte; i++) {
    		CartaView cartaView = new CartaView(carte.get(i),true,true);
    		cartePc2.add(cartaView);
    		pannelloCartePc2Destra.add(cartaView,cartaView.toString());
    	}
    	pannelloCartePc2Destra.revalidate();
    	pannelloCartePc2Destra.repaint();
    }
    
    private void aggiornaCartePc3(List<Carta> carte) {
        cartePc3.clear();
        pannelloCartePc3Sinistra.removeAll();
        
        int numCarte = Math.min(PartitaTressette.NUM_CARTE_PER_GIOCATORE, carte.size());
    	for (int i=0; i<numCarte; i++) {
    		CartaView cartaView = new CartaView(carte.get(i),true,true);
    		cartePc3.add(cartaView);
    		pannelloCartePc3Sinistra.add(cartaView,cartaView.toString());
    	}
    	pannelloCartePc2Destra.revalidate();
    	pannelloCartePc2Destra.repaint();
    }
    
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
    }
    
	private void iniziaPartita() {
		resetCartePannelloGioco();
		aggiornaCarteBanco(partitaInCorso.getCarteNelBanco());
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
		System.out.println("Inizia il "+partitaInCorso.getTurnoGiocatore());

		giocaTurnoConAttesa();
	}

	private void giocaTurno() {
		TipoGiocatore turnoGiocatore = partitaInCorso.getTurnoGiocatore();
		
		System.out.println("Turno del giocatore" + turnoGiocatore);
		new DialogoInfoGioco(view, 250, 80, "Turno" , "Turno del giocatore " + turnoGiocatore, TEMPO_ATTESA_TRA_GIOCATORI);
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
	
	private void gestioneAccuse(TipoGiocatore giocatore) {
    	int numGiocatori = partitaInCorso.getNumeroGiocatori();
		int roundAttuale = partitaInCorso.getRound();
		String nomeGiocatore = giocatore.toString();
		if(giocatore.equals(TipoGiocatore.UTENTE)) {
			nomeGiocatore = view.getPannelloAccount().getNickname();
		}
		if(giocatore.equals(TipoGiocatore.PC3) && numGiocatori == 3) {
			nomeGiocatore = "Morto";
		}
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
				PannelloEsitoPartita.mostraEsito(this, "Franco", (int)partitaInCorso.getPunteggioTotaleUtenteOCarteSquadra1(), partitaInCorso.controlloVittoria());
				view.terminaParita();
			}
			else
			{
				partitaInCorso.resetPerPartitaSuccessiva();
				new DialogoInfoGioco(view, 300,80,"Fine turno" , "Turno terminato. Creazie nuovo turno... ", TEMPO_ATTESA_FINE_TURNO);

				iniziaPartita();
			}
		}
		else
		{
			partitaInCorso.aggiornaPunteggio(false);
			System.out.println("Utente o Utente+pc: " + partitaInCorso.getPunteggioTotaleUtenteOCarteSquadra1());
			System.out.println("Pc1 o Pc2+Pc3: " + partitaInCorso.getPunteggioTotalePc1OCarteSquadra2());
			
			String punteggio1 = formattaPunteggio(partitaInCorso.getPunteggioTotaleUtenteOCarteSquadra1());
			String punteggio2 = formattaPunteggio(partitaInCorso.getPunteggioTotalePc1OCarteSquadra2());	
			
	        aggiornaLabelGiocatori(punteggio1,punteggio2);

	        giocaTurnoConAttesa();
		}
	}

	private static String formattaPunteggio(double valore) {
        BigDecimal bd = new BigDecimal(valore).setScale(1, RoundingMode.HALF_UP);
        return bd.stripTrailingZeros().toPlainString(); // Rimuove lo .0 se non serve
    };
    
	private void giocaTurnoPc(TipoGiocatore turnoPc) {
		partitaInCorso.giocaCartaPc(turnoPc);
	}
	
    public MioBottone getBottoneUscita() {
    	return bottoneExit;
    }
}
