package com.pio.models;

public class Player {
    private final int MAX_CARDS = 11;
    private int accountBalance;
    private int betAmount;
    private boolean isPlaying = true;
    private final Card[] cards = new Card[MAX_CARDS];
    private int cardsAmount = 0;
    private int sumOfCardsValue = 0;
    private boolean isStanding = false;

    public int getCardsAmount() {
        return cardsAmount;
    }

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

    public void takeCard() {

        Card card = new Card();

        card.generateRandomCard();

        cards[cardsAmount++] = card;

        sumOfCardsValue += card.getCardValue();

        if (sumOfCardsValue > 21) {
            sumOfCardsValue = 0;
        }
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

    public Card getLastCard() {
        return cards[cardsAmount - 1];
    }

    public int getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }

    public int getSumOfCardsValue() {
        return sumOfCardsValue;
    }

    public void setSumOfCardsValue(int sumOfCardsValue) {
        this.sumOfCardsValue = sumOfCardsValue;
    }
}
