package model;

import java.time.LocalDateTime;

import org.json.JSONObject;

public class Partita {
	private boolean vinta;
    private int punteggioOttenuto;
    private int punteggioStabilito;
	private LocalDateTime dataFine;
	
	public JSONObject toJSON() {
	    JSONObject obj = new JSONObject();
	    obj.put("vinta", vinta);
	    obj.put("punteggioOttenuto", punteggioOttenuto);
	    obj.put("punteggioStabilito", punteggioStabilito);
	    obj.put("dataFine", dataFine.toString()); // ISO format
	
	    return obj;
	}

    public static Partita fromJSON(JSONObject obj) {
        Partita p = new Partita();
        p.vinta = obj.getBoolean("vinta");
        p.punteggioOttenuto = obj.getInt("punteggioOttenuto");
        p.punteggioStabilito = obj.getInt("punteggioStabilito");
        p.dataFine = LocalDateTime.parse(obj.getString("dataFine"));

        return p;
    }
}
