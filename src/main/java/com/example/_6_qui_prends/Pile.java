package com.example._6_qui_prends;

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
        return cards.toString();
    }
}
