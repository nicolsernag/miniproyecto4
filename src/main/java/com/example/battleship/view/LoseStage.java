package com.example.battleship.view;

import com.example.battleship.controller.LoseController;
import com.example.battleship.controller.WelcomeStageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A JavaFX Stage representing the Lose screen of the Battleship game.
 * <p>
 * This class follows the Singleton design pattern to ensure that only one instance
 * of the LoseStage exists at any given time.
 */
public class LoseStage extends Stage {
    private LoseController controller;

    /**
     * Private constructor that initializes and displays the LoseStage.
     * <p>
     * This method loads the FXML file, configures the scene,
     * sets window properties such as title and icon, and displays the stage.
     *
     * @throws IOException if the FXML file cannot be loaded
     */
    private LoseStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/battleship/LoseStage.fxml"));
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
     * Static inner class responsible for holding the singleton instance of LoseStage.
     */
    private static class Holder {
        private static LoseStage INSTANCE = null;
    }

    /**
     * Returns the singleton instance of LoseStage.
     * <p>
     * If the instance does not exist, it is created.
     *
     * @return the singleton instance of LoseStage
     * @throws IOException if the LoseStage cannot be created
     */
    public static LoseStage getInstance() throws IOException {
        if (LoseStage.Holder.INSTANCE == null) {
            LoseStage.Holder.INSTANCE = new LoseStage();
        }
        return LoseStage.Holder.INSTANCE;
    }

    /**
     * Deletes the singleton instance of LoseStage.
     * <p>
     * This method closes the stage and sets the instance to null,
     * allowing for a new instance to be created later if needed.
     */
    public static void deleteInstance() {
        if (LoseStage.Holder.INSTANCE != null) {
            LoseStage.Holder.INSTANCE.close();
            LoseStage.Holder.INSTANCE = null;
        }
    }

    /**
     * Returns the LoseController associated with this LoseStage.
     * @return
     */
    public LoseController getController() {
        return controller;
    }
}


