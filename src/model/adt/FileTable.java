package model.adt;

import model.values.StringValue;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class FileTable implements MyIDictionary<StringValue, BufferedReader>{
    private final Map<StringValue,BufferedReader> map;

    public FileTable(){
        this.map = new HashMap<>();
    }
    public FileTable(Map<StringValue,BufferedReader> map){
        this.map = map;
    }

    @Override
    public BufferedReader get(StringValue key) {
        return map.get(key);
    }

    @Override
    public void put(StringValue key, BufferedReader value) {
        map.put(key,value);
    }

    @Override
    public boolean isDefined(StringValue key) {
        return map.containsKey(key);
    }

    @Override
    public void remove(StringValue key) {
        map.remove(key);
    }

    @Override
    public String toStringFormatted() {
        String returnString = "";

        for (Map.Entry<StringValue, BufferedReader> entry : map.entrySet()) {//map.entrySet() is a function that returns a set of entries of the map , we iterate through it
            StringValue key = entry.getKey();
            BufferedReader value = entry.getValue();

            returnString = returnString + key.getValue() + "\n";
        }

        return returnString;
    }

    @Override
    public Map<StringValue, BufferedReader> getContent() {
        return map;
    }

    @Override
    public MyIDictionary<StringValue, BufferedReader> deepCopy() {
        return new FileTable(map);
    }
}
