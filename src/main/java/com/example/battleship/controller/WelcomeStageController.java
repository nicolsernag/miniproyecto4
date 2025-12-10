package com.example.battleship.controller;

import com.example.battleship.view.SelectionStage;
import com.example.battleship.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class WelcomeStageController {
    @FXML
    private Button playButton;

    @FXML
    void handlePlay(ActionEvent event) throws IOException {
        SelectionStage.getInstance().getController();
        WelcomeStage.deleteInstance();
    }

    @FXML
    private Button playButton1;

    @FXML
    void handleClickContinue(ActionEvent event) {

    }
}
