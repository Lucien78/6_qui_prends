package com.example._6_qui_prends;

public class PlayedCard {
    private final Card card;
    private final Player player;

    public PlayedCard(Card card, Player player) {
        this.card = card;
        this.player = player;
    }

    public Card getCard() {
        return card;
    }

    public Player getPlayer() {
        return player;
    }
}
