package sk.fxmachine.controller;

import java.util.Map.Entry;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sk.fxmachine.models.Data;
import sk.fxmachine.models.EchoResponse;

public class ResponseScene {

    @FXML
    private TableView<Entry<String, String>> headersTable;
    @FXML
    private TableColumn<Entry<String, String>, String> fieldColumn;
    @FXML
    private TableColumn<Entry<String, String>, String> valueColumn;
    @FXML
    private Label messageTitleLabel;
    @FXML
    private Label messageBodyLabel;

    public void initialize(EchoResponse echoResponse) {
        setupTable();
        Data data = echoResponse.geData();
        if (data != null) {
            messageTitleLabel.setText(data.getTitle());
            messageBodyLabel.setText(data.getBody());
        }
        ObservableList<Entry<String, String>> headers = FXCollections.observableArrayList();
        headers.addAll(echoResponse.getHeaders().entrySet());
        headersTable.setItems(headers);
    }

    private void setupTable() {
        fieldColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKey()));
        valueColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue()));
    }

}
