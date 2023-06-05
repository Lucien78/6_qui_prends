package com.example._6_qui_prends.Player;

import com.example._6_qui_prends.Card.Card;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private List<Card> hand;
    private int score;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.hand = new ArrayList<>();  // Initialize 'hand' as a new ArrayList
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }

    public void addScore(int additionalScore) {
        if (additionalScore < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        }
        this.score += additionalScore;
    }

    public List<Card> getHand() {
        return this.hand;
    }

    public void receiveCards(List<Card> cards) {
        if (cards == null || cards.isEmpty()) {
            throw new IllegalArgumentException("Cards cannot be null or empty");
        }
        this.hand.addAll(cards);
    }

    public Card playCard(int cardIndex) {
        if (cardIndex < 0 || cardIndex >= this.hand.size()) {
            throw new IllegalArgumentException("Invalid card index");
        }
        return this.hand.remove(cardIndex);
    }



}
