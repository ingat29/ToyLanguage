package model;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIList;
import model.adt.MyIStack;
import model.statements.IStmt;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, IValue> symTable;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private MyIList<IValue> out;
    private MyIHeap heap;
    private IStmt originalProgram; //optional field, but good to have
    private static int lastId = 0;
    private int id;

    public static synchronized int getNewPrgStateId() {
        return ++lastId;
    }

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, IValue> symtbl,MyIDictionary<StringValue, BufferedReader> fileTable, MyIList<IValue> ot, IStmt prg ,MyIHeap heap) {
        this.exeStack = stk;
        this.symTable = symtbl;
        this.fileTable = fileTable;
        this.out = ot;
        this.originalProgram = prg;
        this.heap = heap;
        this.id = PrgState.getNewPrgStateId();

        if (prg != null) {
            stk.push(prg);
        }
    }

    private int getId() {
        return id;
    }

    public MyIList<IValue> getOut() {
        return out;
    }

    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {return fileTable;}

    public MyIHeap getHeap() {return heap;}

    public void setFileTable(MyIDictionary<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public void setSymTable(MyIDictionary<String, IValue> symTable) {
        this.symTable = symTable;
    }

    public void setOut(MyIList<IValue> out) {
        this.out = out;
    }

    public void setHeap(MyIHeap heap) {
        this.heap = heap;
    }

    public boolean isNotCompleted(){
        if(exeStack.isEmpty()){
            return false;
        }
        return true;
    }

    PrgState oneStep() throws MyException {
        if(exeStack.isEmpty()){
            throw new MyException("PrgState stack is empty");
        }
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    @Override
    public String toString() {
        return  "PrgState ID : " + id + "\n" +
                "ExeStack:\n" + exeStack.toString() + "\n" +
                "SymTable:\n" + symTable.toString() + "\n" +
                "Out:\n" + out.toString() + "\n" +
                "FileTable:\n" + fileTable.toStringFormatted() + "\n" +
                "Heap:\n" + heap.toStringFormatted() + "\n";
    }
}