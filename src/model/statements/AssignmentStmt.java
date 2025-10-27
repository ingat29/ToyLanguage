package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;

public class AssignmentStmt implements IStmt {
    private IExp exp;
    private String varName;

    public AssignmentStmt(String varName, IExp exp) {
        this.exp = exp;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> tempDict = state.getSymTable();
        if (tempDict.isDefined(varName)) {
            IValue expEval = exp.eval(tempDict);
            boolean isExpEvalBool = expEval.getClass().equals(BoolValue.class);
            boolean isExpEvalInt = expEval.getClass().equals(IntValue.class);
            boolean isVarBool = tempDict.get(varName) instanceof BoolValue;
            boolean isVarInt = tempDict.get(varName) instanceof IntValue;

            if(isExpEvalBool && isVarBool) {
                tempDict.put(varName, expEval);
            }
            else if(isVarInt && isExpEvalInt) {
                tempDict.put(varName, expEval);
            }
            else {
                throw new MyException("Variable type and expression type mismatch");
            }
        }
        else{
            throw new MyException("Variable " + varName + " is not defined");
        }
        return state;
    }

    @Override
    public String toString() {
        return "Assign variable: " + varName + " expression: " + exp.toString();
    }

    @Override
    public IStmt deepCopy() {
        IStmt newAssignmentStmt = new AssignmentStmt(this.varName, this.exp.deepCopy());
        return newAssignmentStmt;
    }
}
