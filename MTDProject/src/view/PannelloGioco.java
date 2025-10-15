package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import carte.Carta;
import carte.Mazzo;
import model.Model;
import model.PartitaTressette;
import model.TipoGiocatore;
import model.Utente;

public class PannelloGioco extends Pannello {
	
	private View view;
	private JPanel pannelloPrincipaleDelGioco; 
	
	private JPanel pannelloInAlto;
	private MioBottone bottoneExit;
	private MioBottone bottoneMusica;

	private JPanel pannelloCarteGiocatoreSotto;
	private JPanel pannelloCartePc1Sopra;
	private JPanel pannelloCartePc2Destra;
	private JPanel pannelloCartePc3Sinistra;
	private JPanel pannelloCarteBancoCentrale;


    private List<CartaView> carteGiocatore;
    private List<CartaView> cartePc1;
    private List<CartaView> cartePc2;
    private List<CartaView> cartePc3;

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
		
        
        //Io sono un BorderLayout. Aggiungo il bottone di uscita a nord
        pannelloInAlto = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pannelloInAlto.setOpaque(false);
        bottoneExit = new MioBottone("Esci");
        bottoneExit.setPreferredSize(new Dimension(50, 50));
        bottoneExit.setMaximumSize(new Dimension(50, 50));
        bottoneExit.setMinimumSize(new Dimension(50, 50));
        bottoneExit.setMargin(new Insets(10, 10, 10, 10));
        bottoneExit.addActionListener(e -> uscitaForzataDalGiocatore());
        pannelloInAlto.add(bottoneExit);
        add(pannelloInAlto, BorderLayout.NORTH);
        
        
        //costurisco e aggiungo i vari pannelli per le carte dei vari giocatori
        //metto tutto in un borderLayout che poi vado ad aggiungere al centro di me stesso\
        pannelloPrincipaleDelGioco = new JPanel(new BorderLayout());
        pannelloPrincipaleDelGioco.setOpaque(false);

        pannelloCartePc1Sopra = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pannelloCartePc1Sopra.setOpaque(false);
        pannelloPrincipaleDelGioco.add(pannelloCartePc1Sopra, BorderLayout.NORTH);

        pannelloCartePc2Destra = new JPanel();
        pannelloCartePc2Destra.setLayout(new BoxLayout(pannelloCartePc2Destra, BoxLayout.Y_AXIS));
        pannelloCartePc2Destra.setOpaque(false);
        pannelloPrincipaleDelGioco.add(pannelloCartePc2Destra, BorderLayout.EAST);

        pannelloCartePc3Sinistra = new JPanel();
        pannelloCartePc3Sinistra.setLayout(new BoxLayout(pannelloCartePc3Sinistra, BoxLayout.Y_AXIS));
        pannelloCartePc3Sinistra.setOpaque(false);
        pannelloPrincipaleDelGioco.add(pannelloCartePc3Sinistra, BorderLayout.WEST);

        pannelloCarteGiocatoreSotto = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pannelloCarteGiocatoreSotto.setOpaque(false);
        pannelloPrincipaleDelGioco.add(pannelloCarteGiocatoreSotto, BorderLayout.SOUTH);  
        
        
        //costruisco e aggiungo il banco al centro dove verrano messe le carte selezionate da giocatore e pc
        pannelloCarteBancoCentrale = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pannelloCarteBancoCentrale.setOpaque(false);
        pannelloPrincipaleDelGioco.add(pannelloCarteBancoCentrale, BorderLayout.CENTER);  

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
    	resetCartePannelloGioco();
    	
    	aggiornaCarteGiocatore(partitaInCorso.getGiocatoreVero().getCarte());
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
    	iniziaPartita();
    }
    
    private void gestisciCartaScelta(Object arg) {
    	TipoGiocatore giocatoreCheHaGiocatoLaCarta = (TipoGiocatore) arg;
    	if(giocatoreCheHaGiocatoLaCarta.equals(TipoGiocatore.UTENTE))
    	{
			turnoDelGiocatore = false; //il giocatore ha giocato
			aggiornaCarteGiocatore(partitaInCorso.getGiocatoreVero().getCarte());
			if(partitaInCorso.isManoCompletata())
    		{
    			
    		        JOptionPane.showMessageDialog(
    		            null,                      // finestra padre (null = centrato sullo schermo)
    		            "mano finita",                // testo del messaggio
    		            "Informazione",           // titolo della finestra
    		            JOptionPane.INFORMATION_MESSAGE // tipo di messaggio (icona info)
    		        );
    		}
			else
			{
				giocaTurno();
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
    		if(partitaInCorso.isManoCompletata())
    		{
    			
    		        JOptionPane.showMessageDialog(
    		            null,                      // finestra padre (null = centrato sullo schermo)
    		            "mano finita",                // testo del messaggio
    		            "Informazione",           // titolo della finestra
    		            JOptionPane.INFORMATION_MESSAGE // tipo di messaggio (icona info)
    		        );
    		}
    		else
    		{
    			if(!partitaInCorso.getTurnoGiocatore().equals(TipoGiocatore.UTENTE))
    			{
    				turnoDelGiocatore = false;
    				giocaTurno();
    			}
    			else
    			{
    				turnoDelGiocatore = true;
    			}
    		}
    	}
    	
    }

    private void aggiornaCarteGiocatore(List<Carta> carte) {
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
    }
    
	private void iniziaPartita() {
		System.out.println("Inizia il "+partitaInCorso.getTurnoGiocatore());
		giocaTurno();
	}

	private void giocaTurno() {
		TipoGiocatore turnoGiocatore = partitaInCorso.getTurnoGiocatore();
		System.out.println("Turno del giocatore" + turnoGiocatore);
		if(turnoGiocatore == TipoGiocatore.UTENTE) //sono il giocatore vero
		{
			turnoDelGiocatore = true;
		}
		else
		{
			turnoDelGiocatore = false;
			// Simula attesa di 1 secondo (1000 ms)
	        new javax.swing.Timer(1000, e -> {
	            ((javax.swing.Timer) e.getSource()).stop(); // ferma il timer dopo l'esecuzione
	            giocaTurnoPc(turnoGiocatore);
	        }).start();
		}
	}
	
	private void giocaTurnoPc(TipoGiocatore turnoPc) {
		partitaInCorso.giocaCartaPc(turnoPc);
//		gestisciCartaScelta(turnoPc);
	}

	
    public MioBottone getBottoneUscita() {
    	return bottoneExit;
    }
}
