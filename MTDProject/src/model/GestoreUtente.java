package model;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class GestoreUtente {
	private final GestoreFile gestoreFile = GestoreFile.getGestoreFile();
	private final String percorsoFileUtente = "MTDProject/utente/fileInformazioniUtente.json"; //final perche' non deve essere modificato
	private Giocatore giocatore;
	
	public GestoreUtente() {
		giocatore = Giocatore.getIstance();
		giocatore.resetGiocatore();
		leggiDatiUtente();
	}
	
	public boolean getControlloUtenteRegistrato() {
		return giocatore.isRegistrato();
	}
	
	private void leggiDatiUtente() {
		try {
			JSONObject json = gestoreFile.leggiFileJSON(percorsoFileUtente);
			parsaGiocatoreDaJSON(json);
		} catch (IOException e) {
			System.out.println("impossibile leggere il file Json");
			giocatore.resetGiocatore(); // giocatore di default
		}
	}
	
	private void aggiornaDatiUtente(Partita p) {
		giocatore.aggiungiPartita(p);
		aggiornaDati();
	}
	
	public void aggiornaDatiUtente(AvatarEnum avatar,String nickname) {
		giocatore.setAvatar(avatar);
		giocatore.setNickname(nickname);
		aggiornaDati();
	}
	
	private void aggiornaDatiUtente(int livello) {
		giocatore.setLivello(livello);
		aggiornaDati();
	}
	
	public void aggiornaDati() {
        /*JSONObject datiAggiornati = new JSONObject();
        datiAggiornati.put("avatar", giocatore.getAvatarEnum());
        datiAggiornati.put("nickname", giocatore.getNickname());
        datiAggiornati.put("livello", giocatore.getLivello());*/
		
		Map<String, Object> datiOrdinati = new LinkedHashMap<>();
		datiOrdinati.put("avatar", giocatore.getAvatarEnum());
		datiOrdinati.put("nickname", giocatore.getNickname());
		datiOrdinati.put("livello", giocatore.getLivello());

        JSONArray partiteArray = new JSONArray();
        for (Partita p : giocatore.getPartite()) {
            partiteArray.put(p.toJSON());
        }
        datiOrdinati.put("storicoPartite", partiteArray);
        
		try {

			JSONObject datiAggiornati = new JSONObject(datiOrdinati);
			gestoreFile.scriviFileJSON(percorsoFileUtente,false,datiAggiornati);
		} catch (IOException e) {
			System.out.println("impossibile scrivere il file Json");
			giocatore.resetGiocatore(); // giocatore di default
		}

    }

	public void eliminaUtente() {
		giocatore.resetGiocatore();
		aggiornaDati();
	}

	//legge giocatore da JSON
    private void parsaGiocatoreDaJSON(JSONObject obj) {
    	giocatore.setNickname(obj.optString("nickname", ""));
    	giocatore.setLivello(obj.optInt("livello", 0));

        JSONArray partiteArray = obj.optJSONArray("storicoPartite");
        if (partiteArray != null) {
            for (int i = 0; i < partiteArray.length(); i++) {
                JSONObject partitaObj = partiteArray.getJSONObject(i);
                giocatore.aggiungiPartita(Partita.fromJSON(partitaObj));
            }
        }
    	giocatore.setAvatar(AvatarEnum.fromString(obj.optString("avatar", "DEFAULT")));

    }
    
    public Giocatore getGiocatore() {
    	return this.giocatore;
    }
}
