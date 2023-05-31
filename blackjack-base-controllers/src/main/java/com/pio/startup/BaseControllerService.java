package com.pio.startup;

import com.pio.models.BaseModelService;
import com.pio.models.Player;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;


public class BaseControllerService implements Initializable {
    public static int MAX_PLAYERS = 4;

    public static int DECK_CARD_POS_X = 4;

    public static int DECK_CARD_POS_Y = 4;

    public static int CARD_HEIGHT = 70;

    public static int CARD_WIDTH = 50;

    @FXML
    private Label dataFirstPlayer;

    @FXML
    private Label dataSecondPlayer;

    @FXML
    private Label dataThirdPlayer;

    @FXML
    private Label dataFourthPlayer;

    @FXML
    private Text firstPlayerBet;

    @FXML
    private Text secondPlayerBet;

    @FXML
    private Text thirdPlayerBet;

    @FXML
    private Text fourthPlayerBet;


    @FXML
    private TextField firstUserName;

    @FXML
    private TextField secondUserName;

    @FXML
    private TextField fourthUserName;

    @FXML
    private TextField thirdUserName;

    private final List<Point> playerCardPosition = new ArrayList<>() {{
        add(new Point(250, 350));
        add(new Point(400, 450));
        add(new Point(550, 450));
        add(new Point(700, 350));
        add(new Point(500, 200));
        add(new Point(500, 400));
    }};

    private Text[] currentBet;

    private Stage stage;

    private int currentPlayerIndex = 0;

    private int betSum = 0;

    @FXML
    private Label noPlayerName;

    @FXML
    private AnchorPane gamePane;

    Image backImage = new Image("Cards/back.png");

    @FXML
    private Label gameStatus;

    @FXML
    private Circle firstPlayerCircle;

    @FXML
    private Circle secondPlayerCircle;

    @FXML
    private Circle thirdPlayerCircle;

    @FXML
    private Circle fourthPlayerCircle;

    private final BaseModelService baseModelService = new BaseModelService();
    private static final String[] userName = new String[MAX_PLAYERS];


    public BaseControllerService() {
    }

    public void moveToMainStarterView() throws IOException {
        initializeView("startup/blackjack-starter-view.fxml");
    }

    public void moveToGameView() throws IOException {
        int numberOfPlayer = checkNumberOfPlayers();
        if (numberOfPlayer > 0) {

            initializeView("startup/game-screen.fxml");

        } else {
            noPlayerName.setText("You must have at least one player name ");
        }
    }

    public void moveToInfoView() throws IOException {
        initializeView("startup/info-screen.fxml");
    }

    public void moveCardToHand(Object player) {

        int playerHandPositionX;
        int playerHandPositionY;
        String cardName;

        if (player instanceof Player) {
            Point playerHandPosition = playerCardPosition.get(currentPlayerIndex);
            playerHandPositionX = playerHandPosition.getX() + ((Player) player).getCardsAmount() * 10;
            playerHandPositionY = playerHandPosition.getY() - ((Player) player).getCardsAmount() * 20;
            cardName = ((Player) player).getLastCard().getCardType() + "_OF_" + ((Player) player).getLastCard().getSuit();
        } else {                 //TODO:
            cardName = "123"; // tu wywalic pozniej za tego else nizej, bo implementacji krupiera jeszcze nie ma ;))
            playerHandPositionX = 3;
            playerHandPositionY = 5;
        }
        /*else
        {
            Point playerHandPosition = playerCardPosition.get(4);
            playerHandPositionX = playerHandPosition.getX() + ((Croupier) player).getCardsAmount() * 10;
            playerHandPositionY = playerHandPosition.getY() - ((Croupier) player).getCardsAmount() * 20;
            cardName = ((Croupier) player).getLastCard().getCardType() + "_OF_" + ((Croupier) player).getLastCard().getSuit();
        }*/

        final boolean[] isFrontShowing = {true};

        ImageView back = new ImageView(backImage);
        back.setFitWidth(CARD_WIDTH);
        back.setFitHeight(CARD_HEIGHT);

        gamePane.getChildren().add(back);

        Point middleTablePos = playerCardPosition.get(5);
        int middleTablePosX = middleTablePos.getX();
        int middleTablePosY = middleTablePos.getY();

        TranslateTransition transition = new TranslateTransition(Duration.millis(500), back);
        transition.setFromX(DECK_CARD_POS_X);
        transition.setFromY(DECK_CARD_POS_Y);

        transition.setToX(middleTablePosX);
        transition.setToY(middleTablePosY);
        transition.play();

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(500), back);
        rotateTransition.setAxis(Rotate.Y_AXIS);
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(90);
        rotateTransition.play();
        rotateTransition.setOnFinished(event1 -> {

            if (isFrontShowing[0]) {

                back.setImage(getCardImage(cardName));
                rotateTransition.setFromAngle(90);
                rotateTransition.setToAngle(360);
                rotateTransition.play();
                rotateTransition.setOnFinished(event2 -> {

                    transition.setFromX(middleTablePosX);
                    transition.setFromY(middleTablePosY);
                    transition.setToX(playerHandPositionX);
                    transition.setToY(playerHandPositionY);
                    transition.play();
                });
                isFrontShowing[0] = false;
            }
        });
    }

    public void leaveInfoScreen(MouseEvent event) throws IOException {
        moveToMainStarterView();
    }

    public void hit(MouseEvent event) {
        if (betSum == 0) {
            return;
        }

        Player player = baseModelService.returnPlayer(currentPlayerIndex);
        player.placeBet(betSum);


        if (player.getCardsAmount() == 0) {
            player.takeCard();
        }

        player.takeCard();
        moveCardToHand(player);
        changePlayerMove();
    }

    public void leaveGame() throws IOException {

        Player player = baseModelService.returnPlayer(currentPlayerIndex);
        currentBet[currentPlayerIndex].setText("");
        player.setPlaying(false);

        if (returnAmountOfPlayingPlayers() == 0) {
            moveToMainStarterView();
            return;
        }

        changePlayerMove();
    }

    public void stand() {
        Player player = baseModelService.returnPlayer(currentPlayerIndex);
        if (player.getBetAmount() == 0) {
            return;
        }
        player.setStanding(true);
        changePlayerMove();
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
        currentBet = new Text[]{firstPlayerBet, secondPlayerBet, thirdPlayerBet, fourthPlayerBet};
        baseModelService.getCroupier().takeCard();
    }

    private void changePlayerMove() {
        betSum = 0;
        currentPlayerIndex = returnNextPlayingPlayersIndex();
        displayIsPlaying(currentPlayerIndex);
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

    private int returnNextPlayingPlayersIndex() {
        while (true) {
            currentPlayerIndex++;
            if (currentPlayerIndex >= MAX_PLAYERS) {
                currentPlayerIndex = 0;
                prepareNextRound();
            }

            Player player = baseModelService.returnPlayer(currentPlayerIndex);
            if (player.isPlaying()) {
                return currentPlayerIndex;
            }
        }
    }

    private void prepareNextRound() {
        drawCroupierCardsWhenLessThanSixteen();
        verifyRoundResults();
        System.out.println("Croupier hands value: " + baseModelService.getCroupier().getSumOfCardsValue());
        for (Player player : baseModelService.getPlayers()) {
            System.out.println("Players account balance: " + player.getAccountBalance() + " | Players hands value " + player.getSumOfCardsValue());
        }
        cleanMoneyFields();
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
        if (textField.getText().length() >= 7) return textField.getText().substring(0, 7).toUpperCase();
        else return textField.getText().toUpperCase();
    }

    private void initializeView(String fileName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(fileName));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 847.09);
        stage.setTitle("Blackjack!");
        stage.getIcons().add(new Image("startup/coin.png"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        BaseControllerService controller = fxmlLoader.getController();
        controller.setStage(stage);
    }

    public int checkNumberOfPlayers() {
        userName[0] = getUserName(firstUserName);
        userName[1] = getUserName(secondUserName);
        userName[2] = getUserName(thirdUserName);
        userName[3] = getUserName(fourthUserName);


        int playerCounter = 0;
        for (int i = 0; i < MAX_PLAYERS; i++) {
            if (!Objects.equals(userName[i], "")) {
                playerCounter++;
            }
        }
        return playerCounter;
    }

    public void verifyRoundResults() {
        var croupierHandValue = baseModelService.getCroupier().getSumOfCardsValue();

        for (Player player : baseModelService.getPlayers()) {
            if (croupierHandValue < player.getSumOfCardsValue()) {
                player.setAccountBalance(player.getAccountBalance() + player.getBetAmount() * BaseModelService.WIN_MULTIPLIER);
            } else if (croupierHandValue == player.getSumOfCardsValue()) {
                player.setAccountBalance(player.getAccountBalance() + player.getBetAmount());
            }
            player.setBetAmount(0);
        }
    }

    public void assignPlayersNames() {
        dataFirstPlayer.setText(userName[0] + '\n' + baseModelService.returnPlayer(0).getAccountBalance() + " $");
        dataSecondPlayer.setText(userName[1] + '\n' + baseModelService.returnPlayer(1).getAccountBalance() + " $");
        dataThirdPlayer.setText(userName[2] + '\n' + baseModelService.returnPlayer(2).getAccountBalance() + " $");
        dataFourthPlayer.setText(userName[3] + '\n' + baseModelService.returnPlayer(3).getAccountBalance() + " $");
    }

    public Image getCardImage(String cardName) {
        Image cardImage;
        cardImage = new Image("cards/" + cardName + ".png");
        return cardImage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (url.getPath().endsWith("startup/game-screen.fxml")) {
            initialize();
            assignPlayersNames();
            displayIsPlaying(currentPlayerIndex);
            System.out.println("Initializing specific FXML");
        }
    }

    public void displayIsPlaying(int currentPlayer) {

        if (currentPlayer < MAX_PLAYERS) {
            gameStatus.setText(userName[currentPlayer] + " is playing");

            Light.Distant light = new Light.Distant();
            light.setAzimuth(-135.0);

            Lighting lighting = new Lighting();
            lighting.setLight(light);
            lighting.setSurfaceScale(5.0);

            DropShadow dropShadow = new DropShadow();
            dropShadow.setColor(Color.BLACK);
            dropShadow.setRadius(10.0);
            dropShadow.setOffsetX(5.0);
            dropShadow.setOffsetY(5.0);

            if (currentPlayer == 0) {
                firstPlayerCircle.setStroke(Color.YELLOW);
                firstPlayerCircle.setEffect(lighting);
                firstPlayerCircle.setStrokeWidth(3);
            } else {
                firstPlayerCircle.setStroke(Color.BLACK);
                firstPlayerCircle.setStrokeWidth(0);
            }

            if (currentPlayer == 1) {
                secondPlayerCircle.setStroke(Color.YELLOW);
                secondPlayerCircle.setEffect(lighting);
                secondPlayerCircle.setStrokeWidth(3);
            } else {
                secondPlayerCircle.setStroke(Color.BLACK);
                secondPlayerCircle.setStrokeWidth(0);
            }

            if (currentPlayer == 2) {
                thirdPlayerCircle.setStroke(Color.YELLOW);
                thirdPlayerCircle.setEffect(lighting);
                thirdPlayerCircle.setStrokeWidth(3);
            } else {
                thirdPlayerCircle.setStroke(Color.BLACK);
                thirdPlayerCircle.setStrokeWidth(0);
            }

            if (currentPlayer == 3) {
                fourthPlayerCircle.setStroke(Color.YELLOW);
                fourthPlayerCircle.setEffect(lighting);
                fourthPlayerCircle.setStrokeWidth(3);
            } else {
                fourthPlayerCircle.setStroke(Color.BLACK);
                fourthPlayerCircle.setStrokeWidth(0);
            }
        }

    }

    public void drawCroupierCardsWhenLessThanSixteen() {
        baseModelService.getCroupier().keepDrawingIfsumOfCardsValueIsLessThanSixteen();
    }
}
