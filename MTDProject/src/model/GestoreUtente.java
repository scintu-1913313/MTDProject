package model;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Classe che gestisce i dati dell'utente.
 * Si occupa di gestire la serializzazione/deserializzazione e la persistenza dei dati attraverso un file JSON.
 * Gestisce inoltre lo storico delle partite, il livello dell'utente e l'aggiornamento e cancellazione dei dati
 */
public class GestoreUtente {
	/** Gestore file usato per lettura/scrittura JSON. */
	private final GestoreFile gestoreFile = GestoreFile.getGestoreFile();
	
	/** Percorso del file JSON utente (relativo al progetto). */
	private final String percorsoFileUtente = "MTDProject/utente/fileInformazioniUtente.json"; //final perche' non deve essere modificato
	
	/** Istanza Utente gestita. */
	private Utente utente;
	
	/**
	 * Costruttore che inizializza il gestore utente e carica i dati dal file.
	 */
	public GestoreUtente() {
		utente = Utente.getIstance();
		utente.resetUtente();
		leggiDatiUtente();
	}
	
	/**
	 * Indica se l'utente corrente è registrato.
	 * Viene controllato tramite il metodo isRegistrato() di Utente.
	 * @return true se l'utente e' registrato, false altrimenti
	 */
	public boolean getControlloUtenteRegistrato() {
		return utente.isRegistrato();
	}
	
	/**
	 * Tenta di leggere i dati utente dal file JSON; in caso di errore reimposta l'utente a default.
	 */
	private void leggiDatiUtente() {
		try {
			JSONObject json = gestoreFile.leggiFileJSON(percorsoFileUtente);
			parsaUtenteDaJSON(json);
		} catch (IOException e) {
			System.out.println("impossibile leggere il file Json");
			utente.resetUtente(); // utente di default
		}
	}
	
	/**
	 * Aggiunge una partita allo storico dell'utente, gestisce il livello e salva i dati su file.
	 * @param p partita da aggiungere
	 */
	public void aggiornaDatiUtente(Partita p) {
		utente.aggiungiPartita(p);
		gestisciLivello(p);
		aggiornaDati();
	}
	
	/**
	 * Aggiorna avatar e nickname dell'utente e salva i dati su file.
	 * @param avatar avatar scelto
	 * @param nickname nickname scelto
	 */
	public void aggiornaDatiUtente(AvatarEnum avatar,String nickname) {
		utente.setAvatar(avatar);
		utente.setNickname(nickname);
		aggiornaDati();
	}
	
	/**
	 * Incrementa il livello dell'utente in caso di vittoria (fino al massimo 100).
	 * Ogni vittoria aumenta il livello di 1, fino a un massimo di 100(livello massimo).
	 * @param p partita considerata
	 */
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
	
	/**
	 * Costrtuisce la struttura da inserire nel file JSON e salva i dati dell'utente su file.
	 */
	public void aggiornaDati() {
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

	/**
	 * Elimina i dati locali dell'utente (reset) e aggiorna il file.
	 * TODO
	 */
	public void eliminaUtente() {
		utente.resetUtente();
		aggiornaDati();
	}

	/**
	 * Parsa un JSONObject da cui estrarre i dati dell'utente registrato.
	 * @param obj JSONObject da cui estrarre i dati.
	 */
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
    
	/**
	 * Restituisce l'istanza Utente gestita.
	 * @return Utente corrente
	 */
	public Utente getUtente() {
		return this.utente;
	}
}
