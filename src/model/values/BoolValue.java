package model.values;

import model.types.BoolType;
import model.types.IType;

public class BoolValue implements IValue
{
    private boolean val;
    public BoolValue(boolean v){val=v;}
    public boolean getVal() {return val;}
    public String toString()
    {
        return String.valueOf(val);
    }
    public IType getType() { return new BoolType();}
}