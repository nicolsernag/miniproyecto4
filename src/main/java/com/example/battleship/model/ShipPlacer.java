package com.example.battleship.model;

import javafx.scene.layout.GridPane;
import java.util.function.Consumer;

public class ShipPlacer {

    private final GridPane grid;
    private final BoardPlayer board;
    private final double cellSize;
    private final Consumer<Ship> onStartDrag;

    public ShipPlacer(GridPane grid, BoardPlayer board, double cellSize, Consumer<Ship> onStartDrag) {
        this.grid = grid;
        this.board = board;
        this.cellSize = cellSize;
        this.onStartDrag = onStartDrag;
    }

    // ----------------------------------------------------------
    // ROTACIÓN POR DOBLE CLIC
    // ----------------------------------------------------------
    private void attemptRotateShip(Ship ship) {
        Integer row = GridPane.getRowIndex(ship);
        Integer col = GridPane.getColumnIndex(ship);
        boolean isOnGrid = (row != null && col != null);

        boolean newHorizontal = !ship.isHorizontal();
        int size = ship.getSize();

        // Caso 1: NO está en el tablero → rotación libre
        if (!isOnGrid) {
            ship.toggleOrientation();
            return;
        }

        // Caso 2: Está en el tablero → validar bordes
        if (newHorizontal && col + size > 10) return;
        if (!newHorizontal && row + size > 10) return;

        // Validar colisiones con otros barcos
        if (!board.canPlace(ship, row, col, newHorizontal)) {
            System.out.println("Rotación bloqueada por colisión.");
            return;
        }

        // Aplicar rotación
        ship.toggleOrientation();
        board.relocateShipAfterRotation(ship, row, col, newHorizontal);
    }

    // ----------------------------------------------------------
    // DRAG & DROP
    // ----------------------------------------------------------
    public void enableDrag(Ship ship) {

        final double[] original = new double[] { ship.getLayoutX(), ship.getLayoutY() };
        final double[] dragOffset = new double[2];

        // ---------------------------------
        // DOBLE CLIC → ROTAR BARCO
        // ---------------------------------
        ship.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                attemptRotateShip(ship);
                e.consume();
            }
        });

        // ---------------------------------
        // INICIO DEL ARRASTRE
        // ---------------------------------
        ship.setOnMousePressed(e -> {
            onStartDrag.accept(ship);

            dragOffset[0] = e.getX();
            dragOffset[1] = e.getY();

            e.consume();
        });

        // ---------------------------------
        // ARRASTRAR LIBRE
        // ---------------------------------
        ship.setOnMouseDragged(e -> {

            ship.setLayoutX(e.getSceneX() - dragOffset[0]);
            ship.setLayoutY(e.getSceneY() - dragOffset[1]);

            e.consume();
        });

        // ---------------------------------
        // SOLTAR → intentar colocar
        // ---------------------------------
        ship.setOnMouseReleased(e -> {

            double x = ship.getLayoutX();
            double y = ship.getLayoutY();

            int col = (int) Math.floor(x / cellSize);
            int row = (int) Math.floor(y / cellSize);
            boolean horizontal = ship.isHorizontal();

            if (board.canPlace(ship, row, col, horizontal)) {

                board.placeShip(ship, row, col, horizontal);

                // Añadir al grid si aún no está
                if (!grid.getChildren().contains(ship)) {
                    grid.getChildren().add(ship);
                }

                GridPane.setColumnIndex(ship, col);
                GridPane.setRowIndex(ship, row);

                // Centrar dentro de la celda
                ship.setLayoutX(0);
                ship.setLayoutY(0);

                // Desactivar eventos (ya no se puede mover más)
                ship.setOnMouseDragged(null);
                ship.setOnMousePressed(null);
                ship.setOnMouseClicked(null);
                ship.setOnMouseReleased(null);

            } else {
                // Rebotar al panel lateral
                ship.setLayoutX(original[0]);
                ship.setLayoutY(original[1]);

                GridPane.setColumnIndex(ship, null);
                GridPane.setRowIndex(ship, null);
            }

            onStartDrag.accept(null);
            e.consume();
        });
    }
}


