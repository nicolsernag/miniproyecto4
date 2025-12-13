package com.example.battleship.view;

import com.example.battleship.controller.SelectionController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A JavaFX Stage representing the Selection screen of the Battleship game.
 * <p>
 * This class follows the Singleton design pattern to ensure that only one instance
 * of the SelectionStage exists at any given time.
 */
public class SelectionStage extends Stage {
    private SelectionController controller;

    /**
     * Private constructor that initializes and displays the SelectionStage.
     * <p>
     * This method loads the FXML file, configures the scene,
     * sets window properties such as title and icon, and displays the stage.
     *
     * @throws IOException if the FXML file cannot be loaded
     */
    private SelectionStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/battleship/SelectionStage.fxml"));
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
     * Returns the controller associated with this stage.
     *
     * @return a {@link SelectionController} instance
     */
    public SelectionController getController(){
        return controller;
    }

    /**
     * Static inner class responsible for holding the singleton instance of SelectionStage.
     */
    private static class Holder {
        private static SelectionStage INSTANCE = null;
    }

    /**
     * Returns the singleton instance of SelectionStage.
     * <p>
     * If the instance does not exist, it is created.
     *
     * @return the singleton instance of SelectionStage
     * @throws IOException if the SelectionStage cannot be created
     */
    public static SelectionStage getInstance() throws IOException {
        if (Holder.INSTANCE == null) {
            Holder.INSTANCE = new SelectionStage();
        }
        return Holder.INSTANCE;
    }

    /**
     * Deletes the singleton instance of SelectionStage.
     * <p>
     * This method closes the stage and sets the instance to null,
     * allowing for a new instance to be created later if needed.
     */
    public static void deleteInstance() {
        if (Holder.INSTANCE != null) {
            Holder.INSTANCE.close();
            Holder.INSTANCE = null;
        }
    }
}
