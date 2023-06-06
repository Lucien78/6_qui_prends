package com.example._6_qui_prends.Card;

import com.example._6_qui_prends.Card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Deck {
    private List<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>();
        IntStream.rangeClosed(1, 104).forEach(i -> cards.add(new Card(i)));
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("No cards left in the deck");
        }
        return cards.remove(cards.size() - 1);
    }

    public List<Card> drawCards(int count) {
        List<Card> drawnCards = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            drawnCards.add(drawCard());
        }
        return drawnCards;
    }

}
