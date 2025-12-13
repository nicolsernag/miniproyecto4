package com.example.battleship.view;

import com.example.battleship.controller.LoseController;
import com.example.battleship.controller.WinStageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A JavaFX Stage representing the Welcome screen of the Battleship game.
 * <p>
 * This class follows the Singleton design pattern to ensure that only one instance
 * of the WelcomeStage exists at any given time.
 */
public class WinStage extends Stage {
    private WinStageController controller;

    /**
     * Private constructor that initializes and displays the WinStage.
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


    /**
     * Static inner class responsible for holding the singleton instance of WinStage.
     */
    private static class Holder {
        private static WinStage INSTANCE = null;
    }

    /**
     * Returns the singleton instance of WinStage.
     * <p>
     * If the instance does not exist, it is created.
     *
     * @return the singleton instance of WinStage
     * @throws IOException if the WinStage cannot be created
     */
    public static WinStage getInstance() throws IOException {
        if (WinStage.Holder.INSTANCE == null) {
            WinStage.Holder.INSTANCE = new WinStage();
        }
        return WinStage.Holder.INSTANCE;
    }

    /**
     * Deletes the singleton instance of WinStage.
     * <p>
     * This method closes the stage and sets the instance to null.
     */
    public static void deleteInstance() {
        if (WinStage.Holder.INSTANCE != null) {
            WinStage.Holder.INSTANCE.close();
            WinStage.Holder.INSTANCE = null;
        }
    }

    /**
     * Returns the controller associated with this stage.
     *
     * @return a {@link WinStageController} instance
     */
    public WinStageController getController() {
        return controller;
    }
}


