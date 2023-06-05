package com.example._6_qui_prends.Player;

import com.example._6_qui_prends.Card.Card;

import java.util.Comparator;

public class AIPlayer extends Player {
    public AIPlayer(String name) {
        super(name);
    }

    public Card playCard1() {
        Card highestCard = getHand().stream()
                .max(Comparator.comparing(Card::getNumber))
                .orElseThrow(() -> new IllegalStateException("No cards left to play"));

        getHand().remove(highestCard);

        return highestCard;
    }
}
