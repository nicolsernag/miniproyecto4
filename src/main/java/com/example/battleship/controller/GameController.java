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

    // ----------------------------
    // INICIALIZACIÓN PRINCIPAL
    // ----------------------------
    public void initializeBoards(BoardPlayer player, BoardPlayer enemy) {

        this.playerBoard = player;
        this.enemyBoard = enemy;

        // Intentar cargar partida previa
        GameState saved = SerializableFileHandler.loadGameState();

        if (saved != null) {
            System.out.println("Cargando partida previa...");

            playerBoard.loadFromMatrix(saved.getPlayerMatrix());
            enemyBoard.loadFromMatrix(saved.getEnemyMatrix());
            playerTurn = saved.isPlayerTurn();

            this.playerNickname = saved.getPlayerNickname();

            if (nicknameField != null)
                nicknameField.setText(playerNickname);
        }

        // Construcción visual
        buildGrid(playerGrid);
        buildGrid(enemyGrid);

        drawPlayerShips();

        restorePreviousShots(playerGrid, playerBoard);
        restorePreviousShots(enemyGrid, enemyBoard);

        // --- Máquina ---
        machineThread = new MachineThread(playerBoard);
        machineThread.setListener((row, col, result) -> {
            paintShot(playerGrid, row, col, result);

            if (playerBoard.allShipsSunk()) {
                System.out.println("PERDISTE");
            }

            // Cambiar turno
            if (result == ShotResult.WATER) {
                playerTurn = true;
            } else {
                machineThread.playTurn();
            }

            saveCurrentState(); // Guardar jugada de la IA
        });

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

        saveCurrentState(); // Guardado automático
    }

    // ----------------------------
    // GUARDAR ESTADO COMPLETO
    // ----------------------------
    private void saveCurrentState() {

        if (nicknameField != null && !nicknameField.getText().isBlank()) {
            playerNickname = nicknameField.getText().trim();
        }

        GameState state = new GameState(
                playerBoard.toMatrix(),
                enemyBoard.toMatrix(),
                playerTurn,
                playerNickname,
                playerBoard.countSunkShips(),
                enemyBoard.countSunkShips()
        );

        SerializableFileHandler.saveGameState(state);

        FileManager.savePlayerData(
                playerNickname,
                playerBoard.countSunkShips(),
                enemyBoard.countSunkShips()
        );

        System.out.println("Estado guardado.");
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
}




