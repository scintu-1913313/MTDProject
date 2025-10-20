package model;

/**
 * Enum che rappresenta i possibili esiti di una partita con le loro rappresentazioni testuali.
 * Possibili esiti: VINTA, PERSA, PAREGGIATA.
 */
public enum EsitoPartita {

    /** Partita vinta dal giocatore */
    VINTA("Vinta"),

    /** Partita persa dal giocatore */
    PERSA("Persa"),

    /** Partita terminata in pareggio */
    PAREGGIATA("Pareggiata");

    /** Nome rappresentativo dell'esito della partita. */
    private final String nomeEsito;

    /**
     * Costruisce un esito della partita con il relativo nome.
     * @param nomeEsito Il nome dell'esito della partita
     */
    EsitoPartita(String nomeEsito) {
        this.nomeEsito = nomeEsito;
    }

    /**
     * Converte la stringa di un esito in EsitoPartita.
     * @param esito La stringa rappresentativa dell'esito
     * @return L' EsitoPartita corrispondente alla stringa
     */
    public static EsitoPartita fromString(String esito) {
        for (EsitoPartita s : EsitoPartita.values()) {
            if (s.nomeEsito.equalsIgnoreCase(esito)) {
                return s;
            }
        }
        throw new IllegalArgumentException("EsitoPartita non valido: " + esito);
    }
    
    /**
     * Restituisce la stringa rappresentativa dell'esito della partita.
     * @param esito L'esito della partita
     * @return La stringa associata all'esito tramite toString()
     */
    public static String getEsitoPartita(EsitoPartita esito) {
        return esito.toString();
    }

    /**
     * Restituisce la rappresentazione testuale dello esito della partita.
     * @return La stringa associata allo esito
     */
    @Override
    public String toString() {
        return nomeEsito;
    }
}
