package model.expressions;

import exception.MyException;
import model.adt.MyIDictionary;
import model.values.IValue;

public class ValueExpression implements IExp {
    private IValue val;
    public ValueExpression(IValue val) {
        this.val = val;
    }

    public IValue eval(MyIDictionary<String,IValue> tbl) throws MyException {return val;}

    @Override
    public IExp deepCopy() {
        ValueExpression newVal = new ValueExpression(this.val);
        return newVal;
    }

    @Override
    public String toString() {
        return "ValueExpression{" +
                "val=" + val +
                '}';
    }
}