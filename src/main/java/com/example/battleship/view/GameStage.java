package com.example.battleship.view;

import com.example.battleship.controller.GameController;
import com.example.battleship.model.BoardPlayer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameStage {

    private static GameStage instance;
    private Stage stage;
    private GameController controller;

    private GameStage() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/battleship/GameViewBattleship.fxml")
            );

            Parent root = loader.load();
            controller = loader.getController();

            stage = new Stage();
            stage.setTitle("Battleship - Juego");
            stage.setScene(new Scene(root));
            stage.setResizable(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static GameStage getInstance() {
        if (instance == null) instance = new GameStage();
        return instance;
    }

    public void show(BoardPlayer board) {
        controller.initializeBoard(board);
        stage.show();
    }
}



