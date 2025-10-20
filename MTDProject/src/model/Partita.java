package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;

/**
 * Rappresenta una singola partita giocata e terminata con esito, punteggio ottenuto e data di fine.
 */
public class Partita {
	/** Esito della partita (VINTA/PERSA/PAREGGIATA). */
	private EsitoPartita esito;

	/** Punteggio ottenuto dal giocatore locale. */
    private int punteggioOttenuto;
	
	/** Data e ora di fine partita. */
	private LocalDateTime dataFine;
	
	/**
	 * Formatta e serializza questa Partita in un JSONObject con esito, punteggio e data formattata.
	 * @return JSONObject serializzato
	 */
	public JSONObject toJSON() {
	    JSONObject obj = new JSONObject();
	    obj.put("esito", esito);
	    obj.put("punteggioOttenuto", punteggioOttenuto);
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	    String dataFormattata = dataFine.format(formatter);
	    obj.put("dataFine", dataFormattata); // ISO format
	
	    return obj;
	}

	/**
	 * Costruisce una Partita con esito, punteggio e data di fine.
	 * @param esito esito della partita
	 * @param punteggioOttenuto punteggio ottenuto
	 * @param dataFine data e ora di fine
	 */
	public Partita(EsitoPartita esito,int punteggioOttenuto,LocalDateTime dataFine) {
		this.esito = esito;
		this.punteggioOttenuto = punteggioOttenuto;
		this.dataFine = dataFine;
	}
	
	/**
	 * Crea un'istanza Partita a partire da un JSONObject (parsing dei campi).
	 * @param obj JSONObject contenente i campi della partita
	 * @return nuova Partita deserializzata
	 */
	public static Partita fromJSON(JSONObject obj) {
        
    	EsitoPartita esito = EsitoPartita.fromString(obj.getString("esito"));
    	int punteggioOttenuto = obj.getInt("punteggioOttenuto");
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    	LocalDateTime dataFine = LocalDateTime.parse(obj.getString("dataFine"), formatter);
    	return new Partita(esito,punteggioOttenuto,dataFine);
    }
    
	/**
	 * Restituisce l'esito della partita.
	 * @return EsitoPartita corrente
	 */
	public EsitoPartita getEsito() {
		return this.esito;
	}
    
	/**
	 * Rappresentazione testuale compatta della partita (esito, punteggio e data).
	 * @return stringa descrittiva
	 */
	@Override
	public String toString() {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	    String dataFormattata = dataFine.format(formatter);
    	return "Esito: " + esito + "; Punteggio ottenuto: " + punteggioOttenuto + "; Data: " + dataFormattata +".";
    }
}
