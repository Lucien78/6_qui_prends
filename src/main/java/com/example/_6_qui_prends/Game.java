package com.example._6_qui_prends;

import java.util.*;

public class Game {
    private List<Player> players;
    private List<Pile> piles;
    private Deck deck;
    private List<Pile> table;

    public Game(List<Player> players) {
        if (players.size() < 2 || players.size() > 6) {
            throw new IllegalArgumentException("Number of players must be between 2 and 6");
        }
        this.players = players;
        this.piles = new ArrayList<>();
        this.deck = new Deck();
        this.table = new ArrayList<>();
    }

    public void startGame() {
        // Shuffle the deck and deal 10 cards to each player
        deck.shuffle();
        for (Player player : players) {
            player.receiveCards(deck.drawCards(10));
        }

        // Start with four piles, each with one card from the deck
        for (int i = 0; i < 4; i++) {
            table.add(new Pile(deck.drawCard())); // Utilisez "table" au lieu de "piles"
        }

        // Play rounds until no cards left
        boolean gameIsOver = false;
        while (!gameIsOver) {
            playRound();

            // Check if all players have played all their cards
            gameIsOver = true;
            for (Player player : players) {
                if (!player.getHand().isEmpty()) {
                    gameIsOver = false;
                    break;
                }
            }
        }
        // Determine the winner
        Player winner = determineWinner();
        System.out.println("The winner is: " + winner.getName());

        // Retirez ce bloc de code car il n'est pas nécessaire
    }

    private void displayGameState() {
        System.out.println("\nCurrent game state:");
        for (int i = 0; i < table.size(); i++) {
            System.out.println("Pile " + (i+1) + ": " + table.get(i).toString());
        }
    }


    private void playRound() {
        // Each player plays a card
        List<PlayedCard> playedCards = new ArrayList<>();
        displayGameState();
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
            PlayedCard playerAndCard = new PlayedCard(playedCard, player);
            playedCards.add(playerAndCard);
        }

        // Sort the played cards
        Collections.sort(playedCards, Comparator.comparing(c -> c.getCard().getNumber()));

        // Add each card to the appropriate pile
        for (PlayedCard playedCard : playedCards) {
            addCardToPile(playedCard);
        }
    }


    // Get console input - this is a simple implementation for demonstration
    private int getConsoleInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }


    private void addCardToPile(PlayedCard playedCard) {
        Pile pile = findPileForCard(playedCard);
        if (pile.isFull()) {
            Player player = playedCard.getPlayer();
            player.addScore(pile.getBullheadCount());
            pile.clear();
        }
        pile.addCard(playedCard.getCard());
    }



    private Player getPlayerWhoPlayedCard(Card card) {
        for (Player player : players) {
            if (player.getHand().contains(card)) {
                return player;
            }
        }
        throw new IllegalArgumentException("No player found who played card " + card.getNumber());
    }

    private Pile findPileForCard(PlayedCard playedCard) {
        Card card = playedCard.getCard();
        Player player = playedCard.getPlayer();
        Pile chosenPile = null;

        // If card can be legally placed on a pile, place it on the pile with the highest top card
        for (Pile pile : table) {
            if (pile.getTopCard().getNumber() < card.getNumber()) {
                if (chosenPile == null || pile.getTopCard().getNumber() > chosenPile.getTopCard().getNumber()) {
                    chosenPile = pile;
                }
            }
        }

        // If card cannot be legally placed, player must take a pile
        if (chosenPile == null) {
            chosenPile = choosePile(player);
        }

        return chosenPile;
    }
    private Pile choosePile(Player player) {
        // If player is AI, simply choose the pile with the least bullheads
        if (player instanceof AIPlayer) {
            Pile minPile = table.get(0);
            for (Pile pile : table) {
                if (pile.getBullheadCount() < minPile.getBullheadCount()) {
                    minPile = pile;
                }
            }
            return minPile;
        }
        // If player is human, prompt them to choose a pile
        else {
            while (true) {
                System.out.println("You cannot place your card. Choose a pile to take (1-4):");
                int pileIndex = getConsoleInput() - 1;  // Adjust for 0-based index
                if (pileIndex >= 0 && pileIndex < table.size()) {
                    return table.get(pileIndex);
                } else {
                    System.out.println("Invalid pile number. Please enter a number between 1 and 4.");
                }
            }
        }
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
