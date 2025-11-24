package model.expressions;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.values.IValue;
import model.types.IntType;
import model.values.IntValue;

public class ArithmeticalExpression implements IExp{
    IExp exp1;
    IExp exp2;
    ArithmeticalOperation op;

    public ArithmeticalExpression(ArithmeticalOperation op,IExp exp1, IExp exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> table , MyIHeap heap) throws MyException {
        IValue val1 = exp1.eval(table , heap);
        IValue val2 = exp2.eval(table , heap);

        if(val1.getType().equals(new IntType()) &&  val2.getType().equals(new IntType())) {
            IntValue intVal1 = (IntValue) val1;
            IntValue intVal2 = (IntValue) val2;

            if (op.equals(ArithmeticalOperation.ADD)) {
                return new IntValue(intVal1.getVal() + intVal2.getVal());
            } else if (op.equals(ArithmeticalOperation.SUBTRACT)) {
                return new IntValue(intVal1.getVal() - intVal2.getVal());
            } else if (op.equals(ArithmeticalOperation.MULTIPLY)) {
                return new IntValue(intVal1.getVal() * intVal2.getVal());
            } else if(op.equals(ArithmeticalOperation.DIVIDE)) {
                if(intVal2.getVal() == 0) {
                    throw new MyException("Division by zero");
                }

                return new IntValue(intVal1.getVal() / intVal2.getVal());
            } else {
                throw new  MyException("Invalid operator");
            }
        }
        else{
            throw new MyException("At least one of the expressions is not an integer");
        }
    }

    @Override
    public IExp deepCopy() {
        ArithmeticalExpression newArithmExp = new ArithmeticalExpression(op,exp1.deepCopy(), exp2.deepCopy());
        return newArithmExp;
    }

    @Override
    public String toString(){

        return "Arithmetical Expression Exp1:"+ exp1.toString() + " Exp2:" + exp2.toString() + "Operation" + op.toString();
    }
}
