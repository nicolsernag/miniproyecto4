package com.example.battleship.controller;

import com.example.battleship.view.LoseStage;
import com.example.battleship.view.WelcomeStage;
//import com.example.battleship.view.WinStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
//import javafx.scene.control.Button;

import java.io.IOException;

/**
 * Controller for the Lose screen in the Battleship game.
 * Handles user interactions on the Lose screen.
 */
public class LoseController {
   // @FXML
    //private Button restartGame;

    /**
     * Handles the action when the "VOLVER" button is clicked.
     * It navigates the user back to the Welcome screen and deletes the WinStage instance.
     *
     * @param event The action event triggered by clicking the button.
     * @throws IOException If an input or output exception occurs.
     */
    @FXML
    void handleAgain(ActionEvent event) throws IOException {
        WelcomeStage.getInstance().getController();
        LoseStage.deleteInstance();
}
}