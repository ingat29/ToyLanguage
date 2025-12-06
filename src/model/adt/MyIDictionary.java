package model.adt;

import java.util.Map;

public interface MyIDictionary<K, V> {
    V get(K key);
    void put(K key, V value);
    boolean isDefined(K key);
    void remove(K key);
    String toStringFormatted();
    Map<K, V> getContent();
    MyIDictionary<K,V> deepCopy();
}