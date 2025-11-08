package view;

import controller.MyController;
import controller.MyIController;
import model.PrgState;
import model.adt.FileTable;
import model.adt.MyDictionary;
import model.adt.MyList;
import model.adt.MyStack;
import model.expressions.ArithmeticalExpression;
import model.expressions.ArithmeticalOperation;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;

import repository.MyIRepository;
import repository.MyRepository;
import java.util.ArrayList;
import java.util.List;

class Interpreter {
    public static void main(String[] args) {

        // Example 1: int v; v=2; Print(v)
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignmentStmt("v", new ValueExpression(new IntValue(2))),
                        new PrintStmt(new VariableExpression("v"))));

        PrgState prg1 = new PrgState(new MyStack<>(), new MyDictionary<>(), new FileTable(), new MyList<>(), ex1);
        List<PrgState> list1 = new ArrayList<>();
        list1.add(prg1);
        MyIRepository repo1 = new MyRepository(list1, "log1.txt");
        MyIController ctrl1 = new MyController(repo1, true);

        // Example 2: int a; int b; a=2+3*5; b=a+1; Print(b)
        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignmentStmt("a", new ArithmeticalExpression(ArithmeticalOperation.ADD, new ValueExpression(new IntValue(2)), new ArithmeticalExpression(ArithmeticalOperation.MULTIPLY, new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompStmt(new AssignmentStmt("b", new ArithmeticalExpression(ArithmeticalOperation.ADD, new VariableExpression("a"), new ValueExpression(new IntValue(1)))),
                                        new PrintStmt(new VariableExpression("b"))))));

        PrgState prg2 = new PrgState(new MyStack<>(), new MyDictionary<>(), new FileTable(), new MyList<>(), ex2);
        List<PrgState> list2 = new ArrayList<>();
        list2.add(prg2);
        MyIRepository repo2 = new MyRepository(list2, "log2.txt");
        MyIController ctrl2 = new MyController(repo2, true);


        // Example 3: bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)
        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignmentStmt("a", new ValueExpression(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VariableExpression("a"), new AssignmentStmt("v", new ValueExpression(new IntValue(2))), new AssignmentStmt("v", new ValueExpression(new IntValue(3)))),
                                        new PrintStmt(new VariableExpression("v"))))));

        PrgState prg3 = new PrgState(new MyStack<>(), new MyDictionary<>(), new FileTable(), new MyList<>(), ex3);
        List<PrgState> list3 = new ArrayList<>();
        list3.add(prg3);
        MyIRepository repo3 = new MyRepository(list3, "log3.txt");
        MyIController ctrl3 = new MyController(repo3, true);

        // Example 4: File Operations (from Lab5.pdf)
        // string varf; varf="test.in"; openRFile(varf); int varc; readFile(varf,varc); print(varc); readFile(varf,varc); print(varc); closeRFile(varf)
        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignmentStmt("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompStmt(new OpenRFileStmt(new VariableExpression("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFileStmt(new VariableExpression("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VariableExpression("varc")),
                                                        new CompStmt(new ReadFileStmt(new VariableExpression("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VariableExpression("varc")),
                                                                        new CloseRFileStmt(new VariableExpression("varf"))))))))));

        PrgState prg4 = new PrgState(new MyStack<>(), new MyDictionary<>(), new FileTable(), new MyList<>(), ex4);
        List<PrgState> list4 = new ArrayList<>();
        list4.add(prg4);
        MyIRepository repo4 = new MyRepository(list4, "log4.txt");
        MyIController ctrl4 = new MyController(repo4, true);


        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), ctrl1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctrl2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctrl3));
        menu.addCommand(new RunExample("4", ex4.toString(), ctrl4));

        menu.show();
    }
}