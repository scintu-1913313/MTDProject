package model;

import java.util.ArrayList;
import java.util.List;

public class Utente {
	private AvatarEnum avatar;
	private String nickname;
    private int livello;
    private List<Partita> storicoPartite = new ArrayList<>();

    private static Utente istanza;    
    /**
     * Costruttore privato(Singleton Pattern) che inizializza l'utente allo stato di default.
     */
    private Utente() {
        resetUtente();
    }
    
    /**
     * Restituisce l'istanza singleton di Utente.
     * @return istanza unica di Utente.
     */
    public static Utente getIstance() {
        if(istanza == null) {
            istanza = new Utente();
        }
        return istanza;
    }
    
    /**
     * Reimposta l'utente ai valori predefiniti (avatar default, nickname vuoto, livello 0).
     * I valori di default identificano un utente non registrato.
     */
    public void resetUtente() {
    	this.avatar = AvatarEnum.DEFAULT;
    	this.nickname = "";
    	this.livello = 0;
    	storicoPartite.clear();
    }
    
    /**
     * Indica se l'utente è registrato (nickname non vuoto).
     * @return true se nickname non è vuoto
     */
    public boolean isRegistrato() {
        return !nickname.isEmpty();
    }
    
    /**
     * Imposta l'avatar dell'utente.
     * @param avatar enum AvatarEnum scelto
     */
    public void setAvatar(AvatarEnum avatar) {
    	this.avatar = avatar;
    }
    
    /**
     * Restituisce l'avatar selezionato.
     * @return AvatarEnum corrente
     */
    public AvatarEnum getAvatarEnum() {
    	return this.avatar;
    }
    
    /**
     * Imposta il nickname dell'utente.
     * @param nickname nuovo nickname
     */
    public void setNickname(String nickname) {
    	this.nickname = nickname;
    }
    
    /**
     * Restituisce il nickname corrente.
     * @return nickname
     */
    public String getNickname() {
    	return this.nickname;
    }
    
    /**
     * Imposta il livello dell'utente.
     * @param livello nuovo valore di livello
     */
    public void setLivello(int livello) {
    	this.livello = livello;
    }
    
    /**
     * Restituisce il livello corrente dell'utente.
     * @return livello
     */
    public int getLivello() {
    	return this.livello;
    }
    
    /**
     * Aggiunge una partita allo storico dell'utente.
     * @param p partita da aggiungere
     */
    public void aggiungiPartita(Partita p) {
    	this.storicoPartite.add(p);
    }
    
    /**
     * Pulisce lo storico delle partite.
     */
    public void resetPartite() {
    	this.storicoPartite.clear();
    }
    
    /**
     * Restituisce lo storico delle partite dell'utente.
     * @return lista di Partita
     */
    public List<Partita> getPartite() {
    	return this.storicoPartite;
    }
}