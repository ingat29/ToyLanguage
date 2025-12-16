package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;

public class IfStmt implements IStmt {
    private IExp exp;
    private IStmt thenS;
    private IStmt elseS;

    public IfStmt(IExp exp, IStmt thenS, IStmt elseS) {
        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    public String toString() {
        return "if(" + exp + "){" + thenS + "}else{" + elseS + "}";
    }

    public PrgState execute(PrgState state) throws MyException {
        IValue val = exp.eval(state.getSymTable() , state.getHeap());
        if (val.getType().equals(new BoolType())) {
            BoolValue b = (BoolValue) val;
            if (b.getVal() == true) {
                state.getExeStack().push(thenS);
            } else {
                state.getExeStack().push(elseS);
            }
        } else {
            throw new MyException("The condition is not boolean type.");
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        IfStmt newIfStmt = new IfStmt(this.exp.deepCopy(), this.thenS.deepCopy(), this.elseS.deepCopy());
        return newIfStmt;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeExp = exp.typeCheck(typeEnv);
        if(typeExp.equals(new BoolType())) {
            thenS.typeCheck(typeEnv.deepCopy());
            elseS.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }else{
            throw new MyException("IfStmt : The condition is not boolean type");
        }
    }
}