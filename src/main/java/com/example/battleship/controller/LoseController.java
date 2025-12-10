package com.example.battleship.controller;

import com.example.battleship.view.WelcomeStage;
import com.example.battleship.view.WinStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class LoseController {
    @FXML
    private Button restartGame;

    @FXML
    void handleAgain(ActionEvent event) throws IOException {
        WelcomeStage.getInstance().getController();
        WinStage.deleteInstance();
}
}