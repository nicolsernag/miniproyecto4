package com.example.battleship.controller;

import com.example.battleship.model.*;
import com.example.battleship.model.threads.MachineThread;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameController {

    @FXML private Button showEnemyBtn;


    @FXML private GridPane playerGrid; //tablero del jugador(visual)
    @FXML private GridPane enemyGrid;  //tablero del enemigo(interactivo)

    private BoardPlayer playerBoard;
    private BoardPlayer enemyBoard;

    private MachineThread machineThread;

    private boolean playerTurn = true;
    private final double CELL_SIZE = 40;

    public void initializeBoards(BoardPlayer player, BoardPlayer enemy) {
        this.playerBoard = player;
        this.enemyBoard = enemy;
        buildGrid(playerGrid);
        buildGrid(enemyGrid);
        drawPlayerShips();

        machineThread = new MachineThread(playerBoard);
        machineThread.setListener((row, col, result) -> {
            paintShot(playerGrid, row, col, result);
            if(playerBoard.allShipsSunk()){
                System.out.println("perdiste");
            }
            if(result == ShotResult.WATER){
                playerTurn = true;
            } else {
                machineThread.playTurn();
            }
        });
        prepareEnemyClicks();
    }

    @FXML
    private void showEnemyBoard() {
        for (Ship ship : enemyBoard.getPlacedShips()) {

            // Obtener la primera celda del barco
            var start = ship.getOccupiedCells().get(0);
            int row = start.getRow();
            int col = start.getCol();

            ship.updateVisualSize(CELL_SIZE);

            GridPane.setRowIndex(ship, row);
            GridPane.setColumnIndex(ship, col);

            // Evitar agregarlo 2 veces
            if (!enemyGrid.getChildren().contains(ship)) {
                enemyGrid.getChildren().add(ship);
            }
        }
    }


    public double getCELL_SIZE() {
        return CELL_SIZE;
    }

    private void buildGrid(GridPane grid) {

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
                cell.setPrefSize(CELL_SIZE,CELL_SIZE);

                cell.setStyle("""
                -fx-background-color: #1e3a8a;
                -fx-border-color: white;
                -fx-border-width: 1;
            """);

                cell.getProperties().put("row", r);
                cell.getProperties().put("col", c);

                grid.add(cell, c, r);
            }
        }
    }


    private void drawPlayerShips() {

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

    private void prepareEnemyClicks() {
        for (Node n : enemyGrid.getChildren()) {
            n.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handlePlayerShot);
        }
    }
    private void handlePlayerShot(MouseEvent e) {

        if (!playerTurn) return;

        Pane clicked = (Pane) e.getSource();

        int row = (int) clicked.getProperties().get("row");
        int col = (int) clicked.getProperties().get("col");

        ShotResult result = enemyBoard.shoot(row, col);

        if (result == null) return;

        paintShot(enemyGrid, row, col, result);

        // Si hunde todo → gana el jugador
        if (result == ShotResult.SUNK && enemyBoard.allShipsSunk()) {
            System.out.println("GANASTE");
            return;
        }

        if (result == ShotResult.WATER) {
            playerTurn = false;
            machineThread.playTurn();  // inicia hilo de IA
        }
    }

    private void paintShot(GridPane grid, int row, int col, ShotResult result) {
        Node shape;

        switch (result) {
            case WATER -> shape = new WaterShape(CELL_SIZE);
            case HIT -> shape = new TouchedShape(CELL_SIZE);
            case SUNK -> shape = new SunkenShape(CELL_SIZE);
            default -> { return; }
        }

        grid.add(shape, col, row);
    }
}



