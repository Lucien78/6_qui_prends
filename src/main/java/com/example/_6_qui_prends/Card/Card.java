package com.example._6_qui_prends.Card;

import com.example._6_qui_prends.Player.Player;

public class Card {
    private final int number;
    private final int bullheadCount;
    private Player player;

    public Card(int number) {
        this.number = number;
        this.bullheadCount = calculateBullheadCount(number);
    }

    public int getNumber() {
        return this.number;
    }

    public int getBullheadCount() {
        return this.bullheadCount;
    }

    private int calculateBullheadCount(int number) {
        if (number % 10 == 0) return 3;
        else if (number % 5 == 0) return 2;
        else if (number % 10 == 0) return 5;
        else return 1;
    }
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    @Override
    public String toString() {
        return "Card: " + number + ", Bullheads: " + bullheadCount;
    }
}
