package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIStack;

public class CompStmt implements IStmt {
    private IStmt first;
    private IStmt snd;

    public CompStmt(IStmt first, IStmt snd) {
        this.first = first;
        this.snd = snd;
    }
    //:)
    public String toString() {
        return "(" + first.toString() + ";" + snd.toString() + ")";
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        stk.push(snd);
        stk.push(first);
        return state;
    }

    @Override
    public IStmt deepCopy() {
        CompStmt newCompStmt = new CompStmt(first.deepCopy(), snd.deepCopy());
        return newCompStmt;
    }
}