package com.example.battleship.view;

import com.example.battleship.controller.LoseController;
import com.example.battleship.controller.WelcomeStageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class LoseStage extends Stage {
    private LoseController controller;

    /**
     * Private constructor that initializes and displays the WelcomeStage.
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


    private static class Holder {
        private static LoseStage INSTANCE = null;
    }

    public static LoseStage getInstance() throws IOException {
        if (LoseStage.Holder.INSTANCE == null) {
            LoseStage.Holder.INSTANCE = new LoseStage();
        }
        return LoseStage.Holder.INSTANCE;
    }

    public static void deleteInstance() {
        if (LoseStage.Holder.INSTANCE != null) {
            LoseStage.Holder.INSTANCE.close();
            LoseStage.Holder.INSTANCE = null;
        }
    }

    public LoseController getController() {
        return controller;
    }
}


