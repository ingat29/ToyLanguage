package model.adt;

import java.util.Stack;

public interface MyIStack<T> {
    T pop();
    void push(T value);
    boolean isEmpty();
    String toStringFormatted();
}