package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expressions.IExp;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class NewStmt implements IStmt{
    private String varName;
    private IExp exp;

    public NewStmt(String varName, IExp exp){
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTbl = state.getSymTable();
        MyIHeap heap =  state.getHeap();

        if(!symTbl.isDefined(varName)){
            throw new MyException("Variable "+varName+" is not defined");
        }

        IValue varValue = symTbl.get(varName);
        if(!(varValue.getType() instanceof RefType)){
            throw new MyException("Variable "+varName+" is not of type RefType");
        }

        IValue evalValue = exp.eval(symTbl,heap);
        IType evalValueType = evalValue.getType();

        RefType varRefType = (RefType)varValue.getType();
        IType varLocationType = varRefType.getInner();

        if(!evalValueType.equals(varLocationType)){
            throw new MyException("Type mismatch: Variable " + varName + " points to " + varLocationType.toString() + " but expression evaluates to " + evalValue.getType().toString());
        }

        int newAdress = heap.allocate(evalValue);

        symTbl.put(varName, new RefValue(newAdress,varLocationType));

        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new NewStmt(varName, exp);
    }
}
