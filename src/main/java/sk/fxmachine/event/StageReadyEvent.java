package sk.fxmachine.event;

import sk.fxmachine.main.ApplicationInfo;

import org.springframework.context.ApplicationEvent;

public class StageReadyEvent extends ApplicationEvent {

    public StageReadyEvent(ApplicationInfo applicationInfo) {
        super(applicationInfo);
    }

    public ApplicationInfo geApplicationInfo() {
        return (ApplicationInfo) getSource();
    }

}
