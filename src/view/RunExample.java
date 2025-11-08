package view;

import controller.MyIController;
import exception.MyException;

import java.sql.SQLOutput;

public class RunExample extends Command {
    private MyIController ctr;
    public RunExample(String key, String desc,MyIController ctr){
        super(key, desc);
        this.ctr=ctr;
    }
    @Override
    public void execute() {
        try{
            ctr.allStep(); }
        catch (MyException e) {
            System.err.println(e.getMessage());
        }
    }
}
