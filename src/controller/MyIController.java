package controller;
import exception.MyException;
import model.PrgState;

public interface MyIController {
    PrgState oneStep(PrgState state) throws MyException;
    void allStep() throws MyException;
    void setDisplayFlag(boolean displayFlag);
    void displayPrgState(PrgState state);
}
