package model.expressions;

import exception.MyException;
import model.adt.MyIDictionary;
import model.values.IValue;

public class VariableExpression implements IExp {
    private String id;
    public VariableExpression(String id) {
        this.id = id;
    }

    public IValue eval(MyIDictionary<String, IValue> tbl) throws MyException
    {return tbl.get(id);
    }

    @Override
    public IExp deepCopy() {
        VariableExpression newVarExp = new VariableExpression(this.id);
        return newVarExp;
    }

    @Override
    public String toString() {
        return "VariableExpression{" +
                "id='" + id + '\'' +
                '}';
    }
}