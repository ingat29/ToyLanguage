package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.*;
import model.statements.IStmt;
import model.types.IType;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.util.Map;

public class ForkStmt implements IStmt {
    private IStmt stmt;

    public ForkStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> newStack = new MyStack<>();
        MyIDictionary<String, IValue> newSymTable = state.getSymTable().deepCopy();

        MyIHeap sharedHeap = state.getHeap();
        MyIDictionary<StringValue, BufferedReader> sharedFileTable = state.getFileTable();
        MyIList<IValue> sharedOut = state.getOut();

        return new PrgState(newStack, newSymTable, sharedFileTable, sharedOut, stmt, sharedHeap);
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(stmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        stmt.typeCheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "ForkStmt(" + stmt.toString() + ")";
    }
}