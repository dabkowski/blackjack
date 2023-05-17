package com.pio.startup;

import javafx.application.Application;

public class BaseViewStartupService implements ViewStartupService {

    public void showView() {
        Application.launch(ViewRunner.class);
    }

}
