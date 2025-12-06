package view;

import controller.MyController;
import controller.MyIController;
import model.PrgState;
import model.adt.*;
import model.expressions.*;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.RefType;
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

        PrgState prg1 = new PrgState(new MyStack<>(), new MyDictionary<>(), new FileTable(), new MyList<>(), ex1 , new MyHeap());
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

        PrgState prg2 = new PrgState(new MyStack<>(), new MyDictionary<>(), new FileTable(), new MyList<>(), ex2,new MyHeap());
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

        PrgState prg3 = new PrgState(new MyStack<>(), new MyDictionary<>(), new FileTable(), new MyList<>(), ex3,new MyHeap());
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

        PrgState prg4 = new PrgState(new MyStack<>(), new MyDictionary<>(), new FileTable(), new MyList<>(), ex4,new MyHeap());
        List<PrgState> list4 = new ArrayList<>();
        list4.add(prg4);
        MyIRepository repo4 = new MyRepository(list4, "log4.txt");
        MyIController ctrl4 = new MyController(repo4, true);

        // Ref int v; new(v,20); Ref Ref int a; new(a,v); print(v); print(a)
        IStmt exHeap1 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExpression(new IntValue(20))),
                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                new CompStmt(new NewStmt("a", new VariableExpression("v")),
                new CompStmt(new PrintStmt(new VariableExpression("v")),
                new PrintStmt(new VariableExpression("a")))))));

        PrgState prg5 = new PrgState(new MyStack<>(), new MyDictionary<>(), new FileTable(), new MyList<>(), exHeap1,new MyHeap());
        List<PrgState> list5 = new ArrayList<>();
        list5.add(prg5);
        MyIRepository repo5 = new MyRepository(list5, "log5.txt");
        MyIController ctrl5 = new MyController(repo5, true);

        // Ref int v; new(v,20); print(rH(v)); wH(v,30); print(rH(v)+5);
        IStmt exHeap2 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExpression(new IntValue(20))),
                new CompStmt(new PrintStmt(new ReadHeapExp(new VariableExpression("v"))),
                new CompStmt(new WriteHeapStmt("v", new ValueExpression(new IntValue(30))),
                new PrintStmt(new ArithmeticalExpression(ArithmeticalOperation.ADD,
                new ReadHeapExp(new VariableExpression("v")),
                new ValueExpression(new IntValue(5))))))));

        PrgState prg6 = new PrgState(new MyStack<>(), new MyDictionary<>(), new FileTable(), new MyList<>(), exHeap2,new MyHeap());
        List<PrgState> list6 = new ArrayList<>();
        list6.add(prg6);
        MyIRepository repo6 = new MyRepository(list6, "log6.txt");
        MyIController ctrl6 = new MyController(repo6, true);

        // Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); print(rH(rH(a)))
        IStmt exGC = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExpression(new IntValue(20))),
                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                new CompStmt(new NewStmt("a", new VariableExpression("v")),
                new CompStmt(new NewStmt("v", new ValueExpression(new IntValue(30))),
                new NewStmt("v", new ValueExpression(new IntValue(70))))))));

        PrgState prg7 = new PrgState(new MyStack<>(), new MyDictionary<>(), new FileTable(), new MyList<>(), exGC,new MyHeap());
        List<PrgState> list7 = new ArrayList<>();
        list7.add(prg7);
        MyIRepository repo7 = new MyRepository(list7, "log7.txt");
        MyIController ctrl7 = new MyController(repo7, true);

        // int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        IStmt exWhile = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignmentStmt("v", new ValueExpression(new IntValue(4))),
                new CompStmt(new WhileStmt(new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), RelationalOperation.GREATER_THAN),
                new CompStmt(new PrintStmt(new VariableExpression("v")), new AssignmentStmt("v", new ArithmeticalExpression(ArithmeticalOperation.SUBTRACT, new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                new PrintStmt(new VariableExpression("v")))));

        PrgState prg8 = new PrgState(new MyStack<>(), new MyDictionary<>(), new FileTable(), new MyList<>(), exWhile,new MyHeap());
        List<PrgState> list8 = new ArrayList<>();
        list8.add(prg8);
        MyIRepository repo8 = new MyRepository(list8, "log8.txt");
        MyIController ctrl8 = new MyController(repo8, true);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), ctrl1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctrl2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctrl3));
        menu.addCommand(new RunExample("4", ex4.toString(), ctrl4));
        menu.addCommand(new RunExample("5", exHeap1.toString(), ctrl5));
        menu.addCommand(new RunExample("6", exHeap2.toString(), ctrl6));
        menu.addCommand(new RunExample("7", exGC.toString(), ctrl7));
        menu.addCommand(new RunExample("8", exWhile.toString(), ctrl8));

        menu.show();
    }
}