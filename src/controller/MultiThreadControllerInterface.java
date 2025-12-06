package controller;

import exception.MyException;
import model.PrgState;

public interface MultiThreadControllerInterface {
    void allStep() throws MyException;
    void setDisplayFlag(boolean displayFlag);
    void displayPrgState(PrgState state);
}
