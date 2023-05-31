package com.pio.models;

public class Croupier {

    private final int MAX_CARDS = 11;
    private final Card[] cards = new Card[MAX_CARDS];
    private int cardsAmount = 0;
    private int sumOfCardsValue = 0;

    public void takeCard() {
        Card card = new Card();
        card.generateRandomCard();
        cards[cardsAmount++] = card;
        sumOfCardsValue += card.getCardValue();
    }

    public void keepDrawingIfsumOfCardsValueIsLessThanSixteen() {
        while (sumOfCardsValue < 16) {
            takeCard();
        }
        if (sumOfCardsValue > 21) {
            sumOfCardsValue = 0;
        }
    }

    public Card[] getCards() {
        return cards;
    }

    public void setCardsAmount(int cardsAmount) {
        this.cardsAmount = cardsAmount;
    }

    public int getSumOfCardsValue() {
        return sumOfCardsValue;
    }

    public void setSumOfCardsValue(int sumOfCardsValue) {
        this.sumOfCardsValue = sumOfCardsValue;
    }
}
