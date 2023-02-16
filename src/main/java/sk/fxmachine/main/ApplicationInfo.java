package sk.fxmachine.main;

import java.util.Objects;

import org.springframework.context.ApplicationContext;

import javafx.stage.Stage;

public class ApplicationInfo {

    private final Stage stage;
    private final ApplicationContext applicationContext;

    public ApplicationInfo(Stage stage, ApplicationContext applicationContext) {
        this.stage = stage;
        this.applicationContext = applicationContext;
    }

    public Stage getStage() {
        return this.stage;
    }

    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof ApplicationInfo)) {
            return false;
        }
        ApplicationInfo applicationInfo = (ApplicationInfo) object;
        return Objects.equals(stage, applicationInfo.stage) &&
                Objects.equals(applicationContext, applicationInfo.applicationContext);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stage, applicationContext);
    }

}
