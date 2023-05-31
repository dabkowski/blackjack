package com.pio.models;

import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Croupier {
    private List<Card> cards = new ArrayList<>();
    private List<ImageView> cardImages = new ArrayList<>();
    private int cardsAmount = 0;
    private int sumOfCardsValue = 0;

    public void takeCard() {
        Card card = new Card();
        card.generateRandomCard();

        cards.add(card);
        cardsAmount++;

        sumOfCardsValue += card.getCardValue();
    }

    public void showCards(){
        for (int i = 0; i < cards.size(); i++){
            Card card = cards.get(i);
            System.out.println(card.getCardType() + " | " + card.getCardValue());
        }
    }

    public int getSumOfCardsValue() {
        return sumOfCardsValue;
    }

    public void setSumOfCardsValue(int sumOfCardsValue) {
        this.sumOfCardsValue = sumOfCardsValue;
    }

    public int getCardsAmount() {
        return cardsAmount;
    }

    public Card getLastCard() {
        return cards.get(cardsAmount - 1);
    }

    public List<ImageView> getCardImages() {
        return cardImages;
    }

    public void clearCards(){
        cardsAmount = 0;
        cardImages.clear();
        cards.clear();
        sumOfCardsValue = 0;
    }

    public Card getCard(int index){
        return cards.get(index);
    }
}
