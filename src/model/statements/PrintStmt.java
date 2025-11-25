package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIList;
import model.expressions.IExp;
import model.values.IValue;

public class PrintStmt implements IStmt{
    IExp exp;

    public PrintStmt(IExp exp){
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIList<IValue> tempOut = state.getOut();
        tempOut.add(exp.eval(state.getSymTable(),state.getHeap()));

        return state;
    }

    @Override
    public String toString(){
        return "PrintStmt : " + exp.toString();
    }

    @Override
    public IStmt deepCopy() {
        IStmt newPrintStmt = new PrintStmt(exp.deepCopy());
        return newPrintStmt;
    }
}
