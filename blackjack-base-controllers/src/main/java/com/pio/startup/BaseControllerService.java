package com.pio.startup;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class BaseControllerService {

    private Stage stage;

    private FXMLLoader fxmlLoader;

    public void moveToMainStarterView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("startup/blackjack-starter-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080 , 847.09);
        stage.setTitle("Blackjack!");
        stage.getIcons().add(new Image("startup/coin.png"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        BaseControllerService controller = fxmlLoader.getController();
        controller.setStage(stage);
    }

    public void moveToGameView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("startup/game-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080 , 847.09);
        stage.setTitle("Blackjack!");
        stage.getIcons().add(new Image("startup/coin.png"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        BaseControllerService controller = fxmlLoader.getController();
        controller.setStage(stage);
    }

    public void moveToInfoView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("startup/info-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080 , 847.09);
        stage.setTitle("Blackjack!");
        stage.getIcons().add(new Image("startup/coin.png"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        BaseControllerService controller = fxmlLoader.getController();
        controller.setStage(stage);
    }

    public void leaveInfoScreen(MouseEvent event) {
        System.out.println("Leaving!");
    }

    public void hit(MouseEvent event) {
        System.out.println("Hit");
    }

    public void leaveGame(MouseEvent event) {
        System.out.println("leaveGame");
    }

    public void stand(MouseEvent event) {
        System.out.println("stand");
    }

    public void add1000Chip(MouseEvent event) {
        System.out.println("add1000Chip");
    }

    public void add500Chip(MouseEvent event) {
        System.out.println("add500Chip");
    }

    public void add200Chip(MouseEvent event) {
        System.out.println("add200Chip");
    }

    public void add100Chip(MouseEvent event) {
        System.out.println("add100Chip");
    }

    public void add50Chip(MouseEvent event) {
        System.out.println("add50Chip");
    }

    public void add20Chip(MouseEvent event) {
        System.out.println("add20Chip");
    }

    public void add10Chip(MouseEvent event) {
        System.out.println("add10Chip");
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
