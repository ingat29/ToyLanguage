package gui;
import examPartOne.ForStmt;
import examPartTwo.LockStmt;
import examPartTwo.NewLockStmt;
import examPartTwo.UnlockStmt;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import gui.SelectController;
import model.expressions.*;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;

import java.util.ArrayList;
import java.util.List;

public class MainGUI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SelectWindow.fxml"));
        Scene scene = new Scene(loader.load());

        SelectController selectController = loader.getController();
        selectController.setPrograms(getExamplePrograms());

        primaryStage.setTitle("Toy Language - Select Program");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private List<IStmt> getExamplePrograms() {
        List<IStmt> list = new ArrayList<>();

        // Example 1: int v; v=2; Print(v)
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignmentStmt("v", new ValueExpression(new IntValue(2))),
                new PrintStmt(new VariableExpression("v"))));
        list.add(ex1);

        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                new CompStmt(new AssignmentStmt("a", new ArithmeticalExpression(ArithmeticalOperation.ADD, new ValueExpression(new IntValue(2)), new ArithmeticalExpression(ArithmeticalOperation.MULTIPLY, new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                new CompStmt(new AssignmentStmt("b", new ArithmeticalExpression(ArithmeticalOperation.ADD, new VariableExpression("a"), new ValueExpression(new IntValue(1)))),
                new PrintStmt(new VariableExpression("b"))))));
        list.add(ex2);

        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignmentStmt("a", new ValueExpression(new BoolValue(true))),
                new CompStmt(new IfStmt(new VariableExpression("a"), new AssignmentStmt("v", new ValueExpression(new IntValue(2))), new AssignmentStmt("v", new ValueExpression(new IntValue(3)))),
                new PrintStmt(new VariableExpression("v"))))));
        list.add(ex3);

        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignmentStmt("varf", new ValueExpression(new StringValue("test.in.txt"))),
                new CompStmt(new OpenRFileStmt(new VariableExpression("varf")),
                new CompStmt(new VarDeclStmt("varc", new IntType()),
                new CompStmt(new ReadFileStmt(new VariableExpression("varf"), "varc"),
                new CompStmt(new PrintStmt(new VariableExpression("varc")),
                new CompStmt(new ReadFileStmt(new VariableExpression("varf"), "varc"),
                new CompStmt(new PrintStmt(new VariableExpression("varc")),
                new CloseRFileStmt(new VariableExpression("varf"))))))))));
        list.add(ex4);

        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExpression(new IntValue(20))),
                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                new CompStmt(new NewStmt("a", new VariableExpression("v")),
                new CompStmt(new PrintStmt(new VariableExpression("v")),
                new PrintStmt(new VariableExpression("a")))))));
        list.add(ex5);

        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExpression(new IntValue(20))),
                new CompStmt(new PrintStmt(new ReadHeapExp(new VariableExpression("v"))),
                new CompStmt(new WriteHeapStmt("v", new ValueExpression(new IntValue(30))),
                new PrintStmt(new ArithmeticalExpression(ArithmeticalOperation.ADD,
                new ReadHeapExp(new VariableExpression("v")),
                new ValueExpression(new IntValue(5))))))));
        list.add(ex6);

        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExpression(new IntValue(20))),
                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                new CompStmt(new NewStmt("a", new VariableExpression("v")),
                new CompStmt(new NewStmt("v", new ValueExpression(new IntValue(30))),
                new NewStmt("v", new ValueExpression(new IntValue(70))))))));
        list.add(ex7);

        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignmentStmt("v", new ValueExpression(new IntValue(4))),
                new CompStmt(new WhileStmt(new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), RelationalOperation.GREATER_THAN),
                new CompStmt(new PrintStmt(new VariableExpression("v")), new AssignmentStmt("v", new ArithmeticalExpression(ArithmeticalOperation.SUBTRACT, new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                new PrintStmt(new VariableExpression("v")))));
        list.add(ex8);

        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(new AssignmentStmt("v", new ValueExpression(new IntValue(10))),
                new CompStmt(new NewStmt("a", new ValueExpression(new IntValue(22))),
                new CompStmt(
                        new ForkStmt(
                        new CompStmt(new WriteHeapStmt("a", new ValueExpression(new IntValue(30))),
                        new CompStmt(new AssignmentStmt("v", new ValueExpression(new IntValue(32))),
                        new CompStmt(new PrintStmt(new VariableExpression("v")),
                        new PrintStmt(new ReadHeapExp(new VariableExpression("a"))))))),
                new CompStmt(new PrintStmt(new VariableExpression("v")),
                new CompStmt( new PrintStmt(new ReadHeapExp(new VariableExpression("a"))),new NopStmt())))))));
        list.add(ex9);

        // Ref int a; new(a,20); (for(v=0;v<3;v=v+1)fork(print(v);v=v*rh(a))); print(rh(a))
        IStmt forExample = new CompStmt(
                new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("a", new ValueExpression(new IntValue(20))),
                        new CompStmt(
                                new ForStmt("v",
                                        new ValueExpression(new IntValue(0)),
                                        new ValueExpression(new IntValue(3)),
                                        new ArithmeticalExpression(ArithmeticalOperation.ADD, new VariableExpression("v"), new ValueExpression(new IntValue(1))),
                                        new ForkStmt(
                                                new CompStmt(
                                                        new PrintStmt(new VariableExpression("v")),
                                                        new AssignmentStmt("v",
                                                                new ArithmeticalExpression(ArithmeticalOperation.MULTIPLY,
                                                                        new VariableExpression("v"),
                                                                        new ReadHeapExp(new VariableExpression("a"))
                                                                )
                                                        )
                                                )
                                        )
                                ),
                                new PrintStmt(new ReadHeapExp(new VariableExpression("a")))
                        )
                )
        );
        list.add(forExample);

        //CodeExample

// Exam Program:
// Ref int v1; Ref int v2; int x; int q;
// new(v1,20); new(v2,30); newLock(x);
// fork( fork( ); lock(x); wh(v1,rh(v1)-1); unlock(x); lock(x); wh(v1,rh(v1)*10); unlock(x) );
// newLock(q);
// fork( fork( lock(q); wh(v2,rh(v2)+5); unlock(q) ); lock(q); wh(v2,rh(v2)*10); unlock(q) );
// nop; nop; nop; nop;
// lock(x); print(rh(v1)); unlock(x);
// lock(q); print(rh(v2)); unlock(q);

        IStmt lockExample = new CompStmt(
            new VarDeclStmt("v1", new RefType(new IntType())),
            new CompStmt(new VarDeclStmt("v2", new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("x", new IntType()),
                    new CompStmt(new VarDeclStmt("q", new IntType()),
                        new CompStmt(new NewStmt("v1", new ValueExpression(new IntValue(20))),
                            new CompStmt(new NewStmt("v2", new ValueExpression(new IntValue(30))),
                                new CompStmt(new NewLockStmt("x"),
                                    new CompStmt(
                                        // First Fork
                                        new ForkStmt(
                                            new CompStmt(
                                                new ForkStmt(new NopStmt()), // Empty fork() from the exam text
                                                new CompStmt(new LockStmt("x"),
                                                    new CompStmt(new WriteHeapStmt("v1", new ArithmeticalExpression(ArithmeticalOperation.SUBTRACT, new ReadHeapExp(new VariableExpression("v1")), new ValueExpression(new IntValue(1)))),
                                                        new CompStmt(new UnlockStmt("x"),
                                                            new CompStmt(new LockStmt("x"),
                                                                new CompStmt(new WriteHeapStmt("v1", new ArithmeticalExpression(ArithmeticalOperation.MULTIPLY, new ReadHeapExp(new VariableExpression("v1")), new ValueExpression(new IntValue(10)))),
                                                                    new UnlockStmt("x"))))))
                                            )
                                        ),
                                        new CompStmt(new NewLockStmt("q"),
                                            new CompStmt(
                                                // Second Fork
                                                new ForkStmt(
                                                    new CompStmt(
                                                        new ForkStmt( // fork(lock(q); wh(v2,rh(v2)+5); unlock(q));
                                                            new CompStmt(new LockStmt("q"),
                                                                new CompStmt(new WriteHeapStmt("v2", new ArithmeticalExpression(ArithmeticalOperation.ADD, new ReadHeapExp(new VariableExpression("v2")), new ValueExpression(new IntValue(5)))),
                                                                    new UnlockStmt("q")))
                                                        ),
                                                        new CompStmt(new LockStmt("q"),
                                                            new CompStmt(new WriteHeapStmt("v2", new ArithmeticalExpression(ArithmeticalOperation.MULTIPLY, new ReadHeapExp(new VariableExpression("v2")), new ValueExpression(new IntValue(10)))),
                                                                new UnlockStmt("q")))
                                                    )
                                                ),
                                                // Main Thread continuation
                                                new CompStmt(new NopStmt(),
                                                    new CompStmt(new NopStmt(),
                                                        new CompStmt(new NopStmt(),
                                                            new CompStmt(new NopStmt(),
                                                                new CompStmt(new LockStmt("x"),
                                                                    new CompStmt(new PrintStmt(new ReadHeapExp(new VariableExpression("v1"))),
                                                                        new CompStmt(new UnlockStmt("x"),
                                                                            new CompStmt(new LockStmt("q"),
                                                                                new CompStmt(new PrintStmt(new ReadHeapExp(new VariableExpression("v2"))),
                                                                                    new UnlockStmt("q"))))))))))))))))))));

        list.add(lockExample);

        return list;
    }

    public static void main(String[] args) {
        launch(args);
    }
}