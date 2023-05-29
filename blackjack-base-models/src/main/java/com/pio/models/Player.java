package com.pio.models;

public class Player {
    private final int MAX_CARDS = 11;
    private int accountBalance;
    private int betAmount;
    private boolean isPlaying = true;
    private final Card[] cards = new Card[MAX_CARDS];
    private int cardsAmount = 0;

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
    public void showCards() {
        for(int i=0; i<cardsAmount; i++) {
            System.out.println(i+". " + cards[i].getCardType() + " " + cards[i].getSuit() + " " + cards[i].getCardValue());
        }
    }
    public void takeCard() {

        Card card = new Card();

        card.generateRandomCard();

        cards[cardsAmount++] = card;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
