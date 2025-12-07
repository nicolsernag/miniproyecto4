package com.example.battleship.controller;

import com.example.battleship.model.BoardPlayer;
import com.example.battleship.model.Cell;
import com.example.battleship.model.Ship;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.Random;

public class GameController {

    @FXML private GridPane playerGrid;
    @FXML
    private GridPane machineGrid;

    private BoardPlayer board;
    private BoardPlayer enemyBoard;

    private final int SIZE = 10;
    private final int cellSize = 40;
    private final Random random = new Random();

    public void initializeBoard(BoardPlayer board) {
        this.board = board;
        buildGrid();
        drawPlayerShips();
    }

    private void buildGrid() {

        playerGrid.getChildren().clear();

        int rows = 10;
        int cols = 10;
        double cellSize = 40;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {

                Pane cell = new Pane();
                cell.setPrefSize(cellSize, cellSize);

                cell.setStyle("""
                -fx-background-color: #1e3a8a;
                -fx-border-color: white;
                -fx-border-width: 1;
            """);

                cell.getProperties().put("row", r);
                cell.getProperties().put("col", c);

                playerGrid.add(cell, c, r);
            }
        }
    }


    private void drawPlayerShips() {

        for (Ship ship : board.getPlacedShips()) {

            // Obtiene la primera celda como ancla
            Cell start = ship.getOccupiedCells().get(0);

            int row = start.getRow();
            int col = start.getCol();

            // Redimensiona el barco según orientación
            ship.updateVisualSize(40);

            // Lo coloca en el GridPane
            GridPane.setRowIndex(ship, row);
            GridPane.setColumnIndex(ship, col);

            playerGrid.getChildren().add(ship);
        }
    }


}



