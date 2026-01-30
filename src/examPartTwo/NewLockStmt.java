package examPartTwo;
import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.statements.IStmt;
import model.types.IType;
import model.types.IntType;
import model.values.IntValue;
import model.values.IValue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLockStmt implements IStmt {
    private String var;
    private static Lock lock = new ReentrantLock();

    public NewLockStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        try {
            MyIDictionary<String, IValue> symTable = state.getSymTable();
            MyILockTable lockTable = state.getLockTable();

            if (symTable.isDefined(var) && symTable.get(var).getType().equals(new IntType())) {
                int freeLoc = lockTable.getFreeValue();
                lockTable.put(freeLoc, -1);
                symTable.put(var, new IntValue(freeLoc));
            } else {
                throw new MyException("Variable not declared or not int.");
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
    public IStmt deepCopy() { return new NewLockStmt(var); }

    @Override
    public String toString() { return "NewLockStmt(" + var + ")"; }
}