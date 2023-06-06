package com.pio.models;

public class BaseModelService {

    public static int STARTING_MONEY = 8000;
    private static int currentPlayerInGame = 0;
    public static int WIN_MULTIPLIER = 2;
    private final Player[] players = new Player[currentPlayerInGame];
    private final Croupier croupier = new Croupier();

    public BaseModelService() {
        setSettings();
    }

    private void setSettings() {
        for (int i = 0; i < currentPlayerInGame; i++) {
            players[i] = new Player(STARTING_MONEY);
        }
    }

    public Player returnPlayer(int index) {
        return players[index];
    }

    public Player[] getPlayers() {
        return players;
    }

    public Croupier getCroupier() {
        return croupier;
    }

    public void getNumberOfPlayerInGame(int playerInGame) {
        currentPlayerInGame = playerInGame;
    }
}
