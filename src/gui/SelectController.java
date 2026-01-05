package gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.statements.IStmt;
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
}