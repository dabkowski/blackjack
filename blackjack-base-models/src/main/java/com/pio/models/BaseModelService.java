package com.pio.models;

public class BaseModelService {

    public static int STARTING_MONEY = 8000;
    public static int MAX_PLAYERS = 4;
    public static int WIN_MULTIPLIER = 2;
    private final Player[] players = new Player[MAX_PLAYERS];
    private final Croupier croupier = new Croupier();

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

    public Player[] getPlayers() {
        return players;
    }

    public Croupier getCroupier() {
        return croupier;
    }
}
