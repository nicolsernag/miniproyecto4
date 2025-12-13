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

import java.io.IOException;

public class GameStage extends Stage{

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

    private static class Holder {
        private static GameStage INSTANCE = null;
    }

    public static GameStage getInstance() throws IOException {
        if (GameStage.Holder.INSTANCE == null) {GameStage.Holder.INSTANCE = new GameStage();}
        return GameStage.Holder.INSTANCE;
    }

    public void show(BoardPlayer board) throws ShipPlacementException {
        double cellSize = controller.getCELL_SIZE();
        BoardPlayer enemyBoard = new BoardPlayer();
        try {
            enemyBoard.placeShipsAutomatically(cellSize);
        } catch (ShipPlacementException e) {
            throw new RuntimeException(e);
        }
        controller.initializeBoards(board, enemyBoard);
       stage.show();
    }

    public GameController getController() {
        return controller;
    }

    public static void deleteInstance() {
        if (GameStage.Holder.INSTANCE != null) {
            GameStage.Holder.INSTANCE.close();
            GameStage.Holder.INSTANCE = null;
        }
    }
}



