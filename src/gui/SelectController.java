package gui;

import controller.MultiThreadController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.PrgState;
import model.adt.*;
import model.statements.IStmt;
import model.types.IType;
import repository.MultiThreadRepository;
import java.util.ArrayList;
import java.util.List;

public class SelectController {
    @FXML private ListView<IStmt> programsListView;
    private List<IStmt> allPrograms = new ArrayList<>();

    public void setPrograms(List<IStmt> programs) {
        this.allPrograms = programs;
        programsListView.setItems(FXCollections.observableArrayList(allPrograms));
    }

    public IStmt getSelectedProgram() {
        return programsListView.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void handleRunProgram() {
        IStmt selected = getSelectedProgram(); //
        if (selected == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a program!").show();
            return;
        }

        try {
            selected.typeCheck(new MyDictionary<String, IType>());

            PrgState prg = new PrgState(new MyStack<>(), new MyDictionary<>(),
                    new FileTable(), new MyList<>(),
                    selected, new MyHeap());
            MultiThreadRepository repo = new MultiThreadRepository(prg, "log.txt");
            MultiThreadController ctrl = new MultiThreadController(repo, true);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainWindow.fxml"));
            Parent root = loader.load();

             MainWindowController mainCtrl = loader.getController();
            mainCtrl.setController(ctrl); //

            Stage stage = new Stage();
            stage.setTitle("Toy Language - Execution");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}