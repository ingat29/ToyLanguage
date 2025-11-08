package repository;
import exception.MyException;
import model.PrgState;
public interface MyIRepository {

    PrgState getCrtPrg();
    void logPrgStateExecution()throws MyException;
}
