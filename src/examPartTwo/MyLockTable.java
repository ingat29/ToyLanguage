package examPartTwo;

import java.util.HashMap;
import java.util.Map;

public class MyLockTable implements MyILockTable {
    private Map<Integer, Integer> lockTable;
    private int freeLocation = 0;

    public MyLockTable() {
        this.lockTable = new HashMap<>();
    }

    @Override
    public synchronized int getFreeValue() {
        freeLocation++;
        return freeLocation;
    }

    @Override
    public synchronized void put(int key, int value) {
        lockTable.put(key, value);
    }

    @Override
    public synchronized int get(int key) {
        return lockTable.get(key);
    }

    @Override
    public synchronized boolean containsKey(int key) {
        return lockTable.containsKey(key);
    }

    @Override
    public synchronized void update(int key, int value) {
        lockTable.put(key, value);
    }

    @Override
    public synchronized void setContent(Map<Integer, Integer> content) {
        this.lockTable = content;
    }

    @Override
    public synchronized Map<Integer, Integer> getContent() {
        return lockTable;
    }

    @Override
    public String toString() {
        return lockTable.toString();
    }

    @Override
    public String toStringFormatted() {
        String returnString = "";
        for (Integer key : lockTable.keySet()) {
            returnString += key + " -> " + lockTable.get(key) + "\n";
        }
        return returnString;
    }
}