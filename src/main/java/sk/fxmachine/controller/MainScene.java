package sk.fxmachine.controller;

import java.io.IOException;
import java.util.concurrent.Callable;

import org.springframework.context.ApplicationContext;

import javafx.concurrent.Task;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sk.fxmachine.models.EchoResponse;
import sk.fxmachine.logging.BasicLogger;
import sk.fxmachine.models.Data;
import sk.fxmachine.rest.RestApiAgent;
import sk.fxmachine.serialization.Serialization;

public class MainScene {

    private static final String POSTMAN_ECHO_URL = "https://postman-echo.com";
    private static final String GET_PATH = "/get";
    private static final String POST_PATH = "/post";

    @FXML
    private GridPane mainGrid;
    @FXML
    private GridPane loadingGrid;
    @FXML
    private RadioButton methodGetRadio;
    @FXML
    private RadioButton methodPostRadio;
    @FXML
    private TextArea messageBodyTextArea;
    @FXML
    private TextField messageTitleTextField;
    @FXML
    private TextField urlTextField;

    private RestApiAgent restApiAgent;
    private Serialization serialization;

    public void initialize(ApplicationContext applicationContext) {
        restApiAgent = applicationContext.getBean(RestApiAgent.class);
        serialization = applicationContext.getBean(Serialization.class);
    }

    @FXML
    private void initialize() {
        setupListeners();
    }

    @FXML
    private void onSendButtonAction() {
        if (methodGetRadio.isSelected()) {
            sendGetRequest();
            return;
        }
        Data message = getDataFromFields();
        sendPostRequest(message);
    }

    @FXML
    private void onResetButtonAction() {
        urlTextField.setText(POSTMAN_ECHO_URL);
        methodGetRadio.setSelected(true);
        messageTitleTextField.setText("");
        messageBodyTextArea.setText("");
    }

    private void setupListeners() {
        methodGetRadio.selectedProperty().addListener((observable, oldValue, newValue) -> {
            messageBodyTextArea.setDisable(newValue);
            messageTitleTextField.setDisable(newValue);
        });
    }

    private void sendGetRequest() {
        Task<EchoResponse> task = createTask(() -> {
            String json = restApiAgent.sendGetRequest(urlTextField.getText() + GET_PATH);
            return serialization.deserialize(json, EchoResponse.class);
        });
        handleTask(task);
    }

    private void sendPostRequest(Data messageA) {
        Task<EchoResponse> task = createTask(() -> {
            String message = serialization.serialize(messageA);
            String response = restApiAgent.sendPostRequest(urlTextField.getText() + POST_PATH, message);
            return serialization.deserialize(response, EchoResponse.class);
        });
        handleTask(task);

    }

    private Task<EchoResponse> createTask(Callable<EchoResponse> callable) {
        return new Task<EchoResponse>() {
            @Override
            protected EchoResponse call() throws Exception {
                return callable.call();
            }
        };
    }

    private void handleTask(Task<EchoResponse> task) {
        showLoading(true);
        task.setOnSucceeded(event -> {
            showResponseScene(task.getValue());
            showLoading(false);
        });
        task.setOnFailed(event -> {
            showRequestFailedAlert();
            BasicLogger.error(new Exception(task.getException()));
            showLoading(false);
        });
        new Thread(task).start();
    }

    private void showLoading(boolean show) {
        mainGrid.setDisable(show);
        loadingGrid.setVisible(show);
    }

    private Data getDataFromFields() {
        return new Data(messageTitleTextField.getText(), messageBodyTextArea.getText());
    }

    private void showResponseScene(EchoResponse echoResponse) {
        Stage stage = new Stage();
        try {
            var loader = new FXMLLoader(ResponseScene.class.getResource("ResponseScene.fxml"));
            var scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
            ResponseScene responseScene = loader.getController();
            responseScene.initialize(echoResponse);
        } catch (IOException e) {
            BasicLogger.error(e);
        }
    }

    private void showRequestFailedAlert() {
        Alert alert = new Alert(AlertType.ERROR, "Request unsuccessful", ButtonType.OK);
        alert.showAndWait();
    }
}
