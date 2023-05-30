package com.pio.models;

public class BaseModelService {

    public static int STARTING_MONEY = 8000;
    public static int MAX_PLAYERS = 4;
    private final Player[] players = new Player[MAX_PLAYERS];

    public BaseModelService() {
        setSettings();
    }

    private void setSettings() {
        for (int i = 0; i < MAX_PLAYERS; i++) {
            players[i] = new Player(STARTING_MONEY);
        }
    }

    public Player returnPlayer(int index) {
        return players[index];
    }
}
