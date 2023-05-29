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
        if(cardType == CardType.ACE)
            cardValue = 11;
        else if(cardType == CardType.TWO) {
            cardValue = 2;
        }
        else if(cardType == CardType.THREE) {
            cardValue = 3;
        }
        else if(cardType == CardType.FOUR) {
            cardValue = 4;
        }
        else if(cardType == CardType.FIVE) {
            cardValue = 5;
        }
        else if(cardType == CardType.SIX) {
            cardValue = 6;
        }
        else if(cardType == CardType.SEVEN) {
            cardValue = 7;
        }
        else if(cardType == CardType.EIGHT) {
            cardValue = 8;
        }
        else if(cardType == CardType.NINE) {
            cardValue = 9;
        }
        else {
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
