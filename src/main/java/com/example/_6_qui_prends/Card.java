package com.example._6_qui_prends;

public class Card {
        private int value;
        private int bulls;
        private Player player;

        public Card(int value) {
            this.value = value;
            this.bulls = calculateBulls(value);
        }
        public Player getPlayer() {
        return player;
         }
         public void setPlayer(Player player) {
        this.player = player;
        }

        private int calculateBulls(int value) {
            if (value % 55 == 0) {
                return 7;
            } else if (value % 11 == 0) {
                return 5;
            } else if (value % 5 == 0) {
                return 2;
            } else if (value % 10 == 0) {
                return 3;
            } else {
                return 1;
            }
        }

        public int getValue() {
            return value;
        }

        public int getBulls() {
            return bulls;
        }

        @Override
        public String toString() {
            return "Card{" +
                    "value=" + value +
                    ", bulls=" + bulls +
                    '}';
        }

}

