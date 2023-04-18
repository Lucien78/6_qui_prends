module com.example._6_qui_prends {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example._6_qui_prends to javafx.fxml;
    exports com.example._6_qui_prends;
}