package model;

import java.time.LocalDateTime;

import org.json.JSONObject;

public class Partita {
	private boolean vinta;
    private int punteggioOttenuto;
	private LocalDateTime dataFine;
	
	public JSONObject toJSON() {
	    JSONObject obj = new JSONObject();
	    obj.put("vinta", vinta);
	    obj.put("punteggioOttenuto", punteggioOttenuto);
	    obj.put("dataFine", dataFine.toString()); // ISO format
	
	    return obj;
	}

	public Partita(boolean vinta,int punteggioOttenuto,LocalDateTime dataFine) {
		this.vinta = vinta;
		this.punteggioOttenuto = punteggioOttenuto;
		this.dataFine = dataFine;
	}
	
    public static Partita fromJSON(JSONObject obj) {
        
    	boolean vinta = obj.getBoolean("vinta");
    	int punteggioOttenuto = obj.getInt("punteggioOttenuto");
    	LocalDateTime dataFine = LocalDateTime.parse(obj.getString("dataFine"));
        return new Partita(vinta,punteggioOttenuto,dataFine);
    }
    
    @Override
    public String toString() {
    	return "Vinta: " + vinta + ", punteggio ottenuto: " + punteggioOttenuto + ", data Fine: " + dataFine +".";
    }
}
