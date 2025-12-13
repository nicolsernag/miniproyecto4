package com.example.battleship.controller;

import com.example.battleship.model.*;
import com.example.battleship.model.exceptions.ShipPlacementException;
import com.example.battleship.view.GameStage;
import com.example.battleship.view.SelectionStage;
//import com.example.battleship.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//import javafx.event.ActionEvent;


/**
 * Controller for the ship selection and placement stage.
 */
public class SelectionController {

    @FXML private GridPane playerGrid;
    @FXML private VBox shipPanel;
    @FXML private javafx.scene.control.Button continueButton;

    private BoardPlayer board;
    private ShipPlacer shipPlacer;
    private final double cellSize = 40;

    //reference to the ship that is currently being towed (to rotate with R)
    private Ship currentDraggingShip = null;

    /**
     * Initializes the selection controller, setting up the board, ship placer, and fleet.
     */
    @FXML
    public void initialize() {

        // initializes the logic board and ShipPlacer
        board = new BoardPlayer();


        buildGrid();

        shipPlacer = new ShipPlacer(playerGrid, board, cellSize, this::onStartDrag);
        // creates the fleet
        createFleetInPanel();


        continueButton.toFront();

        continueButton.setOnAction(e -> {
          if (allShipsPlaced()) {
              try {
                  GameStage.getInstance().show(board);
              } catch (ShipPlacementException ex) {
                  throw new RuntimeException(ex);
              } catch (IOException ex) {
                  throw new RuntimeException(ex);
              }
              SelectionStage.deleteInstance();
            }
        });

    }

    /**
     * Handles the game start action.
     * @param event The key event.
     */
   @FXML
    public void handleGame(ActionEvent event) throws IOException {
        GameStage.getInstance().getController();
        SelectionStage.deleteInstance();
    }

    /**
     * Creates the grids.
     */
    private void buildGrid() {

        playerGrid.getChildren().clear();

        int rows = 10;
        int cols = 10;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {

                Pane cell = new Pane();
                cell.setPrefSize(cellSize, cellSize);

                cell.setStyle("""
                -fx-background-color: #a5b7c6;
                -fx-border-color: white;
                -fx-border-width: 1;
            """);

                //saves coordinates
                cell.getProperties().put("row", r);
                cell.getProperties().put("col", c);

                // adds to GridPane
                playerGrid.add(cell, c, r);
            }
        }
    }

    /**
     * Creates the fleet of ships and adds them to the ship panel.
     */
    private void createFleetInPanel() {
        List<Ship> created = new ArrayList<>();

        // Creates the fleet
        created.add(new Carrier(cellSize));  // 1 carrier
        for (int i = 0; i < 2; i++) created.add(new Submarine(cellSize));  // 2 submarines
        for (int i = 0; i < 3; i++) created.add(new Destroyer(cellSize));  // 3 destroyers
        for (int i = 0; i < 4; i++) created.add(new Frigate(cellSize));    // 4 fragates

        // Cleans VBox
        shipPanel.getChildren().clear();


        // Add each ship to its HBox
        for (Ship s : created) {

            HBox container = new HBox();
            container.setSpacing(10);
            container.setPrefHeight(60);
            container.setMinHeight(60);
            container.setMaxHeight(60);
            container.setAlignment(Pos.CENTER_LEFT);

            s.setTranslateX(0);
            s.setTranslateY(0);

            // Rotation button
            javafx.scene.control.Button rotateBtn = new javafx.scene.control.Button("↻");
            rotateBtn.setStyle("-fx-font-size: 18; -fx-background-radius: 8; -fx-background-color:  #a5b7c6; -fx-text-fill:  #374957");

            rotateBtn.setOnAction(e -> s.toggleOrientation(cellSize));

            shipPlacer.enableDrag(s);

            container.getChildren().addAll(s, rotateBtn);

            shipPanel.getChildren().add(container);
        }
    }


    /**
     * Callback for when a ship starts being dragged.
     * @param ship The ship that is being dragged.
     */
    private void onStartDrag(Ship ship) {
        currentDraggingShip = ship;
    }


    /**
     * Checks if all ships have been placed on the board.
     * @return
     */
    private boolean allShipsPlaced() {
        try {
            java.lang.reflect.Field f = BoardPlayer.class.getDeclaredField("placedShips");
            f.setAccessible(true);
            java.util.Deque<?> placed = (java.util.Deque<?>) f.get(board);
            return placed.size() >= 10;
        } catch (Exception ex) {
            return false;
        }
    }

}
