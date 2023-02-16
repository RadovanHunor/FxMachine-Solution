package sk.fxmachine.listener;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import sk.fxmachine.controller.MainScene;
import sk.fxmachine.event.StageReadyEvent;
import sk.fxmachine.logging.BasicLogger;
import sk.fxmachine.main.ApplicationInfo;

@Component
public class StageReadyListener implements ApplicationListener<StageReadyEvent> {

    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        loadMainScene(stageReadyEvent.geApplicationInfo());
        BasicLogger.info("Application started...");
    }

    private void loadMainScene(ApplicationInfo applicationInfo) {
        Stage stage = applicationInfo.getStage();
        try {
            var loader = new FXMLLoader(MainScene.class.getResource("MainScene.fxml"));
            var scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
            MainScene mainScene = loader.getController();
            mainScene.initialize(applicationInfo.getApplicationContext());

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
