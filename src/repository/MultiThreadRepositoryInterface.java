package repository;

import exception.MyException;
import model.PrgState;

import java.util.List;

public interface MultiThreadRepositoryInterface {

    void addPrg(PrgState prgState);
    void logPrgStateExecution(PrgState state)throws MyException;
    List<PrgState> getPrgList()throws MyException;
    void setPrgList(List<PrgState> prgList)throws MyException;

}
