package model.adt;

import model.values.IValue;

import java.util.Map;
import java.lang.Integer;

public interface MyIHeap{
    IValue get(Integer key);
    void put(Integer key , IValue value);
    boolean isDefined(Integer key);
    void remove(Integer key);
    String toStringFormatted();
    int allocate(IValue value);

    void SetContent(Map<Integer, IValue> map);
    Map<Integer, IValue> getContent();
}
