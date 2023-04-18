package com.example._6_qui_prends;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Entrez le nombre de joueurs (2-10) : ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        if (numPlayers < 2 || numPlayers > 10) {
            System.out.println("Nombre de joueurs invalide. Le jeu doit avoir entre 2 et 10 joueurs.");
            return;
        }

        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Entrez le nom du joueur " + i + " : ");
            String playerName = scanner.nextLine();
            players.add(new Player(playerName));
        }
        players.add(new AIPlayer("IA 1"));
        players.add(new AIPlayer("IA 2"));

        Game game = new Game(players);
        game.initializeGame();

        // Play 10 rounds
        for (int i = 0; i < 10; i++) {
            System.out.println("Tour " + (i + 1) + " :");
            game.playRound(scanner);


            // Print rows
            for (int j = 0; j < game.getRows().size(); j++) {
                System.out.println("Rangée " + (j + 1) + " : " + game.getRows().get(j));
            }
        }

        System.out.println("Fin de la partie. Scores :");
        int minScore = Integer.MAX_VALUE;
        Player winner = null;

        for (Player player : players) {
            int score = player.getScore();
            System.out.println(player.getName() + " : " + score);

            if (score < minScore) {
                minScore = score;
                winner = player;
            }
        }

        System.out.println("Le gagnant est " + winner.getName() + " avec " + minScore + " points !");
    }
}
