package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIStack;
import model.expressions.IExp;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;

public class WhileStmt implements IStmt {
    IStmt body;
    IExp condition;

    public WhileStmt(IExp condition,IStmt body) {
        this.body = body;
        this.condition = condition;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack stack = state.getExeStack();
        IValue condEval = condition.eval(state.getSymTable(), state.getHeap());
        IType condType = condEval.getType();

        if(!(condType instanceof BoolType)){
            throw new MyException("Condition Type is not BoolType");
        }
        BoolValue boolCond = (BoolValue)condEval;

        if(boolCond.getVal()){
            stack.push(this);
            stack.push(body);
        }

        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(condition.deepCopy(),body.deepCopy());
    }

    @Override
    public String toString() {
        return "while(" + condition.toString() + "){" + body.toString() + "}";
    }
}
