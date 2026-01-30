package examPartTwo;

import java.util.Map;

public interface MyILockTable {
    int getFreeValue();
    void put(int key, int value);
    int get(int key);
    boolean containsKey(int key);
    void update(int key, int value);
    void setContent(Map<Integer, Integer> content);
    Map<Integer, Integer> getContent();
    String toStringFormatted();
}