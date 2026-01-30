package examPartTwo;
import exception.MyException;
import model.PrgState;
import model.statements.IStmt;
import model.types.IType;
import model.types.IntType;
import model.values.IntValue;
import model.values.IValue;
import model.adt.MyIDictionary;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockStmt implements IStmt {
    private String var;
    private static Lock lock = new ReentrantLock();

    public LockStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        try {
            IValue val = state.getSymTable().get(var);
            if (val == null || !val.getType().equals(new IntType())) {
                throw new MyException("Var is not of type int!");
            }

            int foundIndex = ((IntValue) val).getVal();
            MyILockTable lockTable = state.getLockTable();

            if (!lockTable.containsKey(foundIndex)) {
                throw new MyException("Index not in LockTable!");
            }

            if (lockTable.get(foundIndex) == -1) {
                lockTable.update(foundIndex, state.getId());
            } else {
                state.getExeStack().push(this);
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        if (!typeEnv.get(var).equals(new IntType()))
            throw new MyException("Var is not int!");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() { return new LockStmt(var); }

    @Override
    public String toString() { return "lock(" + var + ")"; }
}