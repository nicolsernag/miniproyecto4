package com.example.battleship.view;

import com.example.battleship.model.BoardPlayer;
import com.example.battleship.model.Cell;
import com.example.battleship.model.Ship;
import com.example.battleship.model.ShotResult;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardRenderer {
    private final double CELL_SIZE;

    public BoardRenderer(double cellSize) {
        this.CELL_SIZE = cellSize;
    }

    public void buildGrid(GridPane grid) {

        double cellSize = CELL_SIZE;
        int gridSize = 10;
        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();

        //Size for Columns
        ColumnConstraints cc = new ColumnConstraints();
        cc.setPrefWidth(cellSize);
        cc.setMinWidth(cellSize);
        cc.setMaxWidth(cellSize);

        for (int i = 0; i < gridSize; i++) {
            grid.getColumnConstraints().add(cc);
        }

        // Size for rows
        RowConstraints rc = new RowConstraints();
        rc.setPrefHeight(cellSize);
        rc.setMinHeight(cellSize);
        rc.setMaxHeight(cellSize);

        for (int i = 0; i < gridSize; i++) {
            grid.getRowConstraints().add(rc);
        }

        grid.getChildren().clear();

        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {

                Pane cell = new Pane();
                cell.setPrefSize(CELL_SIZE, CELL_SIZE);

                cell.setStyle(" -fx-background-color: #a5b7c6; -fx-border-color: white; -fx-border-width: 1; ");

                cell.getProperties().put("row", r);
                cell.getProperties().put("col", c);

                grid.add(cell, c, r);
            }
        }
    }

    public void drawPlayerShips(GridPane playerGrid, BoardPlayer playerBoard) {

        for (Ship ship : playerBoard.getPlacedShips()) {

            // Obtiene la primera celda como ancla
            Cell start = ship.getOccupiedCells().get(0);

            int row = start.getRow();
            int col = start.getCol();

            // Redimensiona el barco según orientación
            ship.updateVisualSize(CELL_SIZE);

            // Lo coloca en el GridPane
            GridPane.setRowIndex(ship, row);
            GridPane.setColumnIndex(ship, col);

            playerGrid.getChildren().add(ship);
        }
    }

    public void paintShot(GridPane grid, int row, int col, ShotResult result) {
        ImageView mark;
        String imagePath;

        switch (result) {
            case WATER:
                imagePath = "/com/example/battleship/ola.png";
                break;
            case HIT:
                imagePath = "/com/example/battleship/bomba.png";
                break;
            case SUNK:
                imagePath = "/com/example/battleship/fuego.png";
                break;
            default:
                return;
        }

        // 2. Crear el ImageView a partir de la ruta de la imagen
        try {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            mark = new ImageView(image);

            // 3. Configurar el tamaño del ImageView al tamaño de la celda
            mark.setFitWidth(CELL_SIZE);
            mark.setFitHeight(CELL_SIZE);
            mark.setPreserveRatio(true);

        } catch (Exception e) {
            System.err.println("Error al cargar la imagen: " + imagePath);
            // Si la imagen falla, usamos el rectángulo rojo de respaldo para impacto
            Rectangle errorMark = new Rectangle(CELL_SIZE, CELL_SIZE, Color.RED);
            grid.add(errorMark, col, row); // Usa grid.add(mark, col, row) de la línea 39
            return;
        }
        grid.add(mark, col,
                row);
    }


}
