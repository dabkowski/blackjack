package com.pio.startup;

import com.pio.models.BaseModelService;
import com.pio.models.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.util.Objects;

public class BaseControllerService {
    public static int MAX_PLAYERS = 4;

    @FXML
    private Text dataFirstPlayer;

    @FXML
    private Text dataSecondPlayer;

    @FXML
    private Text dataThirdPlayer;

    @FXML
    private Text dataFourthPlayer;


    @FXML
    private Text firstMoneyPlayer;

    @FXML
    private Text secondMoneyPlayer;

    @FXML
    private Text thirdMoneyPlayer;

    @FXML
    private Text fourthMoneyPlayer;


    @FXML
    private TextField firstUserName;

    @FXML
    private TextField secondUserName;

    @FXML
    private TextField fourthUserName;

    @FXML
    private TextField thirdUserName;


    private Text[] currentBet;

    private Stage stage;

    private int currentPlayerIndex = 0;

    private int betSum = 0;

    @FXML
    private Label noPlayerName;

    private final BaseModelService baseModelService = new BaseModelService();

    private static final String[] userName = new String[MAX_PLAYERS];
    private static final int[] accountAmount = new int[MAX_PLAYERS];
    int startAmount = 8000;

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
        int numberOfPlayer = checkNumberOfPlayers();
        System.out.println(numberOfPlayer);
        if (numberOfPlayer > 0) {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("startup/game-screen.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1080, 847.09);
            stage.setTitle("Blackjack!");
            stage.getIcons().add(new Image("startup/coin.png"));
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

            BaseControllerService controller = fxmlLoader.getController();
            controller.setStage(stage);

            initialize();

        } else {
            noPlayerName.setText("You must have at least one player name ");
        }

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
        if (betSum == 0) {
            return;
        }


        Player player = baseModelService.returnPlayer(currentPlayerIndex);
        player.placeBet(betSum);

        assignPlayersNames();

        changePlayerMove();
    }

    public void leaveGame(MouseEvent event) throws IOException {

        Player player = baseModelService.returnPlayer(currentPlayerIndex);
        currentBet[currentPlayerIndex].setText("");
        player.setPlaying(false);

        if (returnAmountOfPlayingPlayers() == 0) {
            moveToMainStarterView();
            return;
        }

        changePlayerMove();
    }

    public void stand(MouseEvent event) {
        System.out.println("stand");
    }

    public void add1000Chip(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            betSum += 1000;
        } else if (event.getButton() == MouseButton.SECONDARY) {
            betSum -= 1000;
        }
        updateBetText(betSum);
    }

    public void add500Chip(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            betSum += 500;
        } else if (event.getButton() == MouseButton.SECONDARY) {
            betSum -= 500;
        }
        updateBetText(betSum);
    }

    public void add200Chip(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            betSum += 200;
        } else if (event.getButton() == MouseButton.SECONDARY) {
            betSum -= 200;
        }
        updateBetText(betSum);
    }

    public void add100Chip(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            betSum += 100;
        } else if (event.getButton() == MouseButton.SECONDARY) {
            betSum -= 100;
        }
        updateBetText(betSum);
    }

    public void add50Chip(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            betSum += 50;
        } else if (event.getButton() == MouseButton.SECONDARY) {
            betSum -= 50;
        }
        updateBetText(betSum);
    }

    public void add20Chip(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            betSum += 20;
        } else if (event.getButton() == MouseButton.SECONDARY) {
            betSum -= 20;
        }
        updateBetText(betSum);
    }

    public void add10Chip(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            betSum += 10;
        } else if (event.getButton() == MouseButton.SECONDARY) {
            betSum -= 10;
        }
        updateBetText(betSum);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        currentBet = new Text[]{firstMoneyPlayer, secondMoneyPlayer, thirdMoneyPlayer, fourthMoneyPlayer};
    }

    private void changePlayerMove() {
        betSum = 0;
        currentPlayerIndex = returnNextPlayingPlayer();
        System.out.println("Ruch: " + currentPlayerIndex);
    }

    private void cleanMoneyFields() {
        for (int i = 0; i < MAX_PLAYERS; i++) {
            Player player = baseModelService.returnPlayer(i);
            if (player.isPlaying()) {
                currentBet[i].setText("0$");
            }
        }
    }

    private void updateBetText(int amount) {
        if (amount < 0) {
            betSum = 0;
            amount = 0;
        }

        currentBet[currentPlayerIndex].setText(amount + "$");
    }

    private int returnNextPlayingPlayer() {
        while (true) {
            currentPlayerIndex++;
            if (currentPlayerIndex >= MAX_PLAYERS) {
                currentPlayerIndex = 0;
                cleanMoneyFields();
            }

            Player player = baseModelService.returnPlayer(currentPlayerIndex);
            if (player.isPlaying()) {
                return currentPlayerIndex;
            }
        }
    }

    private int returnAmountOfPlayingPlayers() {
        int onlinePlayers = 0;
        for (int i = 0; i < MAX_PLAYERS; i++) {
            Player player = baseModelService.returnPlayer(i);
            if (player.isPlaying()) {
                onlinePlayers++;
            }
        }
        return onlinePlayers;
    }

    public String getUserName(TextField textField) {
        return textField.getText();
    }

    public int checkNumberOfPlayers() {
        userName[0] = getUserName(firstUserName);
        userName[1] = getUserName(secondUserName);
        userName[2] = getUserName(thirdUserName);
        userName[3] = getUserName(fourthUserName);


        int playerCounter = 0;
        for (int i = 0; i < MAX_PLAYERS; i++) {
            if (!Objects.equals(userName[i], "")){
                playerCounter++;
                accountAmount[i]=startAmount;
            }
            System.out.println(userName[i]);
        }
        return playerCounter;
    }

    public void assignPlayersNames() {

        dataFirstPlayer.setText(userName[0]+'\n'+accountAmount[0]);
        dataSecondPlayer.setText(userName[1]+'\n'+accountAmount[1]);
        dataThirdPlayer.setText(userName[2]+'\n'+accountAmount[2]);
        dataFourthPlayer.setText(userName[3]+'\n'+accountAmount[3]);

    }


}
