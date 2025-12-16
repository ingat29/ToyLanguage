package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.FileTable;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.types.IType;
import model.types.StringType;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStmt implements IStmt{
    IExp exp;

    public OpenRFileStmt(IExp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if(exp.eval(state.getSymTable(), state.getHeap()).getType().equals(new StringType())){
            StringValue tempVal = (StringValue) exp.eval(state.getSymTable(),state.getHeap());

            MyIDictionary<StringValue , BufferedReader> fileTable = state.getFileTable();
            if(!fileTable.isDefined(tempVal)) {

                try{
                    FileReader fr = new FileReader(tempVal.getValue());
                    BufferedReader br = new BufferedReader(fr);

                    fileTable.put(tempVal, br);
                }catch(IOException e){
                    throw new MyException(e.getMessage());
                }
            }
            else throw new MyException("File path is already defined in file table");
        }
        else throw new MyException("Expression evaluation is not a StringValue");

        return null;
    }

    @Override
    public String  toString(){
        return "OpenRFileStmt(" + exp.toString() + ')';
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFileStmt(exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeExp = exp.typeCheck(typeEnv);
        if (typeExp.equals(new StringType())) {
            return typeEnv;
        }else{
            throw new MyException("OpenReadFile : Expression evaluation is not a StringValue");
        }
    }
}
