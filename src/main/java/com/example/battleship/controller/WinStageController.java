package com.example.battleship.controller;

import com.example.battleship.view.SelectionStage;
import com.example.battleship.view.WelcomeStage;
import com.example.battleship.view.WinStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

/**
 * Controller class for the Win Stage.
 * Handles user interactions on the win screen.
 */
public class WinStageController {
    @FXML
    private Button turnBack;

    /**
     * Handles the action of returning to the welcome stage.
     * Closes the current win stage and opens the welcome stage.
     *
     * @param event The action event triggered by the user.
     * @throws IOException If an I/O error occurs while loading the selection stage.
     */
    @FXML
    void handleRestart(ActionEvent event) throws IOException {
        WelcomeStage.getInstance().getController();
        WinStage.deleteInstance();
    }
}
