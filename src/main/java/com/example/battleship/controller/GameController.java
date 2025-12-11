package com.example.battleship.controller;

import com.example.battleship.model.*;
import com.example.battleship.model.GameState.FileManager;
import com.example.battleship.model.GameState.GameState;
import com.example.battleship.model.serializable.SerializableFileHandler;
import com.example.battleship.model.threads.MachineThread;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.RowConstraints;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class GameController {

    @FXML private Button showEnemyBtn;

    @FXML private TextField nicknameField;
    private String playerNickname;

    @FXML private GridPane playerGrid;
    @FXML private GridPane enemyGrid;

    private BoardPlayer playerBoard;
    private BoardPlayer enemyBoard;

    private MachineThread machineThread;

    private boolean playerTurn = true;
    private final double CELL_SIZE = 40;

    // ----------------------------
    // Para recibir el nickname desde afuera
    // ----------------------------
    public void setPlayerNicknameField(TextField tf) {
        this.nicknameField = tf;
    }

    public void setPlayerNickname(String name) {
        this.playerNickname = name;
    }

    private List<Ship> playerShips;
    private List<Ship> machineShips;

    // Guardar partida actual
    public void saveCurrentGame() {
        GameState gameSave = new GameState(playerBoard, enemyBoard, playerShips, machineShips, playerTurn);
        SerializableFileHandler.saveGame(gameSave, "mi_partida.sav");
    }

    // Cargar partida
    public void loadSavedGame() {
        GameState loaded = SerializableFileHandler.loadGame("mi_partida.sav");
        if (loaded != null) {
            this.playerBoard = loaded.getPlayerBoard();
            this.enemyBoard = loaded.getMachineBoard();
            this.playerShips = loaded.getPlayerShips();
            this.machineShips = loaded.getMachineShips();
            this.playerTurn = loaded.isPlayerTurn();

            // Aquí actualizas la UI con los datos cargados
            //refreshUI();
        }
    }

    public void refreshUI() {
        // Limpiar los tableros
        buildGrid(playerGrid);
        buildGrid(enemyGrid);

        // ----------------------------
        // Dibujar barcos del jugador
        // ----------------------------
        for (Ship ship : playerShips) {
            ship.updateVisualSize(CELL_SIZE);
            Cell start = ship.getOccupiedCells().get(0);

            GridPane.setRowIndex(ship, start.getRow());
            GridPane.setColumnIndex(ship, start.getCol());

            if (!playerGrid.getChildren().contains(ship)) {
                playerGrid.getChildren().add(ship);
            }
        }

        // ----------------------------
        // Dibujar barcos enemigos (solo si quieres mostrar)
        // ----------------------------
        for (Ship ship : machineShips) {
            ship.updateVisualSize(CELL_SIZE);
            Cell start = ship.getOccupiedCells().get(0);

            GridPane.setRowIndex(ship, start.getRow());
            GridPane.setColumnIndex(ship, start.getCol());

            if (!enemyGrid.getChildren().contains(ship)) {
                enemyGrid.getChildren().add(ship);
            }
        }

        // ----------------------------
        // Restaurar disparos previos
        // ----------------------------
        restorePreviousShots(playerGrid, playerBoard);
        restorePreviousShots(enemyGrid, enemyBoard);

        // ----------------------------
        // Actualizar turno
        // ----------------------------
        //labelTurn.setText(playerTurn ? "Tu turno" : "Turno de la IA");

        // Preparar clicks en enemigo
        prepareEnemyClicks();
    }


    // ----------------------------
    // Dibujar GridPane
    // ----------------------------
    private void buildGrid(GridPane grid) {

        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();

        for (int i = 0; i < 10; i++) {
            ColumnConstraints cc = new ColumnConstraints(CELL_SIZE);
            grid.getColumnConstraints().add(cc);
        }

        for (int i = 0; i < 10; i++) {
            RowConstraints rc = new RowConstraints(CELL_SIZE);
            grid.getRowConstraints().add(rc);
        }

        grid.getChildren().clear();

        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {

                Pane cell = new Pane();
                cell.setPrefSize(CELL_SIZE, CELL_SIZE);

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

    // ----------------------------
    // Colocar barcos del jugador
    // ----------------------------
    private void drawPlayerShips() {

        for (Ship ship : playerBoard.getPlacedShips()) {

            Cell start = ship.getOccupiedCells().get(0);

            ship.updateVisualSize(CELL_SIZE);

            GridPane.setRowIndex(ship, start.getRow());
            GridPane.setColumnIndex(ship, start.getCol());

            if (!playerGrid.getChildren().contains(ship)) {
                playerGrid.getChildren().add(ship);
            }
        }
    }

    // ----------------------------
    // Restaurar disparos en tablero
    // ----------------------------
    private void restorePreviousShots(GridPane grid, BoardPlayer board) {

        Cell[][] cells = board.getCells();

        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {

                if (!cells[r][c].isShot()) continue;

                ShotResult res = board.getShotResultAt(r, c);
                if (res != null) {
                    paintShot(grid, r, c, res);
                }
            }
        }
    }

    // ----------------------------
    // EVENTO: Disparo del jugador
    // ----------------------------
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

        if (result == ShotResult.SUNK && enemyBoard.allShipsSunk()) {
            System.out.println("GANASTE");
        }

        if (result == ShotResult.WATER) {
            playerTurn = false;
            machineThread.playTurn();
        }

    }



    // ----------------------------
    // Pintar resultado del disparo
    // ----------------------------
    private void paintShot(GridPane grid, int row, int col, ShotResult result) {
        Node shape = switch (result) {
            case WATER -> new WaterShape(CELL_SIZE);
            case HIT -> new TouchedShape(CELL_SIZE);
            case SUNK -> new SunkenShape(CELL_SIZE);
        };

        grid.add(shape, col, row);
    }

    // Botón mostrar barcos enemigos
    @FXML
    private void showEnemyBoard() {
        for (Ship ship : enemyBoard.getPlacedShips()) {

            Cell start = ship.getOccupiedCells().get(0);
            ship.updateVisualSize(CELL_SIZE);

            GridPane.setRowIndex(ship, start.getRow());
            GridPane.setColumnIndex(ship, start.getCol());

            if (!enemyGrid.getChildren().contains(ship)) {
                enemyGrid.getChildren().add(ship);
            }
        }
    }

    public double getCELL_SIZE() {
        return CELL_SIZE;
    }

    private GameState loadedState;
    public void setLoadedState(GameState state) {
        this.loadedState = state;
    }

    public void initializeLoadedBoards(BoardPlayer player, BoardPlayer enemy) {

        this.playerBoard = player;
        this.enemyBoard = enemy;

        buildGrid(playerGrid);
        buildGrid(enemyGrid);

        // Dibujar barcos del jugador
        drawPlayerShips();

        // Restaurar disparos jugador → enemigo
        restorePreviousShots(enemyGrid, enemy);

        // Restaurar disparos enemigo → jugador
        restorePreviousShots(playerGrid, player);

        // Turno guardado
        if (loadedState != null) {
            this.playerTurn = loadedState.isPlayerTurn();
        }

        // Prepara la IA
        machineThread = new MachineThread(playerBoard);
        machineThread.setListener((row, col, result) -> {
            paintShot(playerGrid, row, col, result);

            if (playerBoard.allShipsSunk()) {
                System.out.println("Perdiste");
            }

            if (result == ShotResult.WATER) {
                playerTurn = true;
            } else {
                machineThread.playTurn();
            }
        });

        prepareEnemyClicks();
    }

    public void loadSavedGame(GridPane grid, String filePath) {
        try {
            // 1️⃣ Deserializar BoardPlayer desde archivo
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));
            BoardPlayer board = (BoardPlayer) in.readObject();
            in.close();

            // 2️⃣ Reconstruir mapas internos (occupiedMap y placedShips)
            board.rebuildShipsFromBoard();

            // 3️⃣ Actualizar la UI con los disparos previos
            Cell[][] cells = board.getCells();
            for (int r = 0; r < 10; r++) {
                for (int c = 0; c < 10; c++) {
                    if (!cells[r][c].isShot()) continue;

                    ShotResult res = board.getShotResultAt(r, c);
                    if (res != null) {
                        paintShot(grid, r, c, res); // tu método para dibujar HIT/WATER/SUNK
                    }
                }
            }

            // 4️⃣ Guardar referencia al tablero cargado en tu controlador si la necesitas
            this.playerBoard = board;

            System.out.println("Partida cargada correctamente.");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la partida.");
        }
    }


}




