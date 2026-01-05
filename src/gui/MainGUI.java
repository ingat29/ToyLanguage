package gui;
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
                new CompStmt(new AssignmentStmt("varf", new ValueExpression(new StringValue("test.in"))),
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
                new PrintStmt(new ReadHeapExp(new VariableExpression("a")))))))));
        list.add(ex9);

        return list;
    }

    public static void main(String[] args) {
        launch(args);
    }
}