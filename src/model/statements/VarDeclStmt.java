package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.types.IType;
import model.values.IValue;

public class VarDeclStmt implements IStmt {
    private String varName;
    private IType type;

    public VarDeclStmt(String varName, IType type) {
        this.varName = varName;
        this.type = type;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> dict = state.getSymTable();

        if (dict.isDefined(varName)) {
            throw new MyException("Variable already declared");
        } else {
            dict.put(varName, type.defaultValue());
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(varName, type);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        typeEnv.put(varName, type);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "VarDeclStmt(" + varName + "," + type.toString() + ')';
    }
}