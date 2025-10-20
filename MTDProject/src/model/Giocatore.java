package model;

import java.util.ArrayList;
import java.util.List;
import carte.*;

public class Giocatore {
    private String nome;
    private List<Carta> carte;
    private boolean isIA;
    /**
     * Costruisce un giocatore con il nome e se e' un'IA, e inizializza la lista di carte.
     * @param nome nome del giocatore
     * @param isIA true se il giocatore e' controllato dalla IA(Pc)
     */
    public Giocatore(String nome, boolean isIA) {
    	this.nome = nome;
    	this.isIA = isIA;
    	this.carte = new ArrayList<>();
    }
    
    /**
     * Restituisce il nome del giocatore.
     * @return nome
     */
    public String getNome() {
    	return this.nome;
    }
    
    /**
     * Aggiunge carte alla lista delle carte del giocatore.
     * @param nuoveCarte lista di carte da aggiungere
     */
    public void aggiungiCarte(List<Carta> nuoveCarte) {
        this.carte.addAll(nuoveCarte);
    }
    
    /**
     * Restituisce la lista di carte attualmente in mano.
     * @return lista di Carte
     */
    public List<Carta> getCarte(){
    	return carte;
    }
    
    /**
     * Indica se questo giocatore è controllato dalla IA(Pc).
     * @return true se IA
     */
    public boolean getIsIA() {
    	return isIA;
    }
}

