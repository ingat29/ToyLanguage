package examPartOne;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.expressions.*;
import model.statements.*;
import model.types.IType;
import model.types.IntType;

public class ForStmt implements IStmt {
    private String var;
    private IExp exp1, exp2, exp3;
    private IStmt stmt;

    public ForStmt(String var, IExp exp1, IExp exp2, IExp exp3, IStmt stmt) {
        this.var = var;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack = state.getExeStack();

        // Expand the for loop into: int v; v=exp1; while(v<exp2) { stmt; v=exp3 }
        IStmt converted = new CompStmt(
                new VarDeclStmt(var, new IntType()),
                new CompStmt(
                        new AssignmentStmt(var, exp1),
                        new WhileStmt(
                                new RelationalExpression(new VariableExpression(var), exp2, RelationalOperation.LESS_THAN),
                                new CompStmt(stmt, new AssignmentStmt(var, exp3))
                        )
                )
        );

        stack.push(converted);
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        MyIDictionary<String, IType> localEnv = typeEnv.deepCopy();

        localEnv.put(var, new IntType());

        IType t1 = exp1.typeCheck(localEnv);
        IType t2 = exp2.typeCheck(localEnv);
        IType t3 = exp3.typeCheck(localEnv);

        if (t1.equals(new IntType()) && t2.equals(new IntType()) && t3.equals(new IntType())) {
            stmt.typeCheck(localEnv);
            return typeEnv; //return original env bcs variable v is local to forloop
        } else {
            throw new MyException("ForStmt: exp1, exp2, and exp3 must all be int");
        }
    }

    @Override
    public IStmt deepCopy() {
        return new ForStmt(var, exp1.deepCopy(), exp2.deepCopy(), exp3.deepCopy(), stmt.deepCopy());
    }

    @Override
    public String toString() {
        return "for(" + var + "=" + exp1 + "; " + var + "<" + exp2 + "; " + var + "=" + exp3 + ") {" + stmt + "}";
    }
}