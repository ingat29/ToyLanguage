package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expressions.IExp;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;

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
        MyIHeap tempHeap = state.getHeap();

        if (tempDict.isDefined(varName)) {
            IValue expEval = exp.eval(tempDict , tempHeap);
            boolean isExpEvalBool = expEval.getClass().equals(BoolValue.class);
            boolean isExpEvalInt = expEval.getClass().equals(IntValue.class);
            boolean isExpEvalString = expEval.getClass().equals(StringValue.class);

            boolean isVarBool = tempDict.get(varName) instanceof BoolValue;
            boolean isVarInt = tempDict.get(varName) instanceof IntValue;
            boolean isVarString = tempDict.get(varName) instanceof StringValue;

            if(isExpEvalBool && isVarBool) {
                tempDict.put(varName, expEval);
            }
            else if(isVarInt && isExpEvalInt) {
                tempDict.put(varName, expEval);
            }
            else if(isExpEvalString && isVarString){
                tempDict.put(varName, expEval);
            }
            else {
                throw new MyException("Variable type and expression type mismatch");
            }
        }
        else{
            throw new MyException("Variable " + varName + " is not defined");
        }
        return null;
    }

    @Override
    public String toString() {
        return "AssignStmt(" + varName + "," + exp.toString() +')';
    }

    @Override
    public IStmt deepCopy() {
        IStmt newAssignmentStmt = new AssignmentStmt(this.varName, this.exp.deepCopy());
        return newAssignmentStmt;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeVar = typeEnv.get(varName);
        IType typeExp = exp.typeCheck(typeEnv);

        if(typeVar.equals(typeExp)){
            return typeEnv;
        }else{
            throw new MyException("Assignment : Variable type and expression type mismatch");
        }
    }
}
