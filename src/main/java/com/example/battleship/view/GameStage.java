package com.example.battleship.view;

import com.example.battleship.controller.GameController;
import com.example.battleship.controller.WelcomeStageController;
import com.example.battleship.model.BoardPlayer;
import com.example.battleship.model.exceptions.ShipPlacementException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
            stage.getIcons().add(
                    new Image(String.valueOf(getClass().getResource("/com/example/battleship/gran-ancla.png")))
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static GameStage getInstance() {
        if (instance == null) instance = new GameStage();
        return instance;
    }

    public void show(BoardPlayer board) throws ShipPlacementException {
        double cellSize = controller.getCELL_SIZE();
        BoardPlayer enemyBoard = new BoardPlayer();
        enemyBoard.placeShipsAutomatically(cellSize);
        controller.initializeBoards(board, enemyBoard);
       stage.show();
    }

    public GameController getController() {
        return controller;
    }
}



