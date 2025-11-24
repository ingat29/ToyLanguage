package model.expressions;

import com.sun.jdi.BooleanValue;
import com.sun.jdi.IntegerType;
import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.statements.IStmt;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;

public class RelationalExpression implements IExp {
    private IExp exp1;
    private IExp exp2;
    private RelationalOperation op;

    public RelationalExpression(IExp exp1, IExp exp2,RelationalOperation op) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> table , MyIHeap heap) throws MyException {
        IValue val1 = exp1.eval(table,heap);
        IValue val2 = exp2.eval(table,heap);

        if(val1.getType().equals(new IntType()) &&  val2.getType().equals(new IntType())) {
            int intval1 = ((IntValue)val1).getVal();
            int intval2 = ((IntValue)val2).getVal();

            switch (op) {
                case EQUAL:
                    return new BoolValue(intval1 == intval2);
                case NOT_EQUAL:
                    return new  BoolValue(intval1 != intval2);
                case GREATER_THAN:
                    return new  BoolValue(intval1 > intval2);
                case GREATER_THAN_OR_EQUAL:
                    return new  BoolValue(intval1 >= intval2);
                case LESS_THAN:
                    return new  BoolValue(intval1 < intval2);
                case LESS_THAN_OR_EQUAL:
                    return new  BoolValue(intval1 <= intval2);
            }

        }else throw new MyException("One of the operands is not an integer");

        return null;
    }

    @Override
    public String toString() {
        switch (op) {
            case EQUAL:
                return  exp1.toString() + " == " + exp2.toString();
            case NOT_EQUAL:
                return  exp1.toString() + " != " + exp2.toString();
            case GREATER_THAN:
                return  exp1.toString() + " > " + exp2.toString();
            case GREATER_THAN_OR_EQUAL:
                return  exp1.toString() + " >= " + exp2.toString();
            case LESS_THAN:
                return  exp1.toString() + " < " + exp2.toString();
            case LESS_THAN_OR_EQUAL:
                return  exp1.toString() + " <= " + exp2.toString();
            default:
                return "relExp";
        }
    }

    @Override
    public IExp deepCopy() {
        return new RelationalExpression(exp1.deepCopy(), exp2.deepCopy(), op);
    }
}
