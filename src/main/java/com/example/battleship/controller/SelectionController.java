package com.example.battleship.controller;

import com.example.battleship.model.*;
import com.example.battleship.view.GameStage;
import com.example.battleship.view.SelectionStage;
import com.example.battleship.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;

public class SelectionController {

    @FXML private GridPane playerGrid;
    @FXML private VBox shipPanel;
    @FXML private javafx.scene.control.Button continueButton;

    private BoardPlayer board;
    private ShipPlacer shipPlacer;
    private final double cellSize = 40;

    // referencia al barco que actualmente se está arrastrando (para rotar con R)
    private Ship currentDraggingShip = null;

    @FXML
    public void initialize() {

        // 1) Inicializar tablero lógico y ShipPlacer
        board = new BoardPlayer();


        buildGrid();

        shipPlacer = new ShipPlacer(playerGrid, board, cellSize, this::onStartDrag);
        // 3) Crear la flota según tus cantidades
        createFleetInPanel();


        // 4) Preparar escucha de tecla R para rotar la pieza actual
        // El scene puede no estar aún disponible; esperamos a que se asocie
        playerGrid.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.addEventHandler(KeyEvent.KEY_PRESSED, this::onKeyPressed);
            }
        });

        continueButton.toFront();

        continueButton.setOnAction(e -> {
          if (allShipsPlaced()) {
               GameStage.getInstance().show(board);
              SelectionStage.deleteInstance();
            }
        });

    }

   @FXML
    public void handleGame(ActionEvent event) throws IOException {
        GameStage.getInstance().getController();
        SelectionStage.deleteInstance();
    }

    private void buildGrid() {

        playerGrid.getChildren().clear();

        int rows = 10;
        int cols = 10;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {

                Pane cell = new Pane();
                cell.setPrefSize(cellSize, cellSize);

                cell.setStyle("""
                -fx-background-color: #1e3a8a;
                -fx-border-color: white;
                -fx-border-width: 1;
            """);

                // Guardar coordenadas
                cell.getProperties().put("row", r);
                cell.getProperties().put("col", c);

                // Agregar al GridPane
                playerGrid.add(cell, c, r);
            }
        }
    }

    private void createFleetInPanel() {
        List<Ship> created = new ArrayList<>();

        // Crear la flota
        created.add(new Carrier(cellSize));  // 1 carrier
        for (int i = 0; i < 2; i++) created.add(new Submarine(cellSize));  // 2 submarinos
        for (int i = 0; i < 3; i++) created.add(new Destroyer(cellSize));  // 3 destructores
        for (int i = 0; i < 4; i++) created.add(new Frigate(cellSize));    // 4 fragatas

        // Limpiar el VBox
        shipPanel.getChildren().clear();


        // Añadir cada barco en su HBox
        for (Ship s : created) {

            HBox container = new HBox();
            container.setSpacing(10);
            container.setPrefHeight(60);          // altura fija (suficiente para cualquier barco)
            container.setMinHeight(60);
            container.setMaxHeight(60);
            container.setAlignment(Pos.CENTER_LEFT);  // asegura que el barco quede alineado y no flotando

            s.setTranslateX(0);
            s.setTranslateY(0);

            // Botón de rotación
            javafx.scene.control.Button rotateBtn = new javafx.scene.control.Button("↻");
            rotateBtn.setStyle("-fx-font-size: 18; -fx-background-radius: 8; -fx-background-color:  #2c2a4a; -fx-text-fill: #b3c5d7");

            rotateBtn.setOnAction(e -> s.toggleOrientation(cellSize));

            shipPlacer.enableDrag(s);

            container.getChildren().addAll(s, rotateBtn);

            shipPanel.getChildren().add(container);
        }
    }


    /**
     * Callback cuando se inicia el arrastre de un barco.
     * Usado para saber qué barco rotar cuando se presiona R.
     */
    private void onStartDrag(Ship ship) {
        currentDraggingShip = ship;
    }

    /**
     * Maneja la tecla R: si hay un barco siendo arrastrado, lo rota 90 grados.
     */
    private void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.R && currentDraggingShip != null) {
            // rotar 90 grados
            double newRot = (currentDraggingShip.getRotate() + 90) % 360;
            currentDraggingShip.setRotate(newRot);

            // (opcional) guardar orientación en properties
            boolean horizontal = Math.abs(newRot % 180) < 45;
            currentDraggingShip.getProperties().put("orientation", horizontal ? "H" : "V");
        }
    }

    /**
     * Comprueba si todos los barcos fueron colocados en el tablero.
     * Se basa en BoardPlayer. Ajusta según tu implementación.
     */
    private boolean allShipsPlaced() {
        // Por simplicidad: preguntamos si cantidad de barcos en board >= 1+2+3+4 = 10
        // Si tu BoardPlayer expone placedShips.size(), úsalo directamente
        try {
            java.lang.reflect.Field f = BoardPlayer.class.getDeclaredField("placedShips");
            f.setAccessible(true);
            java.util.Deque<?> placed = (java.util.Deque<?>) f.get(board);
            return placed.size() >= 10;
        } catch (Exception ex) {
            // fallback simple: true solo para permitir continuar en demos
            return false;
        }
    }

}
