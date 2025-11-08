package model.adt;

public interface MyIDictionary<K, V> {
    V get(K key);
    void put(K key, V value);
    boolean isDefined(K key);
    String toStringFormatted();
}