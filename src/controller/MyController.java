package controller;

import exception.MyException;
import model.PrgState;
import repository.MyIRepository;
import model.adt.MyIStack;
import model.statements.IStmt;


public class MyController implements MyIController{
    MyIRepository repo;
    boolean displayFlag;

    MyController(MyIRepository repo, boolean displayFlag){
        this.repo = repo;
        this.displayFlag = displayFlag;
    }

    @Override
    public PrgState oneStep(PrgState state) throws MyException {
        MyIStack<IStmt> stk=state.getExeStack();
        if(stk.isEmpty()) {
            throw new MyException("PrgState stack is empty");
        }
        IStmt crtStmt = stk.pop();
        return crtStmt.execute(state);
    }

    @Override
    public void allStep() {
        PrgState prg = repo.getCrtPrg();
        displayPrgState(prg);
        while (!prg.getExeStack().isEmpty()){
            try {
                prg = oneStep(prg);
                displayPrgState(prg);
            }catch (MyException e){
                System.err.println(e.getMessage());
            }
        }
    }

    @Override
    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag = displayFlag;
    }

    @Override
    public void displayPrgState(PrgState state) {
        if(displayFlag){
            System.out.println(state.toString());
        }
    }
}