package model;

import java.util.ArrayList;
import java.util.List;
import carte.*;
import java.util.Random;

public class PartitaTressette {

    private Model model;
    private Mazzo mazzo;

    public static final int NUM_CARTE_PER_GIOCATORE = 10;
    public final int numGiocatori;
    public final int punteggioStabilito;
    public final boolean accusa;
    private int turnoGiocatore; //0,1,2,3

    List<Giocatore> giocatori;

	/**
     * Costruttore di una partita del Gioco del Tressette
     * 
     * @param gioco L'istanza del modello cui la partita appartiene
     * @param mazzo Il mazzo di carte da utilizzare
     */
    public PartitaTressette(Model model, Mazzo mazzo, int numGiocatori, int punteggio, boolean accusa) {
    	this.model = model;
        this.mazzo = mazzo;
        this.punteggioStabilito = punteggio;
        this.numGiocatori = numGiocatori;
        this.accusa = accusa;
        this.giocatori = giocatori = new ArrayList<>();
        
        inizializzaGiocatori();
        assegnaCarte();
        
        // Nelle regole chi ha il 4 denara inizia
        // Se nessuno ha queste carte, inizia casualmente il giocatore
        this.turnoGiocatore = determinaPrimoGiocatore();
        System.out.println(turnoGiocatore);
    }
    
    private void inizializzaGiocatori() {
    	
    	Utente u = model.getUtente();
    	Giocatore giocatoreUtente = new Giocatore(u.getNickname(),false);
    	giocatori.add(giocatoreUtente);
    	
    	if(numGiocatori == 2)
    	{
        	Giocatore giocatorePc = new Giocatore("Pc1",true);
        	giocatori.add(giocatorePc);
    	}
    	else if(numGiocatori == 3 || numGiocatori == 4)
    	{
    		for(int i=0; i<3;i++)
    		{
    			String nome = "Pc" + (i+1);
    			Giocatore giocatorePc = new Giocatore(nome,true);
            	giocatori.add(giocatorePc);
    		}
    	}
    }
    
    private void assegnaCarte() {

    	int ripetizione = 1;
    	if(numGiocatori == 2)
    	{
    		ripetizione = 2;
    	}
    	List<Carta> carte= new ArrayList<>(mazzo.getCarte());

    	for(int y = 0; y < ripetizione; y++)
    	{
	    	for(Giocatore g: giocatori)
	    	{
	    		List<Carta> carteGiocatore = new ArrayList<>();
	    		for(int i=0; i < NUM_CARTE_PER_GIOCATORE;i++)
	    		{
	    			carteGiocatore.add(carte.remove(0));
	    		}
	    		g.aggiungiCarte(carteGiocatore);
	    	}
    	}
    }
    
    private int determinaPrimoGiocatore() {
    	int result = 0;
    	for(int idx=0; idx<giocatori.size(); idx++)
    	{
    		List<Carta> carteGiocatore = giocatori.get(idx).getCarte();
    		for(int i=0; i < NUM_CARTE_PER_GIOCATORE;i++)
    		{
    			Carta carta = carteGiocatore.get(i);
    			if (carta.getSeme() == Seme.DENARI && carta.getValore() == Valore.QUATTRO) {
    				result = idx;
    				return result;
    			}
    		}
    	}
    	
    	Random rand = new Random();
    	int min = 0;
    	int max = giocatori.size();
    	result = rand.nextInt((max - min) + 1) + min;
    	return result;
    }
}
