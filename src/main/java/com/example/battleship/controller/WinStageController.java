package com.example.battleship.controller;

import com.example.battleship.view.SelectionStage;
import com.example.battleship.view.WelcomeStage;
import com.example.battleship.view.WinStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class WinStageController {
    @FXML
    private Button turnBack;

    @FXML
    void handleRestart(ActionEvent event) throws IOException {
        WelcomeStage.getInstance().getController();
        WinStage.deleteInstance();
    }
}
