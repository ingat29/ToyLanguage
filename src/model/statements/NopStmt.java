package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.types.IType;

public class NopStmt implements IStmt{
    @Override
    public PrgState execute(PrgState state) throws MyException {
        return null;
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

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }
}
