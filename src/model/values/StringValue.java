package model.values;

import exception.MyException;
import model.types.IType;
import model.types.StringType;

public class StringValue implements IValue{
    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof StringValue){
            return value.equals(((StringValue)other).getValue());
        }
        else{
            return false;//maybe should throw an error ?
        }
    }

    @Override
    public IType getType() {
        return new StringType();
    }
}
