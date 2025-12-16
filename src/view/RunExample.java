package view;

import controller.MyIController;
import controller.MultiThreadControllerInterface; //
import exception.MyException;

public class RunExample extends Command {
    private MyIController ctr;
    private MultiThreadControllerInterface mCtr;

    // Constructor for the old single-threaded examples (1-8)
    public RunExample(String key, String desc, MyIController ctr){
        super(key, desc);
        this.ctr = ctr;
    }

    // New Constructor for the concurrent example (9)
    public RunExample(String key, String desc, MultiThreadControllerInterface mCtr){
        super(key, desc);
        this.mCtr = mCtr;
    }

    @Override
    public void execute() {
        try {
            if (ctr != null) {
                ctr.allStep();
            } else if (mCtr != null) {
                mCtr.allStep();

            }
        } catch (MyException e) {
            System.err.println(e.getMessage());
        }
    }
}