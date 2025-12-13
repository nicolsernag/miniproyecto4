package com.example.battleship.controller;

import com.example.battleship.model.BoardPlayer;
import com.example.battleship.model.GameState.FileManager;
import com.example.battleship.model.GameState.GameState;
import com.example.battleship.model.serializable.SerializableFileHandler;
import com.example.battleship.model.serializable.ShipData;
import com.example.battleship.view.GameStage;
import com.example.battleship.view.SelectionStage;
import com.example.battleship.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Controller class for the Welcome Stage of the Battleship game.
 * Manages user interactions on the welcome screen.
 */
public class WelcomeStageController {
    @FXML
    private Button playButton;

    private String playerNickname;

    /**
     * Handles the action when the JUGAR button is clicked.
     * Transitions to the Selection Stage and deletes the Welcome Stage instance.
     *
     * @param event The action event triggered by clicking the Play button.
     * @throws IOException If an I/O error occurs during stage transition.
     */
    @FXML
    void handlePlay(ActionEvent event) throws IOException {
        SelectionStage.getInstance().getController();
        WelcomeStage.deleteInstance();
    }
}

