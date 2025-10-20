package model;

public enum EsitoPartita {

    VINTA("Vinta"),
    PERSA("Persa"),
    PAREGGIATA("Pareggiata");

    private final String nomeStato;

    /**
     * Costruisce un esito della partita con il relativo nome.
     * @param nomeStato Il nome dello stato della partita
     */
    EsitoPartita(String nomeStato) {
        this.nomeStato = nomeStato;
    }

    /**
     * Converte la stringa di un esito in EsitoPartita.
     * @param esito La stringa rappresentativa dell'esito
     * @return L' EsitoPartita corrispondente alla stringa
     */
    public static EsitoPartita fromString(String esito) {
        for (EsitoPartita s : EsitoPartita.values()) {
            if (s.nomeStato.equalsIgnoreCase(esito)) {
                return s;
            }
        }
        throw new IllegalArgumentException("EsitoPartita non valido: " + esito);
    }
    
    /**
     * Restituisce la stringa rappresentativa dello stato della partita.
     * @param stato Lo stato della partita
     * @return La stringa associata allo stato tramite toString()
     */
    public static String getStatoPartita(EsitoPartita stato) {
        return stato.toString();
    }

    /**
     * Restituisce la rappresentazione testuale dello stato della partita.
     * @return La stringa associata allo stato
     */
    @Override
    public String toString() {
        return nomeStato;
    }
}
