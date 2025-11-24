package model.statements;

import exception.MyException;
import model.PrgState;
import model.expressions.IExp;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;
import model.values.StringValue;

public class WriteHeapStmt implements IStmt {
    String varName;
    IExp exp;

    public WriteHeapStmt(String varName, IExp exp)
    {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (!state.getSymTable().isDefined(varName)) {
            throw new MyException("Variable " + varName + " is not defined in symbol table");
        }
        IValue varValue = state.getSymTable().get(varName);
        if (!(varValue.getType() instanceof RefType)) {
            throw new MyException("Variable " + varName + " is not of RefType");
        }

        RefValue refValue = (RefValue) varValue;
        int address = refValue.getAddress();
        RefType refType = (RefType) varValue.getType();
        IType locationType = refType.getInner();

        if (!state.getHeap().isDefined(address)) {
            throw new MyException("The address " + address + " is not defined in the heap");
        }

        IValue expEval = exp.eval(state.getSymTable(), state.getHeap());
        if (!expEval.getType().equals(locationType)) {
            throw new MyException("Expression type " + expEval.getType().toString() + " does not match the location type " + locationType.toString());
        }

        state.getHeap().put(address, expEval);
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new WriteHeapStmt(varName, exp);
    }
}
