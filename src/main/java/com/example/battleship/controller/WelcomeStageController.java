package com.example.battleship.controller;

import com.example.battleship.model.BoardPlayer;
import com.example.battleship.model.GameState.GameState;
import com.example.battleship.model.serializable.SerializableFileHandler;
import com.example.battleship.view.GameStage;
import com.example.battleship.view.SelectionStage;
import com.example.battleship.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.File;
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
    private void initialize() {
        // Habilitar o deshabilitar CONTINUAR según exista savegame.ser
        File f = new File("savegame.ser");
        playButton1.setDisable(!f.exists());
    }

    @FXML
    private Button playButton1;

    // -----------------------
    //   CONTINUAR PARTIDA
    // -----------------------
    @FXML
    void handleClickContinue(ActionEvent event) {

        GameState loaded = SerializableFileHandler.loadGameState();

        if (loaded == null) {
            System.out.println("[WELCOME] No existe partida guardada.");
            return;
        }

        System.out.println("[WELCOME] Partida encontrada. Restaurando tableros...");

        // Crear tableros desde cero
        BoardPlayer player = new BoardPlayer();
        BoardPlayer enemy = new BoardPlayer();

        // Restaurar matrices
        player.loadFromMatrix(loaded.getPlayerMatrix());
        enemy.loadFromMatrix(loaded.getEnemyMatrix());

        // Restaurar barcos en base a la matriz
        player.rebuildShipsFromBoard();
        enemy.rebuildShipsFromBoard();

        try {
            System.out.println("[WELCOME] Abriendo GameStage...");

            GameStage gameStage = GameStage.getInstance();

            // Pasar el estado cargado al controlador del GameStage
            gameStage.getController().setLoadedState(loaded);

            // Mostrar la partida cargada
            gameStage.showLoaded(player, enemy);

            // Cerrar welcome
            WelcomeStage.deleteInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

