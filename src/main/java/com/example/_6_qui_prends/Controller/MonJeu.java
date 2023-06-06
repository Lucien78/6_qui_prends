package com.example._6_qui_prends.Controller;

import com.example._6_qui_prends.Player.AIPlayer;
import com.example._6_qui_prends.Card.Card;
import com.example._6_qui_prends.Card.Pile;
import com.example._6_qui_prends.Player.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MonJeu extends Application {

    private Game game;


    private Label currentPlayerLabel; // un label pour afficher le joueur courant
    private HBox pilesBox; // affichage des cartes
    private Button nextRoundButton;
    private HBox playerHandBox;
    private Button startButton;
    private Label gameStateLabel;
    @Override
    public void start(Stage primaryStage) {
        currentPlayerLabel = new Label();
        pilesBox = new HBox();
        playerHandBox = new HBox();
        gameStateLabel = new Label();

        startButton = new Button("Démarrer la partie");
        startButton.setOnAction(e -> {
                startGame();
        gameStateLabel.setText(game.getGameState());
                });

        nextRoundButton = new Button("Jouer le prochain tour");
        nextRoundButton.setOnAction(e -> playNextRound());

        VBox buttonsBox = new VBox();
        buttonsBox.getChildren().addAll(startButton, nextRoundButton);

        BorderPane root = new BorderPane();

        VBox topBox = new VBox();
        topBox.getChildren().addAll(gameStateLabel, pilesBox);
        root.setTop(topBox);

        root.setCenter(buttonsBox);
        root.setBottom(playerHandBox);

        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("Mon Jeu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void startGame() {
        TextInputDialog numberDialog = new TextInputDialog();
        numberDialog.setTitle("Nombre de joueurs");
        numberDialog.setContentText("Veuillez entrer le nombre de joueurs:");

        Optional<String> result = numberDialog.showAndWait();
        int numPlayers;
        if (result.isPresent()) {
            numPlayers = Integer.parseInt(result.get());
        } else {
            return;
        }

        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            Alert typeDialog = new Alert(Alert.AlertType.CONFIRMATION);
            typeDialog.setTitle("Type de joueur");
            typeDialog.setContentText("Le joueur " + (i + 1) + " est-il un humain ?");

            Optional<ButtonType> typeResult = typeDialog.showAndWait();
            if (typeResult.isPresent() && typeResult.get() == ButtonType.OK) {
                players.add(new Player("Joueur " + (i + 1)));
            } else {
                players.add(new AIPlayer("IA Player " + (i + 1)));
            }
        }

        game = new Game(players);
        game.initializeGame();
        gameStateLabel.setText(game.getGameState());
        nextRoundButton.setDisable(false);
        startButton.setVisible(false);
        playNextRound();
    }

    private void updateGameState() {
        gameStateLabel.setText(game.getGameState());
        currentPlayerLabel.setText("Joueur courant: " + game.getCurrentPlayer().getName());

        pilesBox.getChildren().clear();
        for (Pile pile : game.getPiles()) {
            Label pileLabel = new Label(pile.toString());
            pilesBox.getChildren().add(pileLabel);
        }

        playerHandBox.getChildren().clear();
        for (Card card : game.getCurrentPlayer().getHand()) {
            Label cardLabel = new Label(card.toString());
            playerHandBox.getChildren().add(cardLabel);
        }
    }


    private void playNextRound() {
        game.playNextRound();
        gameStateLabel.setText(game.getGameState());
    }




}
