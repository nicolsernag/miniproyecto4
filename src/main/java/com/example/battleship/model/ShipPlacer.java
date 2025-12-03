package com.example.battleship.model;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.function.Consumer;

public class ShipPlacer {

    private final GridPane grid;
    private final BoardPlayer board;
    private final double cellSize;
    private final Consumer<Ship> onStartDrag; // callback para informar qué barco está siendo arrastrado

    public ShipPlacer(GridPane grid, BoardPlayer board, double cellSize, Consumer<Ship> onStartDrag) {
        this.grid = grid;
        this.board = board;
        this.cellSize = cellSize;
        this.onStartDrag = onStartDrag;
    }

    /**
     * Habilita drag & drop para el barco.
     * - El barco se arrastra libremente.
     * - Al soltar, calculamos la celda objetivo (col,row).
     * - Si cabe, lo registra en BoardPlayer y lo ancla en el GridPane (GridPane.add).
     * - Si no cabe, regresa a su posición inicial.
     */
    public void enableDrag(Ship ship) {

        // Guardar la posición "panel" original para revertir si no cabe
        final double[] original = new double[] { ship.getLayoutX(), ship.getLayoutY() };

        final double[] dragOffset = new double[2];

        ship.setOnMousePressed(e -> {
            // Indicar que arrancamos a arrastrar este barco
            onStartDrag.accept(ship);

            // offset relativo entre el mouse y la esquina del barco
            dragOffset[0] = e.getX();
            dragOffset[1] = e.getY();

            e.consume();
        });

        ship.setOnMouseDragged(e -> {
            // Mover (en coordenadas del padre)
            ship.setLayoutX(e.getSceneX() - dragOffset[0]);
            ship.setLayoutY(e.getSceneY() - dragOffset[1]);
            e.consume();
        });

        ship.setOnMouseReleased((MouseEvent e) -> {
            // Calculamos la celda destino en base a la posicion del barco en el panel
            // NOTA: usamos layoutX/layoutY relativos al contenedor padre que contiene grid y shipPanel.
            double x = ship.getLayoutX();
            double y = ship.getLayoutY();

            int col = (int) Math.floor(x / cellSize);
            int row = (int) Math.floor(y / cellSize);

            // Determinar orientación por rotación del pane (si está rotado ~90 => vertical)
            boolean horizontal = Math.abs((ship.getRotate() % 180)) < 45;

            // Validar límites y colisiones con BoardPlayer
            if (board.canPlace(ship, row, col, horizontal)) {

                // Registrar en el modelo
                board.placeShip(ship, row, col, horizontal);

                // Anclar visualmente en grid: agregar si no estaba
                if (!grid.getChildren().contains(ship)) {
                    grid.getChildren().add(ship);
                }

                // Establecer columnas/filas en GridPane
                GridPane.setColumnIndex(ship, col);
                GridPane.setRowIndex(ship, row);

                // Resetear offsets dentro de la celda
                ship.setLayoutX(0);
                ship.setLayoutY(0);

                // bloquear movimientos posteriores (colocado)
                ship.setOnMouseDragged(null);
                ship.setOnMousePressed(null);
                ship.setOnMouseReleased(null);

            } else {
                // Revertir a la posición original en el panel lateral
                ship.setLayoutX(original[0]);
                ship.setLayoutY(original[1]);

                // Si se estaba mostrando en el grid intentamos removerlo (parche)
                GridPane.setColumnIndex(ship, null);
                GridPane.setRowIndex(ship, null);
            }

            // Limpiar callback de arranque (no estrictamente necesario)
            onStartDrag.accept(null);
            e.consume();
        });
    }
}


