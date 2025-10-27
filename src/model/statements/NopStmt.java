package model.statements;

import exception.MyException;
import model.PrgState;

public class NopStmt implements IStmt{
    @Override
    public PrgState execute(PrgState state) throws MyException {
        return state;
        //don't know where it would throw an exception
    }

    @Override
    public String toString(){
        return "NopStmt";
    }

    @Override
    public IStmt deepCopy() {
        IStmt newNopStmt = new NopStmt();
        return newNopStmt;
    }
}
