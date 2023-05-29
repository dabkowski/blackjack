package com.pio.models;

public class Player {
    private int accountBalance;

    private int betAmount;

    private boolean isPlaying = true;

    private boolean isStanding = false;

    public Player(int accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void placeBet(int amount) {
        if (amount >= accountBalance) {
            amount = accountBalance;
        }

        if (amount <= 0) {
            return;
        }

        betAmount += amount;
        accountBalance -= amount;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public void setStanding(boolean standing) {
        isStanding = standing;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
