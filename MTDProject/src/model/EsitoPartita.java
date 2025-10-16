package model;

public enum EsitoPartita {

    VINTA("Vinta"),
    PERSA("Persa"),
    PAREGGIATA("Pareggiata");

    private final String nomeStato;

    EsitoPartita(String nomeStato) {
        this.nomeStato = nomeStato;
    }

    public static EsitoPartita fromString(String esito) {
        for (EsitoPartita s : EsitoPartita.values()) {
            if (s.nomeStato.equalsIgnoreCase(esito)) {
                return s;
            }
        }
        throw new IllegalArgumentException("EsitoPartita non valido: " + esito);
    }
    
    public static String getStatoPartita(EsitoPartita stato) {
        return stato.toString();
    }

    @Override
    public String toString() {
        return nomeStato;
    }
}
