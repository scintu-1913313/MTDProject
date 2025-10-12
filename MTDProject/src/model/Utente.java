package model;

import java.util.ArrayList;
import java.util.List;

public class Utente {
	private AvatarEnum avatar;
	private String nickname;
    private int livello;
    private List<Partita> storicoPartite = new ArrayList<>();

    private static Utente istanza;    
    private void Utente() {
    	resetUtente();
    }
    
    public static Utente getIstance() {
    	if(istanza == null) {
    		istanza = new Utente();
    	}
    	return istanza;
    }
    
    public void resetUtente() {
    	this.avatar = AvatarEnum.DEFAULT;
    	this.nickname = "";
    	this.livello = 0;
    	storicoPartite.clear();
    }
    
    public boolean isRegistrato() {
        return !nickname.isEmpty();
    }
    
    public void setAvatar(AvatarEnum avatar) {
    	this.avatar = avatar;
    }
    
    public AvatarEnum getAvatarEnum() {
    	return this.avatar;
    }
    
    public void setNickname(String nickname) {
    	this.nickname = nickname;
    }
    
    public String getNickname() {
    	return this.nickname;
    }
    
    public void setLivello(int livello) {
    	this.livello = livello;
    }
    
    public int getLivello() {
    	return this.livello;
    }
    
    public void aggiungiPartita(Partita p) {
    	this.storicoPartite.add(p);
    }
    
    public void resetPartite() {
    	this.storicoPartite.clear();
    }
    
    public List<Partita> getPartite() {
    	return this.storicoPartite;
    }
}