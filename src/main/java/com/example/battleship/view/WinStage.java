package com.example.battleship.view;

import com.example.battleship.controller.LoseController;
import com.example.battleship.controller.WinStageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class WinStage extends Stage {
    private WinStageController controller;

    /**
     * Private constructor that initializes and displays the WelcomeStage.
     * <p>
     * This method loads the FXML file, configures the scene,
     * sets window properties such as title and icon, and displays the stage.
     *
     * @throws IOException if the FXML file cannot be loaded
     */
    private WinStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/battleship/WinStage.fxml"));
        Parent root= loader.load();
        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("Battleship");
        setResizable(false);
        getIcons().add(
                new Image(String.valueOf(getClass().getResource("/com/example/battleship/gran-ancla.png")))
        );
        show();
    }


    private static class Holder {
        private static WinStage INSTANCE = null;
    }

    public static WinStage getInstance() throws IOException {
        if (WinStage.Holder.INSTANCE == null) {
            WinStage.Holder.INSTANCE = new WinStage();
        }
        return WinStage.Holder.INSTANCE;
    }

    public static void deleteInstance() {
        if (WinStage.Holder.INSTANCE != null) {
            WinStage.Holder.INSTANCE.close();
            WinStage.Holder.INSTANCE = null;
        }
    }

    public WinStageController getController() {
        return controller;
    }
}


