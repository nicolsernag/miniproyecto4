package com.example.battleship;

import com.example.battleship.view.WelcomeStage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


/**
 *  * Main class to launch the Battleship JavaFX application.
 */
public class  Main extends Application {

    /**
     *  * Main entry point for the JavaFX application.
     *     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the JavaFX application by displaying the welcome stage.
     * @param primaryStage the primary stage for this application
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        WelcomeStage.getInstance();
    }
}
