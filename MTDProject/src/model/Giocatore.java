package model;

import java.util.ArrayList;
import java.util.List;
import carte.*;

public class Giocatore {
    private String nome;
    private List<Carta> carte;
    private boolean isIA;
    // altri attributi come punteggio, posizione, ecc.
    public Giocatore(String nome, boolean isIA) {
    	this.nome = nome;
    	this.isIA = isIA;
    	this.carte = new ArrayList<>();
    }
    
    public String getNome() {
    	return this.nome;
    }
    
    public void aggiungiCarte(List<Carta> nuoveCarte) {
        this.carte.addAll(nuoveCarte);
    }
    
    public List<Carta> getCarte(){
    	return carte;
    }
    
    public boolean getIsIA() {
    	return isIA;
    }
}

