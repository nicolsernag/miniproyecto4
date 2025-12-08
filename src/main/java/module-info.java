module com.example.battleship {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;



    opens com.example.battleship to javafx.fxml;
    opens com.example.battleship.controller to javafx.fxml;
    exports com.example.battleship;
}