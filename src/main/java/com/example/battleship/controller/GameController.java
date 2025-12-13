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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameController {

    @FXML
    private GridPane playerGrid; //tablero del jugador(visual)
    @FXML
    private GridPane enemyGrid;
    @FXML
    private Label runTimer;//tablero del enemigo(interactivo)
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
                    var controller = LoseStage.getInstance().getController(); // <- paréntesis añadidos
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


        public double getCELL_SIZE () {
            return CELL_SIZE;
        }

        @FXML
        private void handleHide(){
            if (enemyBoard == null || enemyGrid == null) return;

            // Recorre los barcos que fueron colocados y los elimina del GridPane
            for (Ship ship : enemyBoard.getPlacedShips()) {
                // El método remove() del Pane (al que pertenece GridPane) verifica si el nodo existe y lo elimina
                enemyGrid.getChildren().remove(ship);
                hideButton.setText("");
            }

        }


    @FXML
    private void showEnemyBoard() {
        hideButton.setText("Ocultar barcos");
        if (enemyBoard == null || enemyGrid == null) return;
        for (Ship ship : enemyBoard.getPlacedShips()) {

            // Obtener la primera celda del barco
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
                      cell.setPrefSize(CELL_SIZE, CELL_SIZE);

                cell.setStyle(" -fx-background-color: #a5b7c6; -fx-border-color: white; -fx-border-width: 1; ");

                      cell.getProperties().put("row", r);
                cell.getProperties().put("col", c);

              grid.add(cell, c, r);
            }
          }
        }

        public void updateTimerLabel ( int seconds){
            runTimer.setText(seconds + "s");
        }

        public void handleTimeExpired () {
            if (!playerTurn) return;
            msgHumanPlayer.setText("¡Perdiste el turno por tiempo!");
            //newPlayerTurn.setText("");

            machineThread.playTurn();
        }


        private void drawPlayerShips() {

        for (Ship ship : playerBoard.getPlacedShips()) {

         //Obtiene la primera celda como ancla
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

        private void prepareEnemyClicks () {
            for (Node n : enemyGrid.getChildren()) {
                n.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handlePlayerShot);
            }
        }

        public void handlePlayerShot (MouseEvent e){

            if (!playerTurn) return;
            Pane clicked = (Pane) e.getSource();

            int row = (int) clicked.getProperties().get("row");
            int col = (int) clicked.getProperties().get("col");
            ShotResult result = enemyBoard.shoot(row, col);

            if (result == null) return;

            paintShot(enemyGrid, row, col, result);

            // Si gana el jugador
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


        private void paintShot(GridPane grid,int row, int col, ShotResult result){
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
                //Si la imagen falla, usamos el rectángulo rojo de respaldo para impacto
                Rectangle errorMark = new Rectangle(CELL_SIZE, CELL_SIZE, Color.RED);
                grid.add(errorMark, col, row); // Usa grid.add(mark, col, row) de la línea 39
                return;
            }
            grid.add(mark, col, row);
        }
    }








