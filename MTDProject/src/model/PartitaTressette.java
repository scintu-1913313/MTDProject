package model;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import carte.*;
import carte.Mazzo.MazzoBuilder;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PartitaTressette {

    public static final int NUM_CARTE_PER_GIOCATORE = 10;
    private static final List<Integer> gerarchiaCarte = Arrays.asList(3, 2, 1, 10, 9, 8, 7, 6, 5, 4);
    
    private final Model model;
    private final Mazzo mazzo;

    private Mazzo mazzoInGioco;
    private final int numGiocatori;
    private final int punteggioStabilito;
    private final boolean accusa;
    private TipoGiocatore turnoGiocatore; //0,1,2,3
    private Carta cartaPalo;
    private Map<TipoGiocatore, Carta> carteManoDiGiocoOrdinate;
    private List<Carta> carteUtenteOCarteSquadra1; //Utente o Utente+Pc1
    private List<Carta> cartePc1OCarteSquadra2; //Pc1 o Pc2+Pc3
    private HashMap<TipoGiocatore, Giocatore> giocatori;
    private double punteggioAccuseTotaleUtenteOCarteSquadra1;
    private double punteggioAccuseTotalePc1OCarteSquadra2;
    private int numTurno;
    private int round;
    private Map<Integer, Pair<Double, Double>> puntiPerTurno;
    private List<Pair<Accusa, List<Carta>>> accuseTotaliTurno;

	/**
	 * Costruisce una nuova partita di Tressette e inizializza mazzo, giocatori e stato iniziale.
	 * @param model modello proprietario della partita(Model)
	 * @param mazzo mazzo iniziale costruito
	 * @param numGiocatori numero di giocatori (2-4)
	 * @param punteggio punteggio obiettivo(11,21,31,41)
	 * @param accusa true se abilitate le accuse
	 */
	public PartitaTressette(Model model, Mazzo mazzo, int numGiocatori, int punteggio, boolean accusa) {
    	this.model = model;
        this.mazzo = mazzo;
        this.punteggioStabilito = punteggio;
        this.numGiocatori = numGiocatori;
        this.accusa = accusa;
        this.numTurno = 0;
        this.puntiPerTurno = new HashMap<>();
        initPartita();
    }
    
	/**
	 * Inizializza i giocatori basandosi sul numero di partecipanti.
	 */
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
    
	/**
	 * Assegna 10 carte ad ogni giocatore all'inizio della partita.
	 */
    private void assegnaCarte() {

    	List<Carta> carte= mazzoInGioco.getCarte();

    	for(TipoGiocatore giocatore: giocatori.keySet())
    	{
    		List<Carta> carteGiocatore = new ArrayList<>();
    		for(int i=0; i < NUM_CARTE_PER_GIOCATORE;i++)
    		{
    			carteGiocatore.add(carte.remove(0));
    		}
    		giocatori.get(giocatore).aggiungiCarte(carteGiocatore);
    	}
    }
    
	/**
	 * Determina il primo giocatore in base alla presenza del 4 di denari o casualmente.
	 * @return il tipo di giocatore che inizia la partita.
	 */
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
    	
    	Random rand = new Random();
        int scelta = rand.nextInt(2); // 0 o 1
        return scelta == 0 ? TipoGiocatore.UTENTE : TipoGiocatore.PC1;
    }
    
	/**
	 * Restituisce il giocatore vero (non IA) se esistente.
	 * @return Giocatore vero(Utente) o null
	 */
	public Giocatore getGiocatoreVero() {
    	for(TipoGiocatore giocatore: giocatori.keySet()){
    		if(giocatori.get(giocatore).getIsIA()!=true) {
    			return giocatori.get(giocatore);
    		}
    	}
    	System.out.println("Giocatore vero inesistente");
    	return null;
    }
    
	/**
	 * Restituisce il Giocatore corrispondente a un TipoGiocatore PC (IA).
	 * @param giocatorePc tipo di giocatore PC richiesto.
	 * @return Giocatore PC corrispondente o null.
	 */
	public Giocatore getPc(TipoGiocatore giocatorePc) {
    	for(TipoGiocatore giocatore: giocatori.keySet()){
    		if(giocatore.equals(giocatorePc)) {
    			return giocatori.get(giocatore);
    		}
    	}
    	System.out.println("Giocatore " +giocatorePc + " inesistente");
    	return null;
    }
    
	/**
	 * Verifica se la carta scelta è valida rispetto al palo.
	 * Se il palo ancora non è stato stabilito, qualsiasi carta è valida.
	 * Altrimenti, la carta deve seguire il seme del palo se possibile.
	 * @param carta carta selezionata
	 * @return true se la scelta è valida
	 */
	public boolean verificaCartaScelta(Carta carta) {
        if (carta == null) {
        	System.out.println("Sono entranto nel verifica carta ma e' invalida");
            return false;
        }
        
        // Caso 1: cartaPalo è null -> qualsiasi carta va bene
        if (cartaPalo == null) {
            return true;
        }

        // Caso 2: il seme della carta scelta è uguale al seme del palo → va bene
        if (carta.getSeme() == cartaPalo.getSeme()) {
            return true;
        }
        
        // Caso 3: il seme è diverso -> va bene solo se il giocatore non ha carte del seme del palo
        List<Carta> carteDelGiocatore = giocatori.get(TipoGiocatore.UTENTE).getCarte();

        for (Carta c : carteDelGiocatore) {
            if (c.getSeme() == cartaPalo.getSeme()) {
                return false; // ha almeno una carta del seme richiesto → scelta non valida
            }
        }
        
        return true; // non ha carte del seme del palo -> scelta valida
    }
    
	/**
	 * Esegue la giocata di una carta da parte dell'utente, aggiornando stato e notificando le informazioni alla vista.
	 * Aggiorna anche la carta palo se è la prima carta giocata nella mano.
	 * @param carta carta giocata
	 */
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
    	carteManoDiGiocoOrdinate.put(TipoGiocatore.UTENTE, carta);
    	carteDelGiocatore.remove(carta);
    	if(cartaPalo == null) //se la prima carta giocata e' quel del giocatore, allora la carta palo e' nulla e va settata 
    	{
    		cartaPalo = carta;
    	}
    	aggiornaTurnoGiocatore();
    	model.notificaAgliObserver(TipoGiocatore.UTENTE);
    }
    
	/**
	 * Logica di gioco automatico per un PC: sceglie e gioca una carta.
	 * Aggiorna anche la carta palo se è la prima carta giocata nella mano.
	 * @param turnoPc TipoGiocatore del PC che deve giocare
	 */
	public void giocaCartaPc(TipoGiocatore turnoPc) {
        List<Carta> carteDelPc= giocatori.get(turnoPc).getCarte();
        Carta cartaScelta;
        if(carteDelPc.isEmpty())
        {
            System.out.println("Numero invalido di carte del " + turnoPc);
            return;
        }
    	if(this.cartaPalo == null) //se la prima carta giocata e' quella del pc, allora la carta palo e' nulla e va settata(ne prendo una a caso, la prima 
    	{            
    		cartaScelta = carteDelPc.get(0);
    		System.out.println("Carta palo Scelta " + cartaScelta);

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
	        	int idxCartaRandom = new Random().nextInt(carteDelPc.size());

	        	cartaScelta = carteDelPc.isEmpty() ? null : carteDelPc.get(idxCartaRandom);
	        }
	        else
	        {
		        // Trova la carta con il valore massimo
		        Carta cartaMassima = carteDelSeme.get(0);
		        for (Carta c : carteDelSeme) {
		            if (c.getValore().ordinal() > cartaMassima.getValore().ordinal()) {
		                cartaMassima = c;
		            }
		        }
		        cartaScelta = cartaMassima;
	        }
    	}
        System.out.println("Carta giocata dal giocatore pc: " + cartaScelta);
        switch (turnoPc) {
	    	case PC1 -> {
	    					this.carteManoDiGiocoOrdinate.put(TipoGiocatore.PC1, cartaScelta);
	    				}
	    	case PC2 -> {
	    					this.carteManoDiGiocoOrdinate.put(TipoGiocatore.PC2, cartaScelta);
	    				}
	    	case PC3 -> {
	    					this.carteManoDiGiocoOrdinate.put(TipoGiocatore.PC3, cartaScelta);
	    				}
	    	
	    	default -> throw new IllegalArgumentException("Tipo non gestito: " + turnoPc);
		}
        carteDelPc.remove(cartaScelta);
        
	    // Prossimo turno
		aggiornaTurnoGiocatore();
    	model.notificaAgliObserver(turnoPc);
    }
    
	/**
	 * Aggiorna il turno al giocatore successivo in base al numero di partecipanti.
	 * Tiene conto di come sono disposti i giocatori intorno al tavolo, giocando in senso antiorario.
	 */
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
    
	/**
	 * Controlla se tutti i giocatori hanno giocato nella mano corrente.
	 * @return true se la mano è completa
	 */
	public boolean isManoCompletata() {
    	if(numGiocatori == 2) {
    		Set<TipoGiocatore> attesi = Set.of(TipoGiocatore.PC1, TipoGiocatore.UTENTE);
    		Set<TipoGiocatore> presenti = carteManoDiGiocoOrdinate.keySet();
    		return presenti.equals(attesi);
    	}
    	else if (numGiocatori == 3 || numGiocatori == 4) {
    		Set<TipoGiocatore> attesi = Set.of(
    		        TipoGiocatore.PC1,
    		        TipoGiocatore.PC2,
    		        TipoGiocatore.PC3,
    		        TipoGiocatore.UTENTE
    		    );
		    Set<TipoGiocatore> presenti = carteManoDiGiocoOrdinate.keySet();
		    return presenti.equals(attesi);
    	}
    	return true;
    }
    
	/**
	 * Conclude la mano corrente determinando assegnando le carte al giocatore che ha vinto la mano 
	 * e aggiornando lo stato della partita.
	 * Il giocatore che vince la mano inizia la mano successiva.
	 * @return true se la partita è finita per il caso considerato
	 */
	public boolean completamentoManoDiGioco() {
		//qui vado a determinare chi prende le carte della mano di gioco
		//una volta trovato il giocatore che vince la mano, vengono inserite le carte nel mazzo personale di carte prese
		//lo stesso giocatore che ha vinto sara' il giocatore che inizia la mano successiva
		if (carteManoDiGiocoOrdinate == null){
            throw new IllegalArgumentException("Mappa invalida");
        }
		
		if((numGiocatori==2 && carteManoDiGiocoOrdinate.size() != 2) || (numGiocatori!=2 && carteManoDiGiocoOrdinate.size() != 4)){
            throw new IllegalArgumentException("Numero di carte non valido per i giocatori");
        }
		
		Iterator<Map.Entry<TipoGiocatore, Carta>> iterator = carteManoDiGiocoOrdinate.entrySet().iterator();
	    Map.Entry<TipoGiocatore, Carta> prima = iterator.next();
	    Carta semeComandante = prima.getValue();
	    TipoGiocatore vincitore = prima.getKey();
	    Carta cartaVincente = semeComandante;

	    while (iterator.hasNext()) {
	        Map.Entry<TipoGiocatore, Carta> entry = iterator.next();
	        Carta carta = entry.getValue();
	        if (carta.getSeme() == semeComandante.getSeme()) {
	            if (confrontaCarte(carta, cartaVincente) > 0) {
	                cartaVincente = carta;
	                vincitore = entry.getKey();
	            }
	        }
	    }
	    System.out.println("Ha vinto il giocatore "+vincitore);
	    if(this.numGiocatori == 2)
	    {
	    	switch (vincitore) {
		    	case UTENTE -> carteUtenteOCarteSquadra1.addAll(carteManoDiGiocoOrdinate.values());
		    	case PC1 -> cartePc1OCarteSquadra2.addAll(carteManoDiGiocoOrdinate.values());
		    	default -> throw new IllegalArgumentException("Tipo non gestito: " + turnoGiocatore);
			}
	    }
	    else
	    {
	    	switch (vincitore) {
		    	case UTENTE -> carteUtenteOCarteSquadra1.addAll(carteManoDiGiocoOrdinate.values());
		    	case PC1 -> carteUtenteOCarteSquadra1.addAll(carteManoDiGiocoOrdinate.values());
		    	case PC2 -> cartePc1OCarteSquadra2.addAll(carteManoDiGiocoOrdinate.values());
		    	case PC3 -> cartePc1OCarteSquadra2.addAll(carteManoDiGiocoOrdinate.values());
		    	default -> throw new IllegalArgumentException("Tipo non gestito: " + turnoGiocatore);
			}
	    }
	    
	    this.turnoGiocatore = vincitore;
	    this.round++;
	    if(numGiocatori == 2) {
	    	if(mazzoInGioco.getCarte().size()==0)
	    	{
	    		//tutte le carte assegnate
	    		return true;
	    	}
	    	return false;
	    }
	    else
	    {
	    	return true;
	    }
	}
	
	/**
	 * Confronta due carte in base alla gerarchia definita(regole dei punti del gioco) per determinare quale è più forte.
	 * @param c1 prima carta
	 * @param c2 seconda carta
	 * @return valore negativo se c1 è più forte, positivo se c2 è più forte, zero se uguali
	 */
	private static int confrontaCarte(Carta c1, Carta c2) {
		int p1 = gerarchiaCarte.indexOf(c1.getValore().getValoreNumerico());
	    int p2 = gerarchiaCarte.indexOf(c2.getValore().getValoreNumerico());
	    return Integer.compare(p2, p1); // indice più basso = carta più forte
	}
	
	/**
	 * Assegna carte dal mazzo ai giocatori nei match a 2 giocatori quando necessario.
	 */
	public void assegnaCartaDalMazzo() {
		//se non sono a due giocatori o se ho finito le carte, non devo assegnare nulla
		if(numGiocatori != 2 || mazzoInGioco.getCarte().size()==0) {
			return;
		}
		//se ho assegnato gia' tutte le carte mi fermo
		
		if(mazzoInGioco.getCarte().size() % 2 != 0) {
			System.out.println("Errore: nel mazzo c'e' un numero di carte dispari");
			return;
		}
		
    	List<Carta> carte= mazzoInGioco.getCarte();
    	List<Carta> carteUtente = new ArrayList<>();
    	List<Carta> cartePc = new ArrayList<>();
    	if(turnoGiocatore.equals(TipoGiocatore.UTENTE)) {
        	carteUtente.add(carte.remove(0));
        	cartePc.add(carte.remove(0));
    	}
    	else
    	{
        	cartePc.add(carte.remove(0));
        	carteUtente.add(carte.remove(0));
    	}


		giocatori.get(TipoGiocatore.UTENTE).aggiungiCarte(carteUtente);
		giocatori.get(TipoGiocatore.PC1).aggiungiCarte(cartePc);
		
	}
	
	/**
	 * Verifica le condizioni di fine di un turno all'interno di una partita.
	 * Controlla che tutte le carte siano state giocate e che il mazzo sia vuoto.
	 * @return true se il turno è correttamente concluso
	 */
	public boolean gestioneFineTurno() {
		//se il turno e' finito il numero di carte tra i due mazzi dei giocatori deve essere 40
		if(carteUtenteOCarteSquadra1.size() + cartePc1OCarteSquadra2.size() != 40)
		{
			return false;
		}
		
		if(mazzoInGioco.getCarte().size()!= 0)
		{
			System.out.println("Errore: nel mazzo ci sono ancora delle carte ma la partita e' finita");
			return false;
		}
		
		//controllo effettivamente che tutti i giocatori abbiano finito le carte
		if(numGiocatori == 2) {
			if(giocatori.get(TipoGiocatore.UTENTE).getCarte().size() != 0 ||
					giocatori.get(TipoGiocatore.PC1).getCarte().size() != 0 ) {
				System.out.println("Errore: uno dei due giocatori ha ancora delle carte ma la partita e' finita");
				return false;
			}
		}
		else
		{
			if( giocatori.get(TipoGiocatore.UTENTE).getCarte().size() != 0 ||
			    giocatori.get(TipoGiocatore.PC1).getCarte().size() != 0 ||
			    giocatori.get(TipoGiocatore.PC2).getCarte().size() != 0 ||
			    giocatori.get(TipoGiocatore.PC3).getCarte().size() != 0) {
				System.out.println("Errore: uno dei 4 giocatori ha ancora delle carte ma la partita e' finita");
				return false;
			}
		}
		
		//se sono qui la giocata della singola partia e' effetivamente finita
		return true;
	}
	
	/**
	 * Calcola e aggiorna i punteggi delle squadre considerando l'ultima presa.
	 * @param ultimaPresa true se assegnare il punto aggiuntivo dell'ultima presa.
	 */
	public void aggiornaPunteggio(boolean ultimaPresa) {
		boolean punteggioAggiuntivoSquadra1 = false;
		boolean punteggioAggiuntivoSquadra2 = false;
		//se sono all'ultima presa, devo dare un punto in piu' ai giocatori che hanno preso
		//in teoria l'ultimo giocatore che ha preso e' quello che giocherebbe il turno successivo
		if(ultimaPresa==true)
		{
			if(numGiocatori == 2) {
				punteggioAggiuntivoSquadra1 = turnoGiocatore == TipoGiocatore.UTENTE;
				punteggioAggiuntivoSquadra2 = turnoGiocatore == TipoGiocatore.PC1;
			}
			else
			{
				punteggioAggiuntivoSquadra1 = turnoGiocatore == TipoGiocatore.UTENTE || turnoGiocatore == TipoGiocatore.PC1;
				punteggioAggiuntivoSquadra2 = turnoGiocatore == TipoGiocatore.PC2 || turnoGiocatore == TipoGiocatore.PC3;

			}
		}
		double punteggioTotaleUtenteOCarteSquadra1 = calcolaPunteggio(carteUtenteOCarteSquadra1, punteggioAggiuntivoSquadra1);
		double punteggioTotalePc1OCarteSquadra2 = calcolaPunteggio(cartePc1OCarteSquadra2, punteggioAggiuntivoSquadra2);
		double punteggioFinale1 = punteggioTotaleUtenteOCarteSquadra1 + this.punteggioAccuseTotaleUtenteOCarteSquadra1;
		double punteggioFinale2 = punteggioTotalePc1OCarteSquadra2 + this.punteggioAccuseTotalePc1OCarteSquadra2;
        puntiPerTurno.put(numTurno, new Pair<>(punteggioFinale1, punteggioFinale2));
	}
	
	/**
	 * Calcola il punteggio totale(ad ogni mano) basato sulle carte prese e sull'ultima presa.
	 * Il punteggio viene calcolato sommando i valori delle carte secondo le regole del Tressette.
	 * Se ultimaPresa è true, viene aggiunto un punto extra.
	 * @param cartePrese lista delle carte prese
	 * @param ultimaPresa true se assegnare il punto aggiuntivo dell'ultima presa.
	 * @return punteggio calcolato
	 */
	private static double calcolaPunteggio(List<Carta> cartePrese, boolean ultimaPresa) {
        double puntiGrezzi = 0.0;

        for (Carta carta : cartePrese) {
            switch (carta.getValore()) {
                case ASSO:
                    puntiGrezzi += 1.0;
                    break;
                case TRE:
                case DUE:
                case RE:
                case CAVALLO:
                case FANTE:
                    puntiGrezzi += 1.0 / 3.0;
                    break;
                default:
                    // 7, 6, 5, 4 → 0 punti
                    break;
            }
        }

        if (ultimaPresa) {
            puntiGrezzi += 1.0;
        }

        return puntiGrezzi;
    }
	
	
	
	/**
	 * Verifica e restituisce la lista delle accuse valide per il giocatore corrente.
	 * @return lista di coppie (Accusa, liste di Carte) delle accuse rilevate valide
	 */
	public List<Pair<Accusa, List<Carta>>> verificaAccuse() {
		
		List<Carta> carte = giocatori.get(turnoGiocatore).getCarte();
		List<Pair<Accusa, List<Carta>>> accuse = new ArrayList<>();

	    // Mappa: seme -> carte con quel seme
	    Map<Seme, List<Carta>> cartePerSeme = carte.stream()
	        .collect(Collectors.groupingBy(Carta::getSeme));

	    // Verifica Napoli: asso, due, tre dello stesso seme
	    for (Map.Entry<Seme, List<Carta>> entry : cartePerSeme.entrySet()) {
	        Map<Valore, List<Carta>> perValore = entry.getValue().stream()
	            .filter(c -> c.getValore() == Valore.ASSO || c.getValore() == Valore.DUE || c.getValore() == Valore.TRE)
	            .collect(Collectors.groupingBy(Carta::getValore));

	        if (perValore.keySet().containsAll(Set.of(Valore.ASSO, Valore.DUE, Valore.TRE))) {
	            List<Carta> napoli = Stream.of(Valore.ASSO, Valore.DUE, Valore.TRE)
	                .map(perValore::get)
	                .flatMap(List::stream)
	                .collect(Collectors.toList());
	            accuse.add(new Pair<>(Accusa.NAPOLI, napoli));
	        }
	    }

	    // Mappa: valore -> carte con quel valore (solo asso, due, tre)
	    Map<Valore, List<Carta>> cartePerValore = carte.stream()
	            .filter(c -> c.getValore() == Valore.ASSO || c.getValore() == Valore.DUE || c.getValore() == Valore.TRE)
	            .collect(Collectors.groupingBy(Carta::getValore));

        for (Map.Entry<Valore, List<Carta>> entry : cartePerValore.entrySet()) {
            List<Carta> gruppo = entry.getValue();
            if (gruppo.size() == 4) {
                accuse.add(new Pair<>(Accusa.SUPERBUONGIOCO, gruppo));
            } else if (gruppo.size() == 3) {
                accuse.add(new Pair<>(Accusa.BUONGIOCO, gruppo));
            }
        }
	    
        // Filtro le accuse già dichiarate 
        List<Pair<Accusa, List<Carta>>> nuoveAccuse = accuse.stream()
            .filter(nuova -> !accuseTotaliTurno.contains(nuova))
            .collect(Collectors.toList());

        // Aaggiorno le accuseTotaliTurno
        accuseTotaliTurno.addAll(nuoveAccuse);
        
        if(nuoveAccuse.size()>0) {
	        List<Accusa> listaAccuse = nuoveAccuse.stream()
	        	    .map(Pair::getFirst)
	        	    .collect(Collectors.toList());
	        
	        aggiornaPunteggiConAccuse(listaAccuse);
        }
        return nuoveAccuse;    
	}

	/**
	 * Aggiorna i punteggi totali delle squadre in base alle accuse fatte nel turno corrente.
	 * @param listaAccuse lista delle accuse fatte
	 */
	private void aggiornaPunteggiConAccuse(List<Accusa> listaAccuse) {
		int val = 0;
		for (Accusa a: listaAccuse) {
			val += a.getValore();
		}
		double punteggioFinale1 = puntiPerTurno.get(numTurno).getFirst();
		double punteggioFinale2 = puntiPerTurno.get(numTurno).getSecond();
		if (numGiocatori == 2 )
		{
			if (turnoGiocatore.equals(TipoGiocatore.UTENTE))
			{
				punteggioAccuseTotaleUtenteOCarteSquadra1 += val;
				punteggioFinale1 += val;
			}
			else 
			{
				punteggioAccuseTotalePc1OCarteSquadra2 += val;
				punteggioFinale2 += val;
			}
		}
		else 
		{
			if (turnoGiocatore.equals(TipoGiocatore.UTENTE) || turnoGiocatore.equals(TipoGiocatore.PC1)) 
			{
				punteggioAccuseTotaleUtenteOCarteSquadra1 += val;
				punteggioFinale1 += val;
			}
			else
			{
				punteggioAccuseTotalePc1OCarteSquadra2 += val;
				punteggioFinale2 += val;
			}

		}
        puntiPerTurno.put(numTurno, new Pair<>(punteggioFinale1, punteggioFinale2));
	}

	/**
	 * Determina se la partita ha raggiunto il punteggio stabilito per concludersi.
	 * @return true se partita terminata
	 */
	public boolean isPartitaTerminata() {
		if (puntiPerTurno.size() < numTurno) {
		    System.out.println("La mappa è incompleta: mancano dati per alcuni turni.");
		    return false;
		}
		
		double somma1 = 0.0;
		double somma2 = 0.0;
		
	    for (Pair<Double, Double> coppia : puntiPerTurno.values()) {
	    	somma1 += coppia.getFirst();
	    	somma2 += coppia.getSecond();
	    }


		int sommaPuntiFinali1 = (int) somma1;
		int sommaPuntiFinali2 = (int) somma2;
		
	    return sommaPuntiFinali1 >= punteggioStabilito || sommaPuntiFinali2 >= punteggioStabilito;
	}
	
	/**
	 * Resetta il gioco per iniziare un nuovo turno della partita mantenendo le regole e le impostazioni.
	 * Vengono reinizializzati il mazzo e le carte dei giocatori per iniziare una nuovo turno.
	 * I valori di punteggio totale vengono mantenuti.
	 */
	public void resetPerPartitaSuccessiva() {
		initPartita();
	}
	
	/**
	 * Inizializza lo stato interno della partita: assegnazione mazzo, giocatori e gestione turno.
	 */
	private void initPartita() {
		this.mazzoInGioco = new Mazzo(mazzo);
		this.giocatori = new HashMap<>();
        this.punteggioAccuseTotaleUtenteOCarteSquadra1 = 0;
        this.punteggioAccuseTotalePc1OCarteSquadra2 = 0;
		inizializzaGiocatori();
        assegnaCarte();
        this.carteManoDiGiocoOrdinate = new LinkedHashMap<>();
        this.carteUtenteOCarteSquadra1 = new ArrayList<>();
        this.cartePc1OCarteSquadra2 = new ArrayList<>();
        this.accuseTotaliTurno = new ArrayList<>();
		this.cartaPalo = null;

        // Nelle regole chi ha il 4 denara inizia
        // Se nessuno ha queste carte, inizia casualmente il giocatore
        this.turnoGiocatore = determinaPrimoGiocatore();
        this.numTurno++;
        this.round = 1;
        puntiPerTurno.put(numTurno, new Pair<>(punteggioAccuseTotaleUtenteOCarteSquadra1, punteggioAccuseTotalePc1OCarteSquadra2));
	}
	
	/**
	 * Resetta lo stato specifico della mano (palo e banco) per la mano successiva.
	 */
	public void resetPerManoSuccessiva() {
		this.cartaPalo = null;
		this.carteManoDiGiocoOrdinate.clear();
	}
	
	/**
	 * Restituisce il numero di giocatori di questa partita.
	 * @return numero giocatori
	 */
	public int getNumeroGiocatori() {
		return this.numGiocatori;
	}
    
	/**
	 * Restituisce il TipoGiocatore del turno corrente.
	 * @return TipoGiocatore corrente
	 */
	public TipoGiocatore getTurnoGiocatore() {
		return this.turnoGiocatore;
	}
    
	/**
	 * Calcola il punteggio totale accumulato dalla squadra 1 (utente o utente+pc1).
	 * Il punteggio totale viene calcolato sommando i punteggi di ogni turno e include anche i punti delle accuse fatte.
	 * @return punteggio totale squadra 1(utente o utente+pc1)
	 */
	public double getPunteggioTotaleUtenteOCarteSquadra1(){
    	if (puntiPerTurno.size() < numTurno) {
		    System.out.println("La mappa è incompleta: mancano dati per alcuni turni.");
		    return 0.0; 
		}
		
		double somma1 = 0.0;
	    for (Pair<Double, Double> coppia : puntiPerTurno.values()) {
	    	somma1 += coppia.getFirst();
	    }
	    return somma1;
    }
    
	/**
	 * Calcola il punteggio totale accumulato dalla squadra 2 (Pc1 o Pc2+pc3).
	 * Il punteggio totale viene calcolato sommando i punteggi di ogni turno e include anche i punti delle accuse fatte.
	 * @return punteggio totale squadra 2(Pc1 o Pc2+pc3).
	 */
	public double getPunteggioTotalePc1OCarteSquadra2() {
    	if (puntiPerTurno.size() < numTurno) {
		    System.out.println("La mappa è incompleta: mancano dati per alcuni turni.");
		    return 0.0; 
		}
		
		double somma2 = 0.0;
		
	    for (Pair<Double, Double> coppia : puntiPerTurno.values()) {
	    	somma2 += coppia.getSecond();
	    }
	    
	    return somma2;
    }
    
	/**
	 * Determina l'esito(per l'utente) della partita confrontando i punteggi totali dei 2 giocatori/squadre.
	 * @return EsitoPartita risultante
	 */
	public EsitoPartita controlloVittoria() {
    	int punteggio1 = (int) getPunteggioTotaleUtenteOCarteSquadra1();
    	int punteggio2 = (int) getPunteggioTotalePc1OCarteSquadra2();
    	EsitoPartita esito = EsitoPartita.PAREGGIATA;
    	if(punteggio1 == punteggio2){
    		esito = EsitoPartita.PAREGGIATA;
    	}
    	else if(punteggio1 > punteggio2) {
    		esito = EsitoPartita.VINTA;
    	}
    	else {
    		esito = EsitoPartita.PERSA;
    	}
    	return esito;
    }
    
	/**
	 * Restituisce una copia delle carte attualmente nel banco (in tavola).
	 * @return lista di Carte nel banco
	 */
	public List<Carta> getCarteNelBanco(){
		return new ArrayList<>(carteManoDiGiocoOrdinate.values());
	}
    
	/**
	 * Indica se la modalità accuse è abilitata per questa partita.
	 * @return true se abilitata
	 */
	public boolean isAccusaAbilitata() {
		return this.accusa;
	}
    
	/**
	 * Restituisce il round corrente della partita.
	 * @return numero del round
	 */
	public int getRound() {
		return this.round;
	}
}
