package view;
import controller.MyController;
import controller.MyIController;
import exception.MyException;
import model.PrgState;
import model.adt.*;
import model.expressions.ArithmeticalExpression;
import model.expressions.ArithmeticalOperation;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;
import repository.MyIRepository;
import repository.MyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class View {
    IStmt example1;
    IStmt example2;
    IStmt example3;

    MyIRepository repo1;
    MyIController ctrlr1;
    MyIRepository repo2;
    MyIController ctrlr2;
    MyIRepository repo3;
    MyIController ctrlr3;

    PrgState prgState1;
    PrgState prgState2;
    PrgState prgState3;

    List<PrgState> prgList1;
    List<PrgState> prgList2;
    List<PrgState> prgList3;

    public View(){

        //int v; v=2;Print(v)
        example1 = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignmentStmt("v",new ValueExpression(new IntValue(2))), new PrintStmt(new VariableExpression("v"))));

        //int a;int b; a=2+3*5;b=a+1;Print(b)
        example2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignmentStmt("a", new ArithmeticalExpression(ArithmeticalOperation.ADD,new ValueExpression(new IntValue(2)),new ArithmeticalExpression(ArithmeticalOperation.MULTIPLY,new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompStmt(new AssignmentStmt("b",new ArithmeticalExpression(ArithmeticalOperation.ADD,new VariableExpression("a"), new ValueExpression(new IntValue(1)))), new PrintStmt(new VariableExpression("b"))))));

        //bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        example3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignmentStmt("a", new ValueExpression(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VariableExpression("a"),new AssignmentStmt("v",new ValueExpression(new IntValue(2))), new AssignmentStmt("v", new ValueExpression(new IntValue(3)))), new PrintStmt(new VariableExpression("v"))))));

        prgState1 = new PrgState(new MyStack<IStmt>(),new MyDictionary<String, IValue>(),new FileTable(), new MyList<IValue>(),example1);
        prgState2 = new PrgState(new MyStack<IStmt>(),new MyDictionary<String, IValue>(),new FileTable(), new MyList<IValue>(),example2);
        prgState3 = new PrgState(new MyStack<IStmt>(),new MyDictionary<String, IValue>(),new FileTable(), new MyList<IValue>(),example3);

        prgList1 = new ArrayList<>();
        prgList1.add(prgState1);
        prgList2 = new ArrayList<>();
        prgList2.add(prgState2);
        prgList3 = new ArrayList<>();
        prgList3.add(prgState3);

        repo1 = new MyRepository(prgList1,"log1.txt");
        ctrlr1= new MyController(repo1,true);
        repo2 = new MyRepository(prgList2,"log2.txt");
        ctrlr2= new MyController(repo2,true);
        repo3 = new MyRepository(prgList3,"log3.txt");
        ctrlr3= new MyController(repo3,true);
    }


    public void DisplayExamples(){
        System.out.println("Select which example you want to see");
        System.out.println("1 - int v; v=2;Print(v)");
        System.out.println("2 - int a;int b; a=2+3*5;b=a+1;Print(b)");
        System.out.println("3 - bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)");
        System.out.println("0 - Exit");
    }


    public void Run(){
        Scanner sc = new Scanner(System.in);

        while(true){
            this.DisplayExamples();
            String command = sc.nextLine().trim();
            try{
                switch(command){
                    case "1": ctrlr1.allStep();
                        break;
                    case "2": ctrlr2.allStep();
                        break;
                    case "3": ctrlr3.allStep();
                        break;
                    case "0": System.exit(0);
                }
            }catch(MyException e){
                System.err.println(e.getMessage());
            }
        }
    }
}
