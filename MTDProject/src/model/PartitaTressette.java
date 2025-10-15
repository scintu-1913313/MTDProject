package model;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import carte.*;
import java.util.Random;

public class PartitaTressette {

    private Model model;
    private Mazzo mazzo;

    public static final int NUM_CARTE_PER_GIOCATORE = 10;
    private final int numGiocatori;
    private final int punteggioStabilito;
    private final boolean accusa;
    private TipoGiocatore turnoGiocatore; //0,1,2,3
    private Carta cartaPalo;
    private Carta cartaGiocataDaUtente;
    private Carta cartaGiocataDaPc1;
    private Carta cartaGiocataDaPc2;
    private Carta cartaGiocataDaPc3;

    HashMap<TipoGiocatore, Giocatore> giocatori;

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
        this.giocatori = new HashMap<>();
        
        inizializzaGiocatori();
        assegnaCarte();
        
        // Nelle regole chi ha il 4 denara inizia
        // Se nessuno ha queste carte, inizia casualmente il giocatore
        this.turnoGiocatore = determinaPrimoGiocatore();
    }
    
    private void inizializzaGiocatori() {
    	
    	Utente u = model.getUtente();
    	Giocatore giocatoreUtente = new Giocatore(u.getNickname(),false);
    	giocatori.put(TipoGiocatore.UTENTE,giocatoreUtente);
    	
    	if(numGiocatori == 2)
    	{
        	Giocatore giocatorePc = new Giocatore(TipoGiocatore.PC1.toString(),true);
        	giocatori.put(TipoGiocatore.PC1,giocatorePc);
    	}
    	else if(numGiocatori == 3 || numGiocatori == 4)
    	{
			Giocatore giocatorePc1 = new Giocatore(TipoGiocatore.PC1.toString(),true);
        	giocatori.put(TipoGiocatore.PC1,giocatorePc1);
    		
			Giocatore giocatorePc2 = new Giocatore(TipoGiocatore.PC2.toString(),true);
        	giocatori.put(TipoGiocatore.PC2,giocatorePc2);
        	
			Giocatore giocatorePc3 = new Giocatore(TipoGiocatore.PC3.toString(),true);
        	giocatori.put(TipoGiocatore.PC3,giocatorePc3);
    	}
    }
    
    private void assegnaCarte() {

    	//int ripetizione = 1;
    	//if(numGiocatori == 2)
    	//{
    		//ripetizione = 2;
    	//}
    	List<Carta> carte= mazzo.getCarte();

    	//for(int y = 0; y < ripetizione; y++)
    	//{
	    	for(TipoGiocatore giocatore: giocatori.keySet())
	    	{
	    		List<Carta> carteGiocatore = new ArrayList<>();
	    		for(int i=0; i < NUM_CARTE_PER_GIOCATORE;i++)
	    		{
	    			carteGiocatore.add(carte.remove(0));
	    		}
	    		giocatori.get(giocatore).aggiungiCarte(carteGiocatore);
	    	}
    	//}
    }
    
    private TipoGiocatore determinaPrimoGiocatore() {
    	TipoGiocatore result = TipoGiocatore.UTENTE;

    	for(TipoGiocatore giocatore: giocatori.keySet())
    	{
    		List<Carta> carteGiocatore = giocatori.get(giocatore).getCarte();
    		for(int i=0; i < NUM_CARTE_PER_GIOCATORE;i++)
    		{
    			Carta carta = carteGiocatore.get(i);
    			if (carta.getSeme() == Seme.DENARI && carta.getValore() == Valore.QUATTRO) {
    				result = giocatore;
    				return result;
    			}
    		}
    	}
    	
    	return TipoGiocatore.PC1;
    }
    
    public Giocatore getGiocatoreVero() {
    	for(TipoGiocatore giocatore: giocatori.keySet()){
    		if(giocatori.get(giocatore).getIsIA()!=true) {
    			return giocatori.get(giocatore);
    		}
    	}
    	System.out.println("Giocatore vero inesistente");
    	return null;
    }
    
    public Giocatore getPc(TipoGiocatore giocatorePc) {
    	for(TipoGiocatore giocatore: giocatori.keySet()){
    		if(giocatore.equals(giocatorePc)) {
    			return giocatori.get(giocatore);
    		}
    	}
    	System.out.println("Giocatore " +giocatorePc + " inesistente");
    	return null;
    }
    
    public boolean verificaCartaScelta(Carta carta) {
        if (carta == null) {
        	System.out.println("Sono entranto nel verifica carta ma e' invalida");
            return false;
        }
        
        // Caso 1: cartaPalo è null → qualsiasi carta va bene
        if (cartaPalo == null) {
            return true;
        }

        // Caso 2: il seme della carta scelta è uguale al seme del palo → va bene
        if (carta.getSeme() == cartaPalo.getSeme()) {
            return true;
        }
        
        // Caso 3: il seme è diverso → va bene solo se il giocatore non ha carte del seme del palo
        List<Carta> carteDelGiocatore = giocatori.get(TipoGiocatore.UTENTE).getCarte();

        for (Carta c : carteDelGiocatore) {
            if (c.getSeme() == cartaPalo.getSeme()) {
                return false; // ha almeno una carta del seme richiesto → scelta non valida
            }
        }
        
        return true; // non ha carte del seme del palo → scelta valida
    }
    
    public void giocaCartaUtente(Carta carta) {
    	// Verifica che sia il turno del giocatore e che la carta sia valida
    	//se sono qui il giocatore ha giocato una carta buona(controllo sul seme che comanda gia' fatto)
        if (turnoGiocatore != TipoGiocatore.UTENTE || carta == null) {
        	System.out.println("Sono entranto nel gioca carta ma non e' il mio turno");
            return;
        }
        List<Carta> carteDelGiocatore = giocatori.get(TipoGiocatore.UTENTE).getCarte();

        if (!carteDelGiocatore.contains(carta)) {
        	System.out.println("Il giocatore ha giocato una carta che non ha");
            return;
        }
        
        System.out.println("Carta giocata dal giocatore utente: " + carta);
    	this.cartaGiocataDaUtente = carta;
    	carteDelGiocatore.remove(carta);
    	if(cartaPalo == null) //se la prima carta giocata e' quel del giocatore, allora la carta palo e' nulla e va settata 
    	{
    		cartaPalo = carta;
    	}
    	aggiornaTurnoGiocatore();
    	model.notificaAgliObserver(TipoGiocatore.UTENTE);
    }
    
    public void giocaCartaPc(TipoGiocatore turnoPc) {
        List<Carta> carteDelPc= giocatori.get(turnoPc).getCarte();
        Carta cartaScelta;

    	if(this.cartaPalo == null) //se la prima carta giocata e' quella del pc, allora la carta palo e' nulla e va settata(ne prendo una a caso, la prima 
    	{
    		cartaScelta = carteDelPc.get(0);
    		this.cartaPalo = cartaScelta;
    	}
    	else //ho gia' il palo, prendo la carta del seme piu' alto o altrimenti una a caso
    	{
	        // Filtra le carte con il seme desiderato
	        List<Carta> carteDelSeme = new ArrayList<>();
	        for (Carta c : carteDelPc) {
	            if (c.getSeme() == cartaPalo.getSeme()) {
	                carteDelSeme.add(c);
	            }
	        }

	        // Se non ci sono carte di quel seme, restituisci una a caso
	        if (carteDelSeme.isEmpty()) {
	        	cartaScelta = carteDelPc.isEmpty() ? null : carteDelPc.get(new Random().nextInt(carteDelPc.size()));
	        }

	        // Trova la carta con il valore massimo
	        Carta cartaMassima = carteDelSeme.get(0);
	        for (Carta c : carteDelSeme) {
	            if (c.getValore().ordinal() > cartaMassima.getValore().ordinal()) {
	                cartaMassima = c;
	            }
	        }
	        cartaScelta = cartaMassima;
    	}
        System.out.println("Carta giocata dal giocatore pc: " + cartaScelta);
        switch (turnoPc) {
	    	case PC1 -> this.cartaGiocataDaPc1 =  cartaScelta;
	    	case PC2 -> this.cartaGiocataDaPc2 =  cartaScelta;
	    	case PC3 -> this.cartaGiocataDaPc3 =  cartaScelta;
	    	default -> throw new IllegalArgumentException("Tipo non gestito: " + turnoPc);
		}
        carteDelPc.remove(cartaScelta);
        
	    // Prossimo turno
		aggiornaTurnoGiocatore();
    	model.notificaAgliObserver(turnoPc);
    }
    
    public void aggiornaTurnoGiocatore() {
    	if(numGiocatori == 2)
    	{
    		if(turnoGiocatore.equals(TipoGiocatore.PC1))
    		{
    			turnoGiocatore = TipoGiocatore.UTENTE;
    		}
    		else
    		{
    			turnoGiocatore = TipoGiocatore.PC1;
    		}
    	}
    	else if (numGiocatori == 3 || numGiocatori == 4)
    	{
    		switch (turnoGiocatore) {
		    	case UTENTE -> turnoGiocatore =  TipoGiocatore.PC2;
		    	case PC1 -> turnoGiocatore =  TipoGiocatore.PC3;
		    	case PC2 -> turnoGiocatore =  TipoGiocatore.PC1;
		    	case PC3 -> turnoGiocatore =  TipoGiocatore.UTENTE;
		    	default -> throw new IllegalArgumentException("Tipo non gestito: " + turnoGiocatore);
    		}
    	}
    }
    
    public boolean isManoCompletata() {
    	if(numGiocatori == 2) {
    		return cartaGiocataDaUtente != null && cartaGiocataDaPc1 != null;
    	}
    	else if (numGiocatori == 3 || numGiocatori == 4) {
    		return cartaGiocataDaUtente != null && cartaGiocataDaPc1 != null && cartaGiocataDaPc2 != null && cartaGiocataDaPc3 != null;
    	}
    	return true;
    }
    public int getNumeroGiocatori() {
    	return this.numGiocatori;
    }
    
    public TipoGiocatore getTurnoGiocatore() {
    	return this.turnoGiocatore;
    }
}
