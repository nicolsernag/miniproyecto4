package com.example.battleship.controller;

import com.example.battleship.model.*;
import com.example.battleship.model.threads.MachineThread;
import com.example.battleship.model.threads.TimerThread;
import com.example.battleship.view.GameStage;
import com.example.battleship.view.LoseStage;
import com.example.battleship.view.WinStage;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Controller for the main game view.
 */

public class GameController {

    @FXML
    private GridPane playerGrid; //player's board (visual)
    @FXML
    private GridPane enemyGrid;
    @FXML
    private Label runTimer;//enemy's board (interactive)
    @FXML
    private Label msgHumanPlayer;
    @FXML
    private Button hideButton;
    @FXML
    private Button showEnemyBtn;

    private BoardPlayer playerBoard;
    private BoardPlayer enemyBoard;

    private MachineThread machineThread;
    private TimerThread timerThread;


    private boolean playerTurn = true;
    private final double CELL_SIZE = 40;


    /**
     * Initializes the game boards for the player and the enemy.
     * @param player
     * @param enemy
     */
    public void initializeBoards(BoardPlayer player, BoardPlayer enemy) {
        this.playerBoard = player;
        this.enemyBoard = enemy;
        buildGrid(playerGrid);
        buildGrid(enemyGrid);
        timerThread = new TimerThread(this);
        timerThread.start();
        drawPlayerShips();

        machineThread = new MachineThread(playerBoard);

        hideButton.setText("");
        msgHumanPlayer.setText("");
        machineThread.setListener((row, col, result) -> {
            paintShot(playerGrid, row, col, result);
            if (playerBoard.allShipsSunk()) {
                System.out.println("perdiste");
                try {
                    var controller = LoseStage.getInstance().getController();
                    GameStage.deleteInstance();
                } catch (java.io.IOException ex) {
                    System.err.println("No se pudo crear LoseStage: " + ex.getMessage());
                    ex.printStackTrace();
                }

            }
            if (result == ShotResult.WATER) {
                playerTurn = true;
                timerThread = new TimerThread(this);
                timerThread.start();
                msgHumanPlayer.setText("");
            } else {
                machineThread.playTurn();
            }
        });
        prepareEnemyClicks();
    }

        /**
         * Returns the size of each cell in the grid.
         * @return cell size
         */
        public double getCELL_SIZE () {
            return CELL_SIZE;
        }

        /**
         * Handles the hiding of enemy ships on the grid.
         */
        @FXML
        private void handleHide(){
            if (enemyBoard == null || enemyGrid == null) return;

            // It scans the ships that were placed and removes them from the Gridpane.
            for (Ship ship : enemyBoard.getPlacedShips()) {
                //
                enemyGrid.getChildren().remove(ship);
                hideButton.setText("");
            }

        }

    /**
     * Shows the enemy ships on the grid.
     */
    @FXML
    private void showEnemyBoard() {
        hideButton.setText("Ocultar barcos");
        if (enemyBoard == null || enemyGrid == null) return;
        for (Ship ship : enemyBoard.getPlacedShips()) {

            // obtain the first cell of the ship
            var start = ship.getOccupiedCells().get(0);
            int row = start.getRow();
            int col = start.getCol();

            ship.updateVisualSize(CELL_SIZE);

            GridPane.setHalignment(ship, HPos.LEFT);
            GridPane.setValignment(ship, VPos.TOP);

            ship.setTranslateX(0);
            ship.setTranslateY(0);

            GridPane.setRowIndex(ship, row);
            GridPane.setColumnIndex(ship, col);


            if (!enemyGrid.getChildren().contains(ship)) {
                enemyGrid.getChildren().add(ship);
            }
        }
}

    /**
     * Builds the grid for the game board.
     * @param grid
     */
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

                  StackPane cell = new StackPane();
                  cell.setPrefSize(CELL_SIZE, CELL_SIZE);

                cell.setStyle(" -fx-background-color: #a5b7c6; -fx-border-color: white; -fx-border-width: 1; ");

                      cell.getProperties().put("row", r);
                cell.getProperties().put("col", c);

              grid.add(cell, c, r);
            }
          }
        }

        /**
         * Updates the timer label with the remaining seconds.
         * @param seconds remaining time in seconds
         */
        public void updateTimerLabel ( int seconds){
            runTimer.setText(seconds + "s");
        }


        /**
         * Handles the event when the player's time expires.
         */
        public void handleTimeExpired () {
            if (!playerTurn) return;
            msgHumanPlayer.setText("¡Perdiste el turno por tiempo!");


            machineThread.playTurn();
        }

        /**
         * Draws the player's ships on the grid.
         */
        private void drawPlayerShips() {

        for (Ship ship : playerBoard.getPlacedShips()) {

         //gets the first cell as anchor
         Cell start = ship.getOccupiedCells().get(0);

          int row = start.getRow();
          int col = start.getCol();

        // resize the ship according to orientation
              ship.updateVisualSize(CELL_SIZE);

        // It places it in the gridpane
            GridPane.setRowIndex(ship, row);
          GridPane.setColumnIndex(ship, col);

        playerGrid.getChildren().add(ship);
        }
        }

        private void prepareEnemyClicks () {
            for (Node n : enemyGrid.getChildren()) {
                n.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handlePlayerShot);
            }
        }

        /**
         * Handles the player's shot on the enemy grid.
         * @param e MouseEvent triggered by the player's click
         */
        public void handlePlayerShot (MouseEvent e){

            if (!playerTurn) return;
            Pane clicked = (Pane) e.getSource();

            int row = (int) clicked.getProperties().get("row");
            int col = (int) clicked.getProperties().get("col");
            ShotResult result = enemyBoard.shoot(row, col);

            if (result == null) return;

            paintShot(enemyGrid, row, col, result);

            // if player wins
            if (result == ShotResult.SUNK && enemyBoard.allShipsSunk()) {
                System.out.println("GANASTE");
                try {
                    GameStage.deleteInstance();
                    var controller = WinStage.getInstance().getController();
                } catch (java.io.IOException ex) {
                    System.err.println("No se puede crear Win Stage: " + ex.getMessage());
                    ex.printStackTrace();
                }
                return;
            }

            if (result == ShotResult.WATER) {
                playerTurn = false;
                if (timerThread != null) {
                    timerThread.stopTimer();
                    timerThread = null;
                    runTimer.setText("");
                }
                machineThread.playTurn();// inicia hilo de IA
            }
        }

    /**
     * Paints the result of a shot on the grid.
     * @param grid GridPane where the shot is painted
     * @param row Row index of the shot
     * @param col Column index of the shot
     * @param result Result of the shot (WATER, HIT, SUNK)
     */
    private void paintShot(GridPane grid, int row, int col, ShotResult result) {

        StackPane cell = null;

        // Looks for the right cell
        for (Node n : grid.getChildren()) {
            if (GridPane.getRowIndex(n) == row &&
                    GridPane.getColumnIndex(n) == col) {
                cell = (StackPane) n;
                break;
            }
        }

        if (cell == null) return;

        // Avoid to shoot the same cell multiple times
        if (cell.getProperties().containsKey("shot")) return;

        cell.getProperties().put("shot", true);

        Node shape;

        // Create the shape according to the result
        switch (result) {
            case WATER -> shape = new WaterShape(CELL_SIZE);
            case HIT   -> shape = new TouchedShape(CELL_SIZE);
            case SUNK  -> shape = new SunkenShape(CELL_SIZE);
            default    -> { return; }
        }

        //Add the shot above the cell
        cell.getChildren().add(shape);
    }
}








