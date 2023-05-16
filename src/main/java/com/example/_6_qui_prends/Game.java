package com.example._6_qui_prends;

import java.util.*;

public class Game {
    private List<Player> players;
    private List<Pile> piles;
    private Deck deck;

    public Game(List<Player> players) {
        if (players.size() < 2 || players.size() > 6) {
            throw new IllegalArgumentException("Number of players must be between 2 and 6");
        }
        this.players = players;
        this.piles = new ArrayList<>();
        this.deck = new Deck();
    }

    public void startGame() {
        // Shuffle the deck and deal 10 cards to each player
        deck.shuffle();
        for (Player player : players) {
            player.receiveCards(deck.drawCards(10));
        }

        // Start with four piles, each with one card from the deck
        for (int i = 0; i < 4; i++) {
            piles.add(new Pile(deck.drawCard()));
        }

        // Play rounds until no cards left
        while (!deck.isEmpty()) {
            playRound();
        }

        // Determine the winner
        Player winner = determineWinner();
        System.out.println("The winner is: " + winner.getName());
    }

    private void playRound() {
        // Each player plays a card
        List<Card> playedCards = new ArrayList<>();
        for (Player player : players) {
            Card playedCard;
            if (player instanceof AIPlayer) {
                playedCard = ((AIPlayer) player).playCard1();
            } else {
                System.out.println(player.getName() + ", your hand is: ");
                List<Card> hand = player.getHand();
                for (int i = 0; i < hand.size(); i++) {
                    System.out.println((i+1) + ". " + hand.get(i).toString());
                }
                System.out.println("Enter the index of the card you want to play:");
                int cardIndex = getConsoleInput() - 1;  // Adjust for 0-based index
                playedCard = player.playCard(cardIndex);
            }
            playedCards.add(playedCard);
        }

        // Sort the played cards
        Collections.sort(playedCards, Comparator.comparing(Card::getNumber));

        // Add each card to the appropriate pile
        for (Card card : playedCards) {
            addCardToPile(card);
        }
    }

    // Get console input - this is a simple implementation for demonstration
    private int getConsoleInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }


    private void addCardToPile(Card card) {
        // Find the pile that the card should be added to
        Pile pile = findPileForCard(card);

        // If the pile is full, the player takes the pile and the card starts a new pile
        if (pile.isFull()) {
            Player player = getPlayerWhoPlayedCard(card);
            player.addScore(pile.getBullheadCount());
            pile.clear();
        }

        // Add the card to the pile
        pile.addCard(card);
    }

    private Player getPlayerWhoPlayedCard(Card card) {
        for (Player player : players) {
            if (player.getHand().contains(card)) {
                return player;
            }
        }
        throw new IllegalArgumentException("No player found who played card " + card.getNumber());
    }

    private Pile findPileForCard(Card card) {
        // TODO: Implement according to the game rules
        return piles.get(0);  // Just as a placeholder
    }

    private Player determineWinner() {
        Player winner = players.get(0);
        for (Player player : players) {
            if (player.getScore() < winner.getScore()) {
                winner = player;
            }
        }
        return winner;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask for number of players
        System.out.println("Enter number of players (2 to 6):");
        int numPlayers = scanner.nextInt();

        // Validate input
        if (numPlayers < 2 || numPlayers > 6) {
            System.out.println("Invalid number of players. Please enter a number between 2 and 6.");
            return;
        }

        // Create players
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            System.out.println("Is player " + (i + 1) + " a human or AI? (Enter 'human' or 'AI')");
            String playerType = scanner.next();

            if (playerType.equalsIgnoreCase("human")) {
                System.out.println("Enter the name of human player " + (i + 1) + ":");
                String playerName = scanner.next();
                players.add(new Player(playerName));
            } else if (playerType.equalsIgnoreCase("AI")) {
                players.add(new AIPlayer("AI Player " + (i + 1)));
            } else {
                System.out.println("Invalid player type. Please enter 'human' or 'AI'.");
                return;
            }
        }

        // Create and start game
        Game game = new Game(players);
        game.startGame();
    }


}
