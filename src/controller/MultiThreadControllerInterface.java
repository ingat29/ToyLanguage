package controller;

import exception.MyException;
import model.PrgState;

import java.util.List;

public interface MultiThreadControllerInterface {
    void allStep() throws MyException;
    void setDisplayFlag(boolean displayFlag);
    void displayPrgState(PrgState state);
    void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException;
    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList);
}
