package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.types.IntType;
import model.types.StringType;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;

import java.io.BufferedReader;

public class ReadFileStmt implements IStmt{
    IExp exp;//should be a String Value ->filepath
    String varName;

    public ReadFileStmt(IExp exp, String varName){
        this.exp = exp;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String,IValue> symTable = state.getSymTable();
        if(symTable.isDefined(varName) && symTable.get(varName).getType().equals(new IntType())){
            IValue tempVal = exp.eval(symTable);

            if(tempVal.getType().equals(new StringType())){
                MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

                if(fileTable.isDefined((StringValue) tempVal)){
                    BufferedReader br = fileTable.get((StringValue) tempVal);
                    try{
                        String line = br.readLine();
                        if(line == null){
                            symTable.put(varName, new IntValue(0));
                        }
                        else{
                            try{
                                int intValue = Integer.parseInt(line);
                                symTable.put(varName, new IntValue(intValue));

                            }catch (NumberFormatException e) {
                                throw new MyException(e.getMessage());
                            }
                        }

                    }catch(Exception e){
                        throw new MyException(e.getMessage());
                    }
                }
                else throw new MyException("File path is not defined in file table");
            }
            else throw new MyException("Expression doesn't evaluate to a string type");
        }
        else throw new MyException("Either variable "+varName+" is not defined or its type is not IntType");

        return state;
    }

    @Override
    public String toString(){
        return "ReadFileStmt ;file: " + exp.toString() + "; varName: " + varName;
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFileStmt(exp, varName);
    }
}
