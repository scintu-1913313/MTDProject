package model;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class GestoreUtente {
	private final GestoreFile gestoreFile = GestoreFile.getGestoreFile();
	private final String percorsoFileUtente = "MTDProject/utente/fileInformazioniUtente.json"; //final perche' non deve essere modificato
	private Utente utente;
	
	public GestoreUtente() {
		utente = Utente.getIstance();
		utente.resetUtente();
		leggiDatiUtente();
	}
	
	public boolean getControlloUtenteRegistrato() {
		return utente.isRegistrato();
	}
	
	private void leggiDatiUtente() {
		try {
			JSONObject json = gestoreFile.leggiFileJSON(percorsoFileUtente);
			parsaUtenteDaJSON(json);
		} catch (IOException e) {
			System.out.println("impossibile leggere il file Json");
			utente.resetUtente(); // utente di default
		}
	}
	
	public void aggiornaDatiUtente(Partita p) {
		utente.aggiungiPartita(p);
		gestisciLivello(p);
		aggiornaDati();
	}
	
	public void aggiornaDatiUtente(AvatarEnum avatar,String nickname) {
		utente.setAvatar(avatar);
		utente.setNickname(nickname);
		aggiornaDati();
	}
	
	private void gestisciLivello(Partita p) {
		if(p.getEsito().equals(EsitoPartita.VINTA)) {
			int livelloAttuale = utente.getLivello();
			if(livelloAttuale < 100) //100 e' il livello massimo
			{
				livelloAttuale+=1;
			}
			utente.setLivello(livelloAttuale);
		}
	}
	
	public void aggiornaDati() {
        /*JSONObject datiAggiornati = new JSONObject();
        datiAggiornati.put("avatar", giocatore.getAvatarEnum());
        datiAggiornati.put("nickname", giocatore.getNickname());
        datiAggiornati.put("livello", giocatore.getLivello());*/
		
		Map<String, Object> datiOrdinati = new LinkedHashMap<>();
		datiOrdinati.put("avatar", utente.getAvatarEnum());
		datiOrdinati.put("nickname", utente.getNickname());
		datiOrdinati.put("livello", utente.getLivello());

        JSONArray partiteArray = new JSONArray();
        for (Partita p : utente.getPartite()) {
            partiteArray.put(p.toJSON());
        }
        datiOrdinati.put("storicoPartite", partiteArray);
        
		try {

			JSONObject datiAggiornati = new JSONObject(datiOrdinati);
			gestoreFile.scriviFileJSON(percorsoFileUtente,false,datiAggiornati);
		} catch (IOException e) {
			System.out.println("impossibile scrivere il file Json");
			utente.resetUtente(); // giocatore di default
		}

    }

	public void eliminaUtente() {
		utente.resetUtente();
		aggiornaDati();
	}

	//legge giocatore da JSON
    private void parsaUtenteDaJSON(JSONObject obj) {
    	utente.setNickname(obj.optString("nickname", ""));
    	utente.setLivello(obj.optInt("livello", 0));

        JSONArray partiteArray = obj.optJSONArray("storicoPartite");
        if (partiteArray != null) {
            for (int i = 0; i < partiteArray.length(); i++) {
                JSONObject partitaObj = partiteArray.getJSONObject(i);
                utente.aggiungiPartita(Partita.fromJSON(partitaObj));
            }
        }
        utente.setAvatar(AvatarEnum.fromString(obj.optString("avatar", "DEFAULT")));

    }
    
    public Utente getUtente() {
    	return this.utente;
    }
}
