package model;

import java.util.Map;

public enum TipoGiocatore {
	UTENTE(0),
    PC1(1),
    PC2(2),
    PC3(3);
	
    private final int turno;
    
    public static final Map<TipoGiocatore, String> mappaToString = Map.of(
    		TipoGiocatore.UTENTE, "Utente",
    		TipoGiocatore.PC1, "Pc1",
    		TipoGiocatore.PC2, "Pc2",
    		TipoGiocatore.PC3, "Pc3"
	);
    
	/**
	 * Costruttore 
	 * @param turno. Il turno
	 */
	TipoGiocatore(int turno){
		this.turno = turno;
	}    
    
	public static TipoGiocatore fromId(int turno) {
        for (TipoGiocatore a : values()) {
            if (a.turno == turno) {
            	return a;
            }
        }
        return UTENTE;
    }
	
	/**
	 * 
	 * @param avatar.Il turno da convertire in stringa
	 * @return La stringa associata al turno tramite toString()
	 */
	public static String getTurno(TipoGiocatore turno) {
		return turno.toString();
	}
	
	/**
	 * @return Il nome in stringa del turno
	 */
	@Override
	public String toString() {
		return mappaToString.get(TipoGiocatore.fromId(turno));
	}
}
