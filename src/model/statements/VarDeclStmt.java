package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyDictionary;
import model.adt.MyIDictionary;
import model.types.BoolType;
import model.types.IType;
import model.types.IntType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;

public class VarDeclStmt implements IStmt{

    private IType type;
    private String varName;
    static BoolType boolType = new BoolType();
    static IntType intType = new IntType();
    static StringType stringType = new StringType();

    public VarDeclStmt(String varName, IType type) {
        this.varName = varName;
        this.type = type;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> dict = state.getSymTable();

        if(!dict.isDefined(varName)){
            if(type.getClass() == BoolType.class){
                BoolValue boolValue = (BoolValue)boolType.defaultValue();

                MyIDictionary<String, IValue> tempDict= state.getSymTable();
                tempDict.put(varName,boolValue);
            }
            else if (type.getClass() == IntType.class){
                IntValue intValue = (IntValue)intType.defaultValue();

                MyIDictionary<String, IValue> tempDict= state.getSymTable();
                tempDict.put(varName,intValue);
            }
            else if(type.getClass() == StringType.class){
                StringValue stringValue = (StringValue)stringType.defaultValue();

                MyIDictionary<String, IValue> tempDict= state.getSymTable();
                tempDict.put(varName,stringValue);
            }
            else{
                throw new MyException("Not a valid type");
            }
        }
        else {
            throw new MyException("Variable already declared");
        }
        return state;
    }

    @Override
    public IStmt deepCopy() {
        IStmt newVarDeclStmt = new VarDeclStmt(this.varName, this.type);
        return newVarDeclStmt;
    }

    @Override
    public String toString(){
        return "VarDeclStmt varName: " + this.varName + " type: " +  this.type;
    }
}
