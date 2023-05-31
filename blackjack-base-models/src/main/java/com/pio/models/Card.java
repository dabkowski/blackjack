package com.pio.models;

import java.util.Arrays;
import java.util.Random;

public class Card {

    private int cardValue;
    private CardType cardType;
    private Suit suit;

    public int getCardValue() {
        return cardValue;
    }

    public CardType getCardType() {
        return cardType;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public void setSuit(Suit suits) {
        this.suit = suits;
    }

    public void generateRandomCard() {
        Random rand = new Random();

        setCardType(generateRandomCardType(rand));
        setSuit(generateRandomSuit(rand));
        setCardValueFromCardType();
    }

    private void setCardValueFromCardType() {
        cardValue = cardType.ordinal() + 2;
        if (cardValue > 11) {
            cardValue = 10;
        }
    }

    private Suit generateRandomSuit(Random rand) {
        return Arrays.stream(Suit.values())
                .skip(rand.nextInt(Suit.values().length))
                .findFirst()
                .orElse(null);
    }

    private CardType generateRandomCardType(Random rand) {
        return Arrays.stream(CardType.values())
                .skip(rand.nextInt(CardType.values().length))
                .findFirst()
                .orElse(null);
    }
}
