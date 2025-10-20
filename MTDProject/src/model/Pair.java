package model;

/**
 * Utility generica che rappresenta una coppia immutabile di valori (first, second).
 * @param <T> tipo del primo elemento
 * @param <U> tipo del secondo elemento
 */
public class Pair<T, U> {
    /** Primo elemento della coppia. */
    private final T first;
    
    /** Secondo elemento della coppia. */
    private final U second;

    /**
     * Costruisce una coppia immutabile (pair) contenente due valori.
     * @param first primo elemento
     * @param second secondo elemento
     */
    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Restituisce il primo elemento della coppia.
     * @return primo elemento
     */
    public T getFirst() {
        return first;
    }

    /**
     * Restituisce il secondo elemento della coppia.
     * @return secondo elemento
     */
    public U getSecond() {
        return second;
    }

    /**
     * Rappresentazione testuale della coppia.
     * @return stringa formattata "(first, second)"
     */
    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    /**
     * Confronto strutturale basato sui due elementi.
     * @param obj oggetto da confrontare
     * @return true se entrambi gli elementi sono uguali
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Pair)) return false;
        Pair<?, ?> other = (Pair<?, ?>) obj;
        return first.equals(other.first) && second.equals(other.second);
    }

    /**
     * Hash coerente con equals.
     * @return codice hash della coppia
     */
    @Override
    public int hashCode() {
        return 31 * first.hashCode() + second.hashCode();
    }
}
