package com.example.battleship.controller;

import com.example.battleship.model.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class SelectionController {

    @FXML private GridPane playerGrid;
    @FXML private Pane shipPanel;
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
        shipPlacer = new ShipPlacer(playerGrid, board, cellSize, this::onStartDrag);

        buildGrid();

        // 3) Crear la flota según tus cantidades
        createFleetInPanel();

        // 4) Preparar escucha de tecla R para rotar la pieza actual
        // El scene puede no estar aún disponible; esperamos a que se asocie
        playerGrid.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.addEventHandler(KeyEvent.KEY_PRESSED, this::onKeyPressed);
            }
        });

        // 5) Continuar (ejemplo) — se puede conectar a SceneManager
        continueButton.setOnAction(e -> {
            // comprobar que todos los barcos están colocados
            if (allShipsPlaced()) {
                System.out.println("Lista la flota. Continuar al juego.");
                // SceneManager.showGame();  // activar según tu aplicación
            } else {
                System.out.println("Aún faltan barcos por colocar.");
            }
        });
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

        // 1 carrier (4)
        Ship carrier = new Carrier(cellSize);
        created.add(carrier);

        // 2 submarinos (3)
        for (int i = 0; i < 2; i++) created.add(new Submarine(cellSize));

        // 3 destructores (2)
        for (int i = 0; i < 3; i++) created.add(new Destroyer(cellSize));

        // 4 fragatas (1)
        for (int i = 0; i < 4; i++) created.add(new Frigate(cellSize));

        // Colocar visualmente en el panel lateral, uno debajo del otro
        double y = 10;
        double gap = 10;
        for (Ship s : created) {
            s.setLayoutX(10);
            s.setLayoutY(y);
            // registrar tamaño "original" en propiedades si quieres
            s.getProperties().put("size", s instanceof Carrier ? 4 :
                    s instanceof Submarine ? 3 :
                            s instanceof Destroyer ? 2 : 1);

            // habilitar drag & drop
            shipPlacer.enableDrag(s);

            // añadir al panel lateral padre (no al GridPane todavía)
            shipPanel.getChildren().add(s);

            // avanzar la y para siguiente barco (se guarda el alto por cellSize*1.1)
            y += (cellSize * 1.4 + gap);
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
