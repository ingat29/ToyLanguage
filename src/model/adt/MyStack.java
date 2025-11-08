package model.adt;

import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    private Stack<T> stack;

    public MyStack() {
        stack = new Stack<>();
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T value) {
        stack.push(value);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toStringFormatted() {
        String returnString = "";

        Stack<T> auxStack = new Stack<>();
        auxStack.addAll(stack);


        while (!auxStack.isEmpty()) {
            T item = auxStack.pop();
            returnString += item.toString() + "\n";
        }

        return returnString;
    }

    @Override
    public String toString() {
        Stack stack2 = new Stack<>();
        stack2.addAll(stack);
        Stack stack3 = new Stack();
        while (!stack2.isEmpty()) {
            stack3.add(stack2.pop());
        }
        return stack3.toString();
    }
}