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

public class WelcomeStageController {
    @FXML
    private Button playButton;

    private String playerNickname;

    @FXML
    void handlePlay(ActionEvent event) throws IOException {
        SelectionStage.getInstance().getController();
        WelcomeStage.deleteInstance();
    }
}

