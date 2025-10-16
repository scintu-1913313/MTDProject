package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;

public class Partita {
	private EsitoPartita esito;
    private int punteggioOttenuto;
	private LocalDateTime dataFine;
	
	public JSONObject toJSON() {
	    JSONObject obj = new JSONObject();
	    obj.put("esito", esito);
	    obj.put("punteggioOttenuto", punteggioOttenuto);
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	    String dataFormattata = dataFine.format(formatter);
	    obj.put("dataFine", dataFormattata); // ISO format
	
	    return obj;
	}

	public Partita(EsitoPartita esito,int punteggioOttenuto,LocalDateTime dataFine) {
		this.esito = esito;
		this.punteggioOttenuto = punteggioOttenuto;
		this.dataFine = dataFine;
	}
	
    public static Partita fromJSON(JSONObject obj) {
        
    	EsitoPartita esito = EsitoPartita.fromString(obj.getString("esito"));
    	int punteggioOttenuto = obj.getInt("punteggioOttenuto");
    	LocalDateTime dataFine = LocalDateTime.parse(obj.getString("dataFine"));
        return new Partita(esito,punteggioOttenuto,dataFine);
    }
    
    @Override
    public String toString() {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	    String dataFormattata = dataFine.format(formatter);
    	return "Esito: " + esito + "; Punteggio ottenuto: " + punteggioOttenuto + "; Data: " + dataFormattata +".";
    }
}
