package gui;

import javafx.beans.property.SimpleIntegerProperty;

public class LockTableEntry {
    private SimpleIntegerProperty location;
    private SimpleIntegerProperty value;

    public LockTableEntry(int location, int value) {
        this.location = new SimpleIntegerProperty(location);
        this.value = new SimpleIntegerProperty(value);
    }

    public int getLocation() { return location.get(); }
    public int getValue() { return value.get(); }
}