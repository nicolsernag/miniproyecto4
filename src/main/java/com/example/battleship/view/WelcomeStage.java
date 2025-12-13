package com.example.battleship.view;

import com.example.battleship.controller.WelcomeStageController;
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
public class WelcomeStage extends Stage {
    private WelcomeStageController controller;

    /**
     * Private constructor that initializes and displays the WelcomeStage.
     * <p>
     * This method loads the FXML file, configures the scene,
     * sets window properties such as title and icon, and displays the stage.
     *
     * @throws IOException if the FXML file cannot be loaded
     */
    private WelcomeStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/battleship/WelcomeView.fxml"));
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
     * Static inner class responsible for holding the singleton instance of WelcomeStage.
     */
    private static class Holder {
        private static WelcomeStage INSTANCE = null;
    }

    /**
     * Returns the singleton instance of WelcomeStage.
     * <p>
     * If the instance does not exist, it is created.
     *
     * @return the singleton instance of WelcomeStage
     * @throws IOException if the WelcomeStage cannot be created
     */
    public static WelcomeStage getInstance() throws IOException {
        if (Holder.INSTANCE == null) {
            Holder.INSTANCE = new WelcomeStage();
        }
        return Holder.INSTANCE;
    }

    /**
     * Deletes the singleton instance of WelcomeStage.
     * <p>
     * This method closes the stage and sets the instance to null,
     * allowing for a new instance to be created in the future.
     */
    public static void deleteInstance() {
        if (Holder.INSTANCE != null) {
            Holder.INSTANCE.close();
            Holder.INSTANCE = null;
        }
    }

    /**
     * Returns the controller associated with this stage.
     *
     * @return a {@link WelcomeStageController} instance
     */
    public WelcomeStageController getController() {
        return controller;
    }
}
