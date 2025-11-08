package model.adt;

import java.util.ArrayList;
import java.util.List;

public class MyList<T>  implements MyIList<T> {
    private final List<T> list;

    public MyList() {
        this.list = new ArrayList<>();
    }

    @Override
    public void add(T value) {
        list.add(value);
    }

    @Override
    public String toStringFormatted() {
        String returnString = "";

        for(int i = 0 ; i < list.size(); i++) {
            String item = list.get(i).toString();
            returnString =returnString + item + '\n';
        }

        return returnString;
    }

    @Override
    public String toString() {
        return list.toString();
    }


}