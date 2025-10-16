package model;

public class Pair<T, U> {
    private final T first;
    private final U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Pair)) return false;
        Pair<?, ?> other = (Pair<?, ?>) obj;
        return first.equals(other.first) && second.equals(other.second);
    }

    @Override
    public int hashCode() {
        return 31 * first.hashCode() + second.hashCode();
    }
}
