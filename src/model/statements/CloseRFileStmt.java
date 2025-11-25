package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.types.StringType;
import model.values.IntValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt {

    IExp exp;

    public CloseRFileStmt(IExp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if(exp.eval(state.getSymTable(),state.getHeap()).getType().equals(new StringType())){
            StringValue tempVal = (StringValue) exp.eval(state.getSymTable(),state.getHeap());
            MyIDictionary<StringValue,BufferedReader> fileTable = state.getFileTable();

            if(fileTable.isDefined((StringValue) tempVal)) {
                BufferedReader br = fileTable.get((StringValue) tempVal);

                try{
                    br.close();
                    fileTable.remove((StringValue) tempVal);

                }catch (IOException e) {
                    throw new MyException(e.getMessage());
                }

            }else throw new MyException("File path is not defined in file table");

        }else throw new MyException("Expression evaluation is not a StringValue");

        return state;
    }

    @Override
    public String toString(){
        return "CloseRFileStmt : " + exp.toString();
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFileStmt(exp.deepCopy());
    }
}
