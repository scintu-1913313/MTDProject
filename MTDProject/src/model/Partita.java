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

    public static Partita fromJSON(JSONObject obj) {
        Partita p = new Partita();
        p.vinta = obj.getBoolean("vinta");
        p.punteggioOttenuto = obj.getInt("punteggioOttenuto");
        p.dataFine = LocalDateTime.parse(obj.getString("dataFine"));

        return p;
    }
    
    @Override
    public String toString() {
    	return "Vinta: " + vinta + ", punteggio ottenuto: " + punteggioOttenuto + ", data Fine: " + dataFine +".";
    }
}
