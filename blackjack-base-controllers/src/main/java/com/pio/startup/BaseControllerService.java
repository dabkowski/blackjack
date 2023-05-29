package com.pio.startup;

import com.pio.models.BaseModelService;
import com.pio.models.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class BaseControllerService {
    public static int MAX_PLAYERS = 4;

    @FXML
    private Text firstMoneyPlayer;

    @FXML
    private Text secondMoneyPlayer;

    @FXML
    private Text thirdMoneyPlayer;

    @FXML
    private Text fourthMoneyPlayer;

    private Text[] currentBetAmount;

    private Stage stage;

    private int currentPlayerIndex = 0;

    private int betSum = 0;

    private final BaseModelService baseModelService = new BaseModelService();

    public BaseControllerService() {
    }

    public void moveToMainStarterView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("startup/blackjack-starter-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 847.09);
        stage.setTitle("Blackjack!");
        stage.getIcons().add(new Image("startup/coin.png"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        BaseControllerService controller = fxmlLoader.getController();
        controller.setStage(stage);
    }

    public void moveToGameView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("startup/Game-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 847.09);
        stage.setTitle("Blackjack!");
        stage.getIcons().add(new Image("startup/coin.png"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        BaseControllerService controller = fxmlLoader.getController();
        controller.setStage(stage);

        initialize();
    }

    public void moveToInfoView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("startup/info-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 847.09);
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

        if(betSum == 0){
            return;
        }

        Player player = baseModelService.returnPlayer(currentPlayerIndex);
        player.placeBet(betSum);

        changePlayerMove();
    }

    public void leaveGame(MouseEvent event) {
        System.out.println("leaveGame");
    }

    public void stand(MouseEvent event) {
        System.out.println("stand");
    }

    public void add1000Chip(MouseEvent event) {
        if(event.getButton() == MouseButton.PRIMARY) {
            betSum += 1000;
        }
        else if(event.getButton() == MouseButton.SECONDARY){
            betSum -= 1000;
        }
        updateBetAmountText(betSum);
    }

    public void add500Chip(MouseEvent event) {
        if(event.getButton() == MouseButton.PRIMARY) {
            betSum += 500;
        }
        else if(event.getButton() == MouseButton.SECONDARY){
            betSum -= 500;
        }
        updateBetAmountText(betSum);
    }

    public void add200Chip(MouseEvent event) {
        if(event.getButton() == MouseButton.PRIMARY) {
            betSum += 200;
        }
        else if(event.getButton() == MouseButton.SECONDARY){
            betSum -= 200;
        }
        updateBetAmountText(betSum);
    }

    public void add100Chip(MouseEvent event) {
        if(event.getButton() == MouseButton.PRIMARY) {
            betSum += 100;
        }
        else if(event.getButton() == MouseButton.SECONDARY){
            betSum -= 100;
        }
        updateBetAmountText(betSum);
    }

    public void add50Chip(MouseEvent event) {
        if(event.getButton() == MouseButton.PRIMARY) {
            betSum += 50;
        }
        else if(event.getButton() == MouseButton.SECONDARY){
            betSum -= 50;
        }
        updateBetAmountText(betSum);
    }

    public void add20Chip(MouseEvent event) {
        if(event.getButton() == MouseButton.PRIMARY) {
            betSum += 20;
        }
        else if(event.getButton() == MouseButton.SECONDARY){
            betSum -= 20;
        }
        updateBetAmountText(betSum);
    }

    public void add10Chip(MouseEvent event) {
        if(event.getButton() == MouseButton.PRIMARY) {
            betSum += 10;
        }
        else if(event.getButton() == MouseButton.SECONDARY){
            betSum -= 10;
        }
        updateBetAmountText(betSum);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        currentBetAmount = new Text[]{firstMoneyPlayer, secondMoneyPlayer, thirdMoneyPlayer, fourthMoneyPlayer};
    }

    public void changePlayerMove() {
        betSum = 0;
        currentPlayerIndex++;

        if (currentPlayerIndex >= MAX_PLAYERS) {
            currentPlayerIndex = 0;
            cleanMoneyFields();
        }
    }

    public void cleanMoneyFields() {
        for (int i = 0; i < MAX_PLAYERS; i++) {
            currentBetAmount[i].setText("0$");
        }
    }

    public void updateBetAmountText(int amount){
        if(amount < 0){
            betSum = 0;
            amount = 0;
        }

        currentBetAmount[currentPlayerIndex].setText(amount + "$");

    }
}
