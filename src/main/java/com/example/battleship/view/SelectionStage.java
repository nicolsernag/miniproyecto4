package com.example.battleship.view;

import com.example.battleship.controller.SelectionController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class SelectionStage extends Stage {
    private SelectionController controller;
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

    private static class Holder {
        private static SelectionStage INSTANCE = null;
    }

    public static SelectionStage getInstance() throws IOException {
        if (Holder.INSTANCE == null) {
            Holder.INSTANCE = new SelectionStage();
        }
        return Holder.INSTANCE;
    }

    public static void deleteInstance() {
        if (Holder.INSTANCE != null) {
            Holder.INSTANCE.close();
            Holder.INSTANCE = null;
        }
    }
}
