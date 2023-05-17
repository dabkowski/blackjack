package com.pio.startup;

public class BaseControllerStartupService implements ControllerStartupService {

    private final ViewStartupService viewStartupService;

    public BaseControllerStartupService(ViewStartupService viewStartupService) {
        this.viewStartupService = viewStartupService;
    }

    @Override
    public void run() {
        viewStartupService.showView();
    }
}
