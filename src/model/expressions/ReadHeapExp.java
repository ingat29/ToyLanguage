package model.expressions;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class ReadHeapExp implements IExp{
    IExp exp;

    public ReadHeapExp(IExp exp){
        this.exp = exp;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> table , MyIHeap heap) throws MyException {
        IValue expValue = exp.eval(table,heap);

        if(!(expValue.getType() instanceof RefType)){
            throw new MyException("Expression is not a ref type");
        }

        RefValue refExpValue = (RefValue)expValue;
        int adresss = refExpValue.getAddress();

        if(!heap.isDefined(adresss)){
            throw new MyException("Address is not defined in heap");
        }

        return heap.get(adresss);
    }

    @Override
    public IExp deepCopy() {
        return new ReadHeapExp(this.exp);
    }
}
