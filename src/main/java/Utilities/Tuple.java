package Utilities;

public class Tuple<T, S>{
    private T first;
    private S second;

    public Tuple(T first, S second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() { return first; }

    public S getSecond() { return second; }

    @Override
    public String toString() { return "First: " + first.toString() + ", Second: " + second.toString(); }
}
