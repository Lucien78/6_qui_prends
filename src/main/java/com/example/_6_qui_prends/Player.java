package com.example._6_qui_prends;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Card> hand;
    private List<Card> takenCards;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.takenCards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addToHand(Card card) {
        hand.add(card);
    }

    public Card playCard(int index) {
        if (index < 0 || index >= hand.size()) {
            throw new IllegalArgumentException("Invalid card index.");
        }
        Card card = hand.get(index);
        hand.remove(index);
        return card;
    }

    public void takeCards(List<Card> cards) {
        takenCards.addAll(cards);
    }

    public int getScore() {
        return takenCards.stream().mapToInt(Card::getBulls).sum();
    }

    public List<Card> getHand() {
        return hand;
    }

    public List<Card> getTakenCards() {
        return takenCards;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", hand=" + hand +
                ", takenCards=" + takenCards +
                '}';
    }
}


v j,dvjkvndkjlnvkjlnvdkl