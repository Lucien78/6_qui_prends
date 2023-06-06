module com.example._6_qui_prends {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;


    opens com.example._6_qui_prends to javafx.fxml;
    exports com.example._6_qui_prends.Controller;
    opens com.example._6_qui_prends.Controller to javafx.fxml;
    exports com.example._6_qui_prends.Card;
    opens com.example._6_qui_prends.Card to javafx.fxml;
    exports com.example._6_qui_prends.Player;
    opens com.example._6_qui_prends.Player to javafx.fxml;

    opens com.example._6_qui_prends.AIPlayer to javafx.fxml;
}