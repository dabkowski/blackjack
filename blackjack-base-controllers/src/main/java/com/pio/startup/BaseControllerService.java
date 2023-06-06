package com.pio.startup;

import com.pio.models.BaseModelService;
import com.pio.models.Croupier;
import com.pio.models.Player;
import javafx.animation.*;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
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
import java.util.Random;

import javafx.scene.input.MouseEvent;


public class BaseControllerService implements Initializable {
    public static final int RADIUS_HELP_BUTTON = 36;
    public static final double PIXEL_FOR_SCALING = 4.5;
    public static int MAX_PLAYERS = 4;

    public static int CROUPIER_HAND_ID = 4;

    public static int TABLE_CENTER_ID = 5;

    public static int DIFF_BETWEEN_CROUPIER_CARDS = 50;

    public static int DIFF_BETWEEN_PLAYER_CARDS_X = 10;

    public static int DIFF_BETWEEN_PLAYER_CARDS_Y = 20;

    public static int DECK_CARD_POS_X = 326;

    public static int DECK_CARD_POS_Y = 185;

    public static int CARD_HEIGHT = 70;

    public static int CARD_WIDTH = 50;

    public static int WINNING_AMOUNT = 21;

    public static int MIN_CROUPIER_DECK_AMOUNT = 16;

    public static int AMOUNT_OF_CARDS_ON_START = 2;

    public static int NICKNAME_LENGTH_SIZE = 7;

    public static int HIDDEN_CART = 0;

    public static String EMPTY_FIELD = "";

    public static String BACK_OF_CARD_NAME = "back";

    public static String INFO_SCREEN_PATH = "startup/info-screen.fxml";

    public static String GAME_SCREEN_PATH = "startup/game-screen.fxml";

    public static String START_SCREEN_PATH = "startup/blackjack-starter-view.fxml";

    public static String WARNING_IMAGE_PATH = "startup/warning.png";

    public static String COLOR_OF_THE_CIRCLE_MOVE = "YELLOW";

    public static String WARNING_MESSAGE = "You must have at least one player name";

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

    @FXML
    private ImageView warningImage;

    private final String[] samplesImages = {"startup/joker1.png", "startup/joker2.png", "startup/joker3.png", "startup/joker4.png", "startup/joker5.png"};

    private final List<Point> playerCardPosition = new ArrayList<>() {{
        add(new Point(222, 346));
        add(new Point(363, 449));
        add(new Point(663, 449));
        add(new Point(804, 346));
        add(new Point(440, 260));
        add(new Point(535, 440));
    }};

    private final List<ImageView> imageCards = new ArrayList<>();

    private Text[] currentBet;

    private Stage stage;

    private int currentPlayerIndex = 0;
    private static int currentPlayerInGame = 0;

    private int betSum = 0;

    @FXML
    private Label noPlayerName;

    @FXML
    private AnchorPane gamePane;

    private final Image backImage = new Image("cards/back.png");

    private ImageView backCard = new ImageView(backImage);

    @FXML
    private Circle firstPlayerCircle;

    @FXML
    private Circle secondPlayerCircle;

    @FXML
    private Circle thirdPlayerCircle;

    @FXML
    private Circle fourthPlayerCircle;

    @FXML
    private ImageView playerOneWin;

    @FXML
    private ImageView playerTwoWin;

    @FXML
    private ImageView playerThreeWin;

    @FXML
    private ImageView playerFourWin;

    @FXML
    private ImageView playerOneLoss;

    @FXML
    private ImageView playerTwoLoss;

    @FXML
    private ImageView playerThreeLoss;

    @FXML
    private ImageView playerFourLoss;

    @FXML
    private ImageView playerOneDraw;

    @FXML
    private ImageView playerTwoDraw;

    @FXML
    private ImageView playerThreeDraw;

    @FXML
    private ImageView playerFourDraw;

    @FXML
    private Pane helpArea;

    @FXML
    private ImageView HelpImage;

    @FXML
    private Label helpText;

    @FXML
    private HBox helpBox;

    public int roundCounter = 0;

    public boolean helpClicked = false;
    int hBoxWidth = 370;

    private final BaseModelService baseModelService = new BaseModelService();

    private static final String[] userName = new String[MAX_PLAYERS];

    private boolean canIClickButtons = true;

    public BaseControllerService() {
    }

    public void moveToMainStarterView() throws IOException {
        initializeView(START_SCREEN_PATH);
    }

    public void moveToGameView() throws IOException {

        if (checkNumberOfPlayers() > 0) {

            initializeView(GAME_SCREEN_PATH);
        } else {
            noPlayerName.setText(WARNING_MESSAGE);
            Image warning = new Image(WARNING_IMAGE_PATH);
            warningImage.setImage(warning);
        }
    }

    public void moveToInfoView() throws IOException {
        initializeView(INFO_SCREEN_PATH);
    }

    public void moveCardToHand(Object player) {

        ImageView newCard = new ImageView(backImage);
        newCard.setFitWidth(CARD_WIDTH);
        newCard.setFitHeight(CARD_HEIGHT);

        imageCards.add(newCard);

        int playerHandPositionX;
        int playerHandPositionY;
        String cardName;

        if (player instanceof Player) {
            ((Player) player).addCardImage(newCard);
            Point playerHandPosition = playerCardPosition.get(currentPlayerIndex);
            playerHandPositionX = playerHandPosition.getX() + ((Player) player).getCardsAmount() * DIFF_BETWEEN_PLAYER_CARDS_X;
            playerHandPositionY = playerHandPosition.getY() - ((Player) player).getCardsAmount() * DIFF_BETWEEN_PLAYER_CARDS_Y;
            cardName = (((Player) player).getLastCard().getCardType() + "_OF_" + ((Player) player).getLastCard().getSuit()).toLowerCase();
        } else {
            Point playerHandPosition = playerCardPosition.get(CROUPIER_HAND_ID);
            playerHandPositionX = playerHandPosition.getX() + ((Croupier) player).getCardsAmount() * DIFF_BETWEEN_CROUPIER_CARDS;
            playerHandPositionY = playerHandPosition.getY();
            if (((Croupier) player).getCardsAmount() == 1) {
                cardName = BACK_OF_CARD_NAME;
                backCard = newCard;
            } else {
                cardName = (((Croupier) player).getLastCard().getCardType() + "_OF_" + ((Croupier) player).getLastCard().getSuit()).toLowerCase();
            }
        }
        final boolean[] isFrontShowing = {true};

        gamePane.getChildren().add(newCard);

        Point middleTablePos = playerCardPosition.get(TABLE_CENTER_ID);
        int middleTablePosX = middleTablePos.getX();
        int middleTablePosY = middleTablePos.getY();

        TranslateTransition moveCard = new TranslateTransition(Duration.millis(500), newCard);
        moveCard.setFromX(DECK_CARD_POS_X);
        moveCard.setFromY(DECK_CARD_POS_Y);

        moveCard.setToX(middleTablePosX);
        moveCard.setToY(middleTablePosY);
        moveCard.play();

        RotateTransition rotateCard = new RotateTransition(Duration.millis(500), newCard);
        rotateCard.setAxis(Rotate.Y_AXIS);
        rotateCard.setFromAngle(0);
        rotateCard.setToAngle(90);
        rotateCard.play();
        rotateCard.setOnFinished(event1 -> {

            if (isFrontShowing[0]) {

                newCard.setImage(getCardImage(cardName));
                rotateCard.setFromAngle(90);
                rotateCard.setToAngle(360);
                rotateCard.play();
                rotateCard.setOnFinished(event2 -> {

                    moveCard.setFromX(middleTablePosX);
                    moveCard.setFromY(middleTablePosY);
                    moveCard.setToX(playerHandPositionX);
                    moveCard.setToY(playerHandPositionY);
                    moveCard.play();
                });
                isFrontShowing[0] = false;
            }
        });
    }

    public void leaveInfoScreen() throws IOException {
        moveToMainStarterView();
    }

    public void cursorChangeToHandIfEntered(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setCursor(Cursor.HAND);

        scaleButtonIfEntered(button);
    }

    private void scaleButtonIfEntered(Button button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(50), button);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);
        scaleTransition.play();

        button.setOnMouseExited(exitedEvent -> {
            scaleTransition.stop();
            button.setScaleX(1.0);
            button.setScaleY(1.0);
        });
    }

    public void turnAroundInvisibleCroupierCard(Croupier croupier) {

        final boolean[] isFrontShowing = {true};

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(1500), backCard);
        rotateTransition.setAxis(Rotate.Y_AXIS);
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(90);
        rotateTransition.play();
        rotateTransition.setOnFinished(event1 -> {

            if (isFrontShowing[0]) {
                String cardName = croupier.getCard(HIDDEN_CART).getCardType() + "_of_" + croupier.getCard(HIDDEN_CART).getSuit();
                backCard.setImage(getCardImage(cardName));
                rotateTransition.setFromAngle(90);
                rotateTransition.setToAngle(360);
                rotateTransition.play();
                isFrontShowing[0] = false;
                rotateTransition.setOnFinished(event2 -> {
                    verifyRoundResults();
                });
            }
        });

    }

    public void clearAllCardImages() {
        for (ImageView imageView : imageCards) {
            gamePane.getChildren().remove(imageView);
        }
        gamePane.getChildren().remove(backCard);
        imageCards.clear();
    }

    public void clearCardImagesForSpecificPlayer(Object player) {
        List<ImageView> playerCards;

        if (player instanceof Player) {
            playerCards = ((Player) player).getCardImages();
        } else {
            playerCards = ((Croupier) player).getCardImages();
        }

        for (ImageView imageView : playerCards) {
            gamePane.getChildren().remove(imageView);
        }
    }

    public void hit(MouseEvent event) {
        if (!canIClickButtons) {
            return;
        }

        if (helpClicked) handleTheHelpTextArea();

        Player player = baseModelService.returnPlayer(currentPlayerIndex);

        if (betSum == 0 && player.getBetAmount() == 0) {
            return;
        }

        if (player.getBetAmount() == 0) {
            player.placeBet(betSum);
        }

        if (player.getCardsAmount() == 0) {
            for (int i = 0; i < AMOUNT_OF_CARDS_ON_START; i++) {
                player.takeCard();
                moveCardToHand(player);
            }
            changePlayerMove();
            return;
        }

        player.takeCard();
        moveCardToHand(player);

        if (checkIfPlayerLost(player)) {
            player.setStanding(true);
            player.setBetAmount(0);

            if (checkIfAllPlayersFinishedRound()) {
                prepareNextRound();
                canIClickButtons = false;
            } else {
                changePlayerMove();
            }

        }


    }

    public void leaveGame() throws IOException {

        Player player = baseModelService.returnPlayer(currentPlayerIndex);
        clearCardImagesForSpecificPlayer(player);

        currentBet[currentPlayerIndex].setText(EMPTY_FIELD);
        player.setPlaying(false);

        if (returnAmountOfPlayingPlayers() == 0) {
            moveToMainStarterView();
            return;
        }

        if (checkIfAllPlayersFinishedRound()) {
            prepareNextRound();
            canIClickButtons = false;
        } else {
            changePlayerMove();
        }
    }

    public void stand() {
        if (!canIClickButtons) {
            return;
        }
        if (helpClicked) handleTheHelpTextArea();
        Player player = baseModelService.returnPlayer(currentPlayerIndex);
        if (player.getBetAmount() == 0) {
            return;
        }
        player.setStanding(true);

        if (checkIfAllPlayersFinishedRound()) {
            canIClickButtons = false;
            prepareNextRound();
        } else {
            changePlayerMove();
        }

    }

    public void add1000Chip(MouseEvent event) {
        if (!canIClickButtons) {
            return;
        }

        if (event.getButton() == MouseButton.PRIMARY) {
            betSum += 1000;
        } else if (event.getButton() == MouseButton.SECONDARY) {
            betSum -= 1000;
        }
        updateBetText(betSum);
    }

    public void add500Chip(MouseEvent event) {
        if (!canIClickButtons) {
            return;
        }

        if (event.getButton() == MouseButton.PRIMARY) {
            betSum += 500;
        } else if (event.getButton() == MouseButton.SECONDARY) {
            betSum -= 500;
        }
        updateBetText(betSum);
    }

    public void add200Chip(MouseEvent event) {
        if (!canIClickButtons) {
            return;
        }

        if (event.getButton() == MouseButton.PRIMARY) {
            betSum += 200;
        } else if (event.getButton() == MouseButton.SECONDARY) {
            betSum -= 200;
        }
        updateBetText(betSum);
    }

    public void add100Chip(MouseEvent event) {
        if (!canIClickButtons) {
            return;
        }

        if (event.getButton() == MouseButton.PRIMARY) {
            betSum += 100;
        } else if (event.getButton() == MouseButton.SECONDARY) {
            betSum -= 100;
        }
        updateBetText(betSum);
    }

    public void add50Chip(MouseEvent event) {
        if (!canIClickButtons) {
            return;
        }

        if (event.getButton() == MouseButton.PRIMARY) {
            betSum += 50;
        } else if (event.getButton() == MouseButton.SECONDARY) {
            betSum -= 50;
        }
        updateBetText(betSum);
    }

    public void add20Chip(MouseEvent event) {
        if (!canIClickButtons) {
            return;
        }

        if (event.getButton() == MouseButton.PRIMARY) {
            betSum += 20;
        } else if (event.getButton() == MouseButton.SECONDARY) {
            betSum -= 20;
        }
        updateBetText(betSum);
    }

    public void add10Chip(MouseEvent event) {
        if (!canIClickButtons) {
            return;
        }

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
        Croupier croupier = baseModelService.getCroupier();
        croupier.takeCard();
        moveCardToHand(croupier);
        croupier.takeCard();
        moveCardToHand(croupier);
        fadeInAllWinLossViewsImmediately();
    }

    private void fadeInAllWinLossViewsImmediately() {
        fadeInViewImmediately(playerOneWin);
        fadeInViewImmediately(playerTwoWin);
        fadeInViewImmediately(playerThreeWin);
        fadeInViewImmediately(playerFourWin);
        fadeInViewImmediately(playerOneLoss);
        fadeInViewImmediately(playerTwoLoss);
        fadeInViewImmediately(playerThreeLoss);
        fadeInViewImmediately(playerFourLoss);
        fadeInViewImmediately(playerOneDraw);
        fadeInViewImmediately(playerTwoDraw);
        fadeInViewImmediately(playerThreeDraw);
        fadeInViewImmediately(playerFourDraw);
    }

    private void changePlayerMove() {
        betSum = 0;
        currentPlayerIndex = returnNextPlayingPlayersIndex();
        displayIsPlaying(currentPlayerIndex);
    }

    private void cleanMoneyFields() {

        for (int i = 0; i < currentPlayerInGame; i++) {
            Player player = baseModelService.returnPlayer(i);
            if (player.isPlaying()) {
                currentBet[i].setText("0$");
            } else {
                currentBet[i].setText(EMPTY_FIELD);
            }
        }
    }

    private void updateBetText(int amount) {
        if (amount < 0) {
            betSum = 0;
            amount = 0;
        }

        Player player = baseModelService.returnPlayer(currentPlayerIndex);
        if (player.getBetAmount() > 0) {
            return;
        }
        if (player.getAccountBalance() < amount) {
            amount = player.getAccountBalance();
            betSum = amount;
        }

        currentBet[currentPlayerIndex].setText(amount + "$");
    }

    private int returnNextPlayingPlayersIndex() {
        while (true) {
            currentPlayerIndex++;

            if (currentPlayerIndex >= currentPlayerInGame) {

                currentPlayerIndex = 0;
                roundCounter++;

            }

            Player player = baseModelService.returnPlayer(currentPlayerIndex);
            if (player.isPlaying() && !player.isStanding()) {
                return currentPlayerIndex;
            }
        }
    }

    private boolean checkIfAllPlayersFinishedRound() {

        for (int i = 0; i < currentPlayerInGame; i++) {
            Player player = baseModelService.returnPlayer(i);
            if (player.isPlaying()) {
                if (!player.isStanding()) {
                    return false;
                }
            }
        }

        return true;
    }

    private void prepareNextRound() {
        Croupier croupier = baseModelService.getCroupier();
        turnAroundInvisibleCroupierCard(croupier);
        keepDrawingIfSumOfCardsValueIsLessThanSixteen(croupier);
    }

    private int returnAmountOfPlayingPlayers() {
        int onlinePlayers = 0;
        for (int i = 0; i < currentPlayerInGame; i++) {
            Player player = baseModelService.returnPlayer(i);
            if (player.isPlaying()) {
                onlinePlayers++;
                System.out.println(onlinePlayers);
            }
        }
        return onlinePlayers;
    }

    public String getUserName(TextField textField) {
        if (textField.getText().length() >= NICKNAME_LENGTH_SIZE)
            return textField.getText().substring(0, NICKNAME_LENGTH_SIZE).toUpperCase();
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

    public void setCurrentBetToEmpty() {
        for (int i = 0; i < MAX_PLAYERS; i++) {
            if (Objects.equals(userName[i], EMPTY_FIELD)) currentBet[i].setText(EMPTY_FIELD);
        }
    }

    public int checkNumberOfPlayers() {
        TextField[] names = {firstUserName, secondUserName, thirdUserName, fourthUserName};
        for (int i = 0; i < MAX_PLAYERS; i++) {
            userName[i] = getUserName(names[i]);

        }
        currentPlayerInGame = sortUserNames();
        baseModelService.getNumberOfPlayerInGame(currentPlayerInGame);

        return currentPlayerInGame;
    }

    public int sortUserNames() {
        int playerCounter = 0;
        int emptyFields = 0;

        for (int i = 0; i < MAX_PLAYERS - emptyFields; i++) {
            if (Objects.equals(BaseControllerService.userName[i], EMPTY_FIELD)) {

                emptyFields++;
                for (int j = i; j < MAX_PLAYERS - 1; j++) {
                    BaseControllerService.userName[j] = BaseControllerService.userName[j + 1];
                }
                BaseControllerService.userName[MAX_PLAYERS - 1] = EMPTY_FIELD;
                i--;
            } else {
                playerCounter++;
            }
        }

        return playerCounter;
    }

    public void verifyRoundResults() {
        var croupierHandValue = baseModelService.getCroupier().getSumOfCardsValue();
        for (Player player : baseModelService.getPlayers()) {
            var playerIndex = findPlayerIndex(player);
            if (croupierHandValue < player.getSumOfCardsValue() && player.getSumOfCardsValue() <= WINNING_AMOUNT && player.isPlaying()) {
                player.setAccountBalance(player.getAccountBalance() + player.getBetAmount() * BaseModelService.WIN_MULTIPLIER);
                triggerFadeInAnimation(playerIndex, RoundStatus.WIN);
            } else if (croupierHandValue == player.getSumOfCardsValue() && player.getSumOfCardsValue() <= WINNING_AMOUNT && player.isPlaying()) {
                player.setAccountBalance(player.getAccountBalance() + player.getBetAmount());
                triggerFadeInAnimation(playerIndex, RoundStatus.DRAW);
            } else {
                if (player.isPlaying()) {
                    triggerFadeInAnimation(playerIndex, RoundStatus.LOSS);
                }
            }
            roundCounter = 0;
            player.setBetAmount(0);
        }
    }

    private void triggerFadeInAnimation(int playerIndex, RoundStatus roundStatus) {
        switch (playerIndex) {
            case 0 ->
                    fadeInView(RoundStatus.WIN.equals(roundStatus) ? playerOneWin : RoundStatus.LOSS.equals(roundStatus) ? playerOneLoss : playerOneDraw);
            case 1 ->
                    fadeInView(RoundStatus.WIN.equals(roundStatus) ? playerTwoWin : RoundStatus.LOSS.equals(roundStatus) ? playerTwoLoss : playerTwoDraw);
            case 2 ->
                    fadeInView(RoundStatus.WIN.equals(roundStatus) ? playerThreeWin : RoundStatus.LOSS.equals(roundStatus) ? playerThreeLoss : playerThreeDraw);
            case 3 ->
                    fadeInView(RoundStatus.WIN.equals(roundStatus) ? playerFourWin : RoundStatus.LOSS.equals(roundStatus) ? playerFourLoss : playerFourDraw);
        }
    }

    private enum RoundStatus {
        WIN, LOSS, DRAW
    }

    private int findPlayerIndex(Player player) {
        var players = baseModelService.getPlayers();
        int index = 0;
        for (Player foundPlayer : players) {
            if (foundPlayer.equals(player)) {
                break;
            }
            index++;
        }
        return index;
    }

    public void assignPlayersNames() {
        Label[] dataPlayers = {dataFirstPlayer, dataSecondPlayer, dataThirdPlayer, dataFourthPlayer};

        for (int i = 0; i < currentPlayerInGame; i++) {
            Player player = baseModelService.returnPlayer(i);
            if (player.isPlaying()) {
                dataPlayers[i].setText(userName[i] + '\n' + baseModelService.returnPlayer(i).getAccountBalance() + " $");
            } else {
                dataPlayers[i].setText(userName[i] + "\nLEFT");
            }
        }
    }

    public Image getCardImage(String cardName) {
        Image cardImage;
        cardImage = new Image("cards/" + cardName.toLowerCase() + ".png");
        return cardImage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (url.getPath().endsWith(GAME_SCREEN_PATH)) {
            initialize();
            assignPlayersNames();
            displayIsPlaying(currentPlayerIndex);
            initializeHelpButton();
            setCurrentBetToEmpty();

        }
    }

    public void initializeHelpButton() {
        helpBox.setTranslateX(-hBoxWidth);
        helpBox.setStyle("-fx-padding: 5px;" + " -fx-background-radius: 10px; -fx-background-color: #D0A616;");
        helpText.setStyle("-fx-text-fill: #5E4300; -fx-padding-left: 5px; ");

        Circle clipCircle = new Circle();
        double radius = RADIUS_HELP_BUTTON;
        clipCircle.setRadius(radius - PIXEL_FOR_SCALING);
        clipCircle.setCenterX(radius);
        clipCircle.setCenterY(radius);
        clipCircle.setStroke(Color.BLACK);
        clipCircle.setStrokeWidth(4);
        helpArea.setClip(clipCircle);
    }

    public void displayIsPlaying(int currentPlayer) {

        if (currentPlayer < currentPlayerInGame) {
            Circle[] playerCircles = {firstPlayerCircle, secondPlayerCircle, thirdPlayerCircle, fourthPlayerCircle};

            for (int i = 0; i < currentPlayerInGame; i++) {
                if (currentPlayer == i) {
                    playerCircles[i].setStroke(Color.valueOf(COLOR_OF_THE_CIRCLE_MOVE));
                    playerCircles[i].setEffect(createLightingEffect());
                    playerCircles[i].setStrokeWidth(3);
                } else {
                    playerCircles[i].setStroke(Color.BLACK);
                    playerCircles[i].setStrokeWidth(0);
                }
            }
        }
        assignPlayersNames();
    }

    private Lighting createLightingEffect() {
        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135.0);

        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(5.0);

        return lighting;
    }


    private boolean checkIfPlayerLost(Player player) {
        return player.getSumOfCardsValue() > WINNING_AMOUNT;
    }

    public void keepDrawingIfSumOfCardsValueIsLessThanSixteen(Croupier croupier) {
        while (croupier.getSumOfCardsValue() < MIN_CROUPIER_DECK_AMOUNT) {
            croupier.takeCard();
            moveCardToHand(croupier);
        }
        if (croupier.getSumOfCardsValue() > WINNING_AMOUNT) {
            croupier.setSumOfCardsValue(0);
        }
    }

    @FXML
    private void startWithKeyEnter(KeyEvent event) throws IOException {

        if (event.getCode() == KeyCode.ENTER) {
            moveToGameView();
        }

    }

    private void fadeInView(ImageView view) {
        FadeTransition ft = new FadeTransition(Duration.millis(2250), view);

        Path path = new Path();
        path.getElements().add(new MoveTo(40f, 40f));
        path.getElements().add(new LineTo(60, -100));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(4000));
        pathTransition.setPath(path);
        pathTransition.setNode(view);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);
        pathTransition.play();
        pathTransition.setOnFinished(event -> {

            assignPlayersNames();

            for (Player player : baseModelService.getPlayers()) {
                if (player.getAccountBalance() == 0) {
                    player.setPlaying(false);
                }
                player.clearCards();
                player.setStanding(false);
            }

            Croupier croupier = baseModelService.getCroupier();
            croupier.clearCards();

            cleanMoneyFields();
            clearAllCardImages();

            for (int i = 0; i < AMOUNT_OF_CARDS_ON_START; i++) {
                croupier.takeCard();
                moveCardToHand(croupier);
            }
            currentPlayerIndex = returnFirstPlayingPlayer();
            if (currentPlayerIndex == -1) {
                try {
                    moveToMainStarterView();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            displayIsPlaying(currentPlayerIndex);
            canIClickButtons = true;
        });

        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    private void fadeInViewImmediately(ImageView view) {
        FadeTransition ft = new FadeTransition(Duration.millis(1), view);

        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    public int returnFirstPlayingPlayer() {

        for (int i = 0; i < currentPlayerInGame; i++) {
            Player player = baseModelService.returnPlayer(i);
            if (player.isPlaying()) {
                return i;
            }
        }
        return -1;
    }

    @FXML
    void tipsOnMouseEntered(MouseEvent event) {
        helpArea.setStyle("-fx-background-color: yellow;");

    }

    @FXML
    void tipsOnMouseExited(MouseEvent event) {
        helpArea.setStyle("-fx-background-color: transparent;");
    }

    public Image setJokerImage() {
        Random random = new Random();
        int pickedNumber = random.nextInt(samplesImages.length);
        return new Image(samplesImages[pickedNumber]);
    }

    @FXML
    void clickOnTipButton(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY || event.getButton() == MouseButton.SECONDARY) {
            handleTheHelpTextArea();
        }

    }

    public void handleTheHelpTextArea() {
        if (!helpClicked) {
            helpClicked = true;
            HelpImage.setImage(setJokerImage());
            String tipMessage = null;
            if (roundCounter == 0) {
                tipMessage = """
                        -Click chip to set bet amount.

                        -Right-click to increase amount

                        -Left-click to decrease.

                        -Click "Hit"  to bet.""";
            } else if (roundCounter == 1) {
                tipMessage = """
                        -Click "Hit" to receive a card

                        -Click "STAND" to pass your turn""";
            }

            helpText.setText(tipMessage);
            TranslateTransition transition = new TranslateTransition(Duration.seconds(1.5), helpBox);
            transition.setToX(0);
            transition.play();
        } else {
            helpClicked = false;
            TranslateTransition transition = new TranslateTransition(Duration.seconds(1.5), helpBox);
            transition.setToX(-hBoxWidth);
            transition.play();
            helpText.setText("");
        }
    }
}




