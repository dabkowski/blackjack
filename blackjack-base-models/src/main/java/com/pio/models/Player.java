package com.pio.models;

import javafx.scene.image.ImageView;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private int accountBalance;
    private int betAmount;
    private boolean isPlaying = true;
    private List<Card> cards = new ArrayList<>();
    private List<ImageView> cardImages = new ArrayList<>();
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

        cards.add(card);
        cardsAmount++;

        sumOfCardsValue += card.getCardValue();
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
        return cards.get(cardsAmount - 1);
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

    public List<ImageView> getCardImages() {
        return cardImages;
    }

    public boolean isStanding() {
        return isStanding;
    }

    public void clearCards(){
        cardsAmount = 0;
        cardImages.clear();
        cards.clear();
        sumOfCardsValue = 0;
    }
}
