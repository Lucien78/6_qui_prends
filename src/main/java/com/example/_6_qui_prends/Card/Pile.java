package com.example._6_qui_prends.Card;

import com.example._6_qui_prends.Card.Card;

import java.util.ArrayList;
import java.util.List;

public class Pile {
    private static final int MAX_SIZE = 5;
    private List<Card> cards;

    public Pile() {
        this.cards = new ArrayList<>();
    }

    public Pile(Card startingCard) {
        this.cards = new ArrayList<>();
        this.cards.add(startingCard);
    }


    public List<Card> getCards() {
        return this.cards;
    }

    public int getBullheadCount() {
        return cards.stream().mapToInt(Card::getBullheadCount).sum();
    }

    public boolean isFull() {
        return this.cards.size() == MAX_SIZE;
    }

    public void addCard(Card card) {
        if (this.cards.size() >= MAX_SIZE) {
            throw new IllegalStateException("Pile is full");
        }
        this.cards.add(card);
    }

    public void clear() {
        this.cards.clear();
    }
    public Card getTopCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Cannot get top card from an empty pile");
        }
        return cards.get(cards.size() - 1);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            sb.append(card.toString());
            sb.append("\n");  // Ajoute une nouvelle ligne après chaque carte
        }
        return sb.toString();
    }
}
