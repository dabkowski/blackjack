package com.pio.startup;
import javafx.scene.input.MouseEvent;
import org.springframework.stereotype.Component;

@Component
public class BaseControllerStartupService implements ControllerStartupService {

    private final ViewStartupService viewStartupService;

    public BaseControllerStartupService(ViewStartupService viewStartupService) {
        this.viewStartupService = viewStartupService;
    }

    @Override
    public void run() {
        viewStartupService.showView();
    }

    public void handleActionEvent(MouseEvent event) {
        System.out.println("Button pressed!");
    }
}
