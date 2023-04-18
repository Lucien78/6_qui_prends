package com.example._6_qui_prends;

import java.util.Comparator;
import java.util.List;

public class AIPlayer extends Player {

    public AIPlayer(String name) {
        super(name);
    }

    public Card selectCard(Game game) {
        // Pour cette implémentation simple, l'IA joue simplement la carte avec la valeur la plus faible.
        return getHand().stream().min(Comparator.comparing(Card::getValue)).orElse(null);
    }

    public int selectRowToTake(Game game) {
        // Pour cette implémentation simple, l'IA prend la rangée avec le moins de points.
        int minScore = Integer.MAX_VALUE;
        int rowIndexToTake = -1;

        for (int i = 0; i < game.getRows().size(); i++) {
            List<Card> row = game.getRows().get(i);
            int rowScore = row.stream().mapToInt(Card::getValue).sum();

            if (rowScore < minScore) {
                minScore = rowScore;
                rowIndexToTake = i;
            }
        }

        return rowIndexToTake;
    }
}

