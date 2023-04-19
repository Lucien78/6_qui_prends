package com.example._6_qui_prends;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {
    private List<Player> players;
    private List<Card> deck;
    private List<List<Card>> rows;

    public Game(List<Player> players) {
        this.players = players;
        this.deck = createDeck();
        this.rows = new ArrayList<>();
    }

    public void initializeGame() {
        Collections.shuffle(deck);
        dealCards();
        setupRows();
    }
    public void printRows() {
        for (int i = 0; i < rows.size(); i++) {
            System.out.println("Colonne " + (i + 1) + ": " + rows.get(i));
        }
    }


    private List<Card> createDeck() {
        return IntStream.rangeClosed(1, 104)
                .mapToObj(Card::new)
                .collect(Collectors.toList());
    }

    private void dealCards() {
        List<Card> deck = createDeck();
        Collections.shuffle(deck);

        for (Player player : players) {
            List<Card> hand = deck.subList(0, 10);
            deck = deck.subList(10, deck.size());
            player.setHand(new ArrayList<>(hand));

            // Triez la main du joueur par ordre croissant
            player.getHand().sort(Comparator.comparingInt(Card::getValue));
        }
    }

    private void setupRows() {
        for (int i = 0; i < 4; i++) {
            List<Card> row = new ArrayList<>();
            row.add(deck.remove(0));
            rows.add(row);
        }
    }

    public void playRound(Scanner scanner) {
        System.out.println("\nColonnes avant le tour :");
        printRows();

        List<Card> selectedCards = new ArrayList<>();

        for (Player player : players) {
            Card selectedCard;

            if (player instanceof AIPlayer) {
                selectedCard = ((AIPlayer) player).selectCard(this);
                System.out.println(player.getName() + " (IA) joue la carte : " + selectedCard);
            } else {
                System.out.println(player.getName() + ", voici votre main : " + player.getHand());
                System.out.print("Entrez l'index de la carte à jouer (0 - " + (player.getHand().size() - 1) + ") : ");
                int selectedIndex = scanner.nextInt();
                selectedCard = player.getHand().get(selectedIndex);
            }

            selectedCard.setPlayer(player); // Ajoutez cette ligne
            selectedCards.add(selectedCard);
        }

        for (Card card : selectedCards) {
            addCardToRow(card, scanner);
            Player player = card.getPlayer(); // Remplacez findPlayerByCard(card) par card.getPlayer()
            player.getHand().remove(card);
        }
    }

    private void addCardToRow(Card card, Scanner scanner) {
        int bestRowIndex = -1;
        int bestDifference = Integer.MAX_VALUE;

        for (int i = 0; i < rows.size(); i++) {
            List<Card> row = rows.get(i);
            if (row.get(row.size() - 1).getValue() < card.getValue()) {
                int difference = card.getValue() - row.get(row.size() - 1).getValue();
                if (difference < bestDifference) {
                    bestDifference = difference;
                    bestRowIndex = i;
                }
            }
        }

        if (bestRowIndex == -1) {
            Player player = card.getPlayer();;

            if (player instanceof AIPlayer) {
                bestRowIndex = ((AIPlayer) player).selectRowToTake(this);
                System.out.println(player.getName() + " (IA) prend la rangée " + (bestRowIndex + 1));
            } else {
                bestRowIndex = selectRowToTake(card, scanner);
            }

            List<Card> rowToTake = rows.get(bestRowIndex);
            player.takeCards(rowToTake);
            rowToTake.clear();
        }

        rows.get(bestRowIndex).add(card);
    }

        private int selectRowToTake(Card card, Scanner scanner) {
            System.out.println("Aucune rangée valide pour la carte " + card + ".");
            System.out.println("Rangées actuelles : ");
            for (int i = 0; i < rows.size(); i++) {
                System.out.println((i + 1) + ": " + rows.get(i));
            }

            Player player = findPlayerByCard(card);
            System.out.println(player.getName() + ", veuillez choisir une rangée à prendre (1-" + rows.size() + ") : ");
            int selectedRowIndex = scanner.nextInt() - 1;

            // Vérifiez si l'index sélectionné est valide.
            while (selectedRowIndex < 0 || selectedRowIndex >= rows.size()) {
                System.out.println("Index invalide. Veuillez entrer un nombre entre 1 et " + rows.size() + " : ");
                selectedRowIndex = scanner.nextInt() - 1;
            }

            return selectedRowIndex;
        }

    private Player findPlayerByCard(Card card) {
        for (Player player : players) {
            if (player.getHand().contains(card)) {
                return player;
            }
        }
        throw new IllegalStateException("Joueur non trouvé pour la carte : " + card);

    }


    public List<Player> getPlayers() {
        return players;
    }

    public List<List<Card>> getRows() {
        return rows;
    }
}
