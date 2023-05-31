package com.pio;

import com.pio.startup.BaseControllerService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewRunner extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        BaseControllerService controller = new BaseControllerService();
        fxmlLoader.setController(controller);
        controller.setStage(stage);
        controller.moveToMainStarterView();
    }

    public static void main(String[] args) {
        launch();
    }

}
