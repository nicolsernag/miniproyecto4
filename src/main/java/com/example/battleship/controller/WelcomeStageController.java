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

    @FXML
    private void initialize() {
        File f = new File("mi_partida.sav");
        playButton1.setDisable(!f.exists());
    }

    @FXML
    private Button playButton1;

    // -----------------------
    //   CONTINUAR PARTIDA
    // -----------------------
    @FXML
    void handleClickContinue(ActionEvent event) {
        GameState loaded = SerializableFileHandler.loadGame("mi_partida.sav");
        if (loaded == null) {
            System.out.println("[WELCOME] No existe partida guardada.");
            return;
        }

        BoardPlayer player = new BoardPlayer();
        BoardPlayer enemy = new BoardPlayer();

        // Reconstruir barcos
        for (ShipData sd : loaded.getPlayerShips()) player.addShipFromData(sd);
        for (ShipData sd : loaded.getMachineShips()) enemy.addShipFromData(sd);

        try {
            GameStage gameStage = GameStage.getInstance();
            gameStage.getController().setLoadedState(loaded);
            gameStage.showLoaded(player, enemy);
            WelcomeStage.deleteInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

