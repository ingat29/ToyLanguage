package gui;

import controller.MultiThreadControllerInterface;
import exception.MyException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.PrgState;
import model.statements.IStmt;
import model.values.IValue;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainWindowController {
    private MultiThreadControllerInterface controller;

    @FXML private TextField nrPrgStatesField;
    @FXML private TableView<HeapEntry> heapTableView;
    @FXML private TableColumn<HeapEntry, Integer> heapAddressColumn;
    @FXML private TableColumn<HeapEntry, String> heapValueColumn;
    @FXML private ListView<String> outListView;
    @FXML private ListView<String> fileTableListView;
    @FXML private ListView<Integer> prgStateIdsListView;
    @FXML private TableView<SymTblEntry> symTableView;
    @FXML private TableColumn<SymTblEntry, String> symVarNameColumn;
    @FXML private TableColumn<SymTblEntry, String> symValueColumn;
    @FXML private ListView<String> exeStackListView;

    public void setController(MultiThreadControllerInterface controller) {
        this.controller = controller;
        this.populateUI();
    }

    @FXML
    public void initialize() {
        // Map table columns to the wrapper class properties
        heapAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        heapValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        symVarNameColumn.setCellValueFactory(new PropertyValueFactory<>("variableName"));
        symValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        // Update SymTable and Stack when a different PrgState ID is clicked
        prgStateIdsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> populateUI());
    }

    @FXML
    private void handleRunOneStep() {
        if (controller == null) return;
        try {
            List<PrgState> prgList = controller.removeCompletedPrg(controller.getRepo().getPrgList());
            if (prgList.size() > 0) {
                controller.oneStepForAllPrg(prgList);
                populateUI();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Program has finished execution.").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void populateUI() {
        try {
            List<PrgState> prgList = controller.getRepo().getPrgList();
            PrgState currentPrg = getCurrentSelectedPrgState(prgList);

            nrPrgStatesField.setText(String.valueOf(prgList.size()));

            if (prgList.size() > 0) {
                PrgState first = prgList.get(0);
                heapTableView.setItems(FXCollections.observableArrayList(
                        first.getHeap().getContent().entrySet().stream()
                                .map(e -> new HeapEntry(e.getKey(), e.getValue().toString()))
                                .collect(Collectors.toList())));
                outListView.setItems(FXCollections.observableArrayList(
                        first.getOut().toString().replace("[", "").replace("]", "").split(", ")));
                fileTableListView.setItems(FXCollections.observableArrayList(
                        first.getFileTable().getContent().keySet().stream().map(Object::toString).collect(Collectors.toList())));
            }

            prgStateIdsListView.setItems(FXCollections.observableArrayList(
                    prgList.stream().map(PrgState::getId).collect(Collectors.toList())));

            if (currentPrg != null) {
                symTableView.setItems(FXCollections.observableArrayList(
                        currentPrg.getSymTable().getContent().entrySet().stream()
                                .map(e -> new SymTblEntry(e.getKey(), e.getValue().toString()))
                                .collect(Collectors.toList())));
                exeStackListView.setItems(FXCollections.observableArrayList(
                        currentPrg.getExeStack().toString().replace("[", "").replace("]", "").split(", ")));
            }
        } catch (MyException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private PrgState getCurrentSelectedPrgState(List<PrgState> prgList) {
        Integer selectedId = prgStateIdsListView.getSelectionModel().getSelectedItem();
        if (selectedId == null && !prgList.isEmpty()) return prgList.get(0);
        return prgList.stream().filter(p -> p.getId() == selectedId).findFirst().orElse(null);
    }

}