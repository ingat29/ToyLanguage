package model.adt;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {
    private final Map<K, V> map;

    public MyDictionary() {
        this.map = new HashMap<>();
    }

    @Override
    public V get(K key) {
        return map.get(key);
    }

    @Override
    public void put(K key, V value) {
        map.put(key, value);
    }

    @Override
    public boolean isDefined(K key) {
        return map.containsKey(key);
    }

    @Override
    public void remove(K key) {
        map.remove(key);
    }

    @Override
    public String toStringFormatted() {
        String returnString = "";

        for (Map.Entry<K, V> entry : map.entrySet()) {//map.entrySet() is a function that returns a set of entries of the map , we iterate through it
            K key = entry.getKey();
            V value = entry.getValue();

            returnString = returnString + key + " ---> " + value + "\n";
        }

        return returnString;
    }

    @Override
    public String toString() {
        return map.toString();
    }
}