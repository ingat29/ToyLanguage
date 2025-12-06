package controller;

import exception.MyException;
import model.PrgState;

import java.util.List;

public interface MultiThreadControllerInterface {
    void allStep() throws MyException;
    void setDisplayFlag(boolean displayFlag);
    void displayPrgState(PrgState state);
    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList);
}
