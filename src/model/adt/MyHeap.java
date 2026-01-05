package model.adt;

import model.values.IValue;

import java.util.HashMap;
import java.util.Map;


public class MyHeap implements MyIHeap{

    private final HashMap<Integer,IValue> heap;
    private Integer nextFreeAddress;

    public MyHeap(){
        heap = new HashMap<Integer,IValue>();
        nextFreeAddress = 1;
    }

    @Override
    public IValue get(Integer key) {
        return heap.get(key);
    }

    @Override
    public void put(Integer key, IValue iValue) {
        heap.put(key,iValue);
    }

    @Override
    public boolean isDefined(Integer key) {
        return heap.containsKey(key);
    }

    @Override
    public void remove(Integer key) {
        heap.remove(key);
    }

    @Override
    public String toStringFormatted() {
        String returnString = "";
        for (Integer key : heap.keySet()) {
            returnString += key + " -> " + heap.get(key) + "\n";
        }
        return returnString;
    }

    @Override
    public int allocate(IValue iValue) {
        int returnInt =nextFreeAddress;
        heap.put(nextFreeAddress++ , iValue);
        return returnInt;
    }

    @Override
    public void setContent(Map<Integer, IValue> map) {
        this.heap.clear();
        this.heap.putAll(map);
    }

    @Override
    public Map<Integer, IValue> getContent() {
        return heap;
    }
}