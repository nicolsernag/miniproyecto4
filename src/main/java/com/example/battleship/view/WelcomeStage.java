package com.example.battleship.view;

import com.example.battleship.controller.WelcomeStageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

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


    private static class Holder {
        private static WelcomeStage INSTANCE = null;
    }

    public static WelcomeStage getInstance() throws IOException {
        if (Holder.INSTANCE == null) {
            Holder.INSTANCE = new WelcomeStage();
        }
        return Holder.INSTANCE;
    }

    public static void deleteInstance() {
        if (Holder.INSTANCE != null) {
            Holder.INSTANCE.close();
            Holder.INSTANCE = null;
        }
    }

    public WelcomeStageController getController() {
        return controller;
    }
}
