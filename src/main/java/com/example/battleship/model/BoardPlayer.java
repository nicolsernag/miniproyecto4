package com.example.battleship.model;

import com.example.battleship.model.exceptions.ShipPlacementException;
import java.io.Serializable;
import java.util.*;

public class BoardPlayer implements Serializable {

    private final int rows = 10;
    private final int cols = 10;

    // Estructura principal del tablero
    private final ArrayList<ArrayList<Cell>> grid = new ArrayList<>();

    // Barcos colocados en orden
    private final Deque<Ship> placedShips = new LinkedList<>();

    // Mapa de celdas ocupadas → barco
    private final Map<Cell, Ship> occupiedMap = new HashMap<>();

    public BoardPlayer() {
        for (int r = 0; r < rows; r++) {
            ArrayList<Cell> rowList = new ArrayList<>();
            for (int c = 0; c < cols; c++) {
                rowList.add(new Cell(r, c));
            }
            grid.add(rowList);
        }
    }


    public Cell getCell(int row, int col) {
        return grid.get(row).get(col);
    }

    public boolean isInside(int row, int col) {
        return row >= 0 && col >= 0 && row < rows && col < cols;
    }

    // Verifica si el barco cabe y no hay conflicto
    public boolean canPlace(Ship ship, int row, int col, boolean horizontal) {

        int size = ship.getSize();

        // 1️⃣ Validar coordenadas iniciales
        if (row < 0 || col < 0) return false;

        // 2️⃣ Validar que el barco cabe completo según orientación
        if (horizontal) {
            if (col + size > 10) return false;   // se sale por la derecha
        } else {
            if (row + size > 10) return false;   // se sale por abajo
        }

        // 3️⃣ Validar colisiones celda por celda
        for (int i = 0; i < size; i++) {

            int r = horizontal ? row : row + i;
            int c = horizontal ? col + i : col;

            Cell cell = getCell(r, c);

            // Si la celda ya está ocupada, no se puede colocar
            if (cell.isOccupied()) {
                return false;
            }
        }

        // 4️⃣ Todo es válido
        return true;
    }





    public void placeShip(Ship ship, int row, int col, boolean horizontal)
            throws ShipPlacementException {

        int size = ship.getSize();

        // 1️⃣ Validar colisiones y límites
        if (!canPlace(ship, row, col, horizontal)) {
            throw new ShipPlacementException("El barco no cabe o choca.");
        }

        // 2️⃣ LIMPIAR estado previo del barco
        ship.clearCells();

        // 3️⃣ Colocar definitivamente
        for (int i = 0; i < size; i++) {
            int r = horizontal ? row : row + i;
            int c = horizontal ? col + i : col;

            Cell cell = getCell(r, c);
            cell.setOccupied(true);

            ship.addCell(cell);
            occupiedMap.put(cell, ship);
        }

        ship.setPlaced(true);
        placedShips.add(ship);
    }



    public ShotResult shoot(int row, int col){
        Cell cell = getCell(row, col);
        if(cell.isShot()){
            return null;
        }
        cell.markShot();
        if(!cell.isOccupied()){
            return ShotResult.WATER;
        }

        Ship ship = occupiedMap.get(cell);
        int index = ship.getOccupiedCells().indexOf(cell);
        ship.registerHit(index);

        if(ship.isSunk()) {
            return ShotResult.SUNK;
        }
        return ShotResult.HIT;

    }

    public boolean allShipsSunk() {
        for (Ship s : placedShips) {
            if (!s.isSunk()) {
                return false;
            }
        }
        return true;
    }


    public void relocateShipAfterRotation(Ship ship, int row, int col, boolean horizontal) {

        // 1. Eliminar ocupación anterior
        for (Cell c : ship.getOccupiedCells()) {
            occupiedMap.remove(c);
            c.setOccupied(false);
        }

        ship.clearCells();

        // 2. Reasignar celdas nuevas según orientación
        int size = ship.getSize();
        for (int i = 0; i < size; i++) {
            int r = horizontal ? row : row + i;
            int c = horizontal ? col + i : col;

            Cell cell = getCell(r, c);
            cell.setOccupied(true);
            occupiedMap.put(cell, ship);
            ship.addCell(cell);
        }

        // 3. Actualizar orientación interna
        ship.setHorizontal(horizontal);
    }

    public Deque<Ship> getPlacedShips() {
        return placedShips;
    }

    public void placeShipsAutomatically(double cellSize) throws ShipPlacementException {

        // 1️⃣ Crear los barcos que la IA debe posicionar
        List<Ship> shipsToPlace = new ArrayList<>();

        shipsToPlace.add(new Carrier(cellSize));          // 1 carrier
        shipsToPlace.add(new Submarine(cellSize));        // 2 submarinos
        shipsToPlace.add(new Submarine(cellSize));
        shipsToPlace.add(new Destroyer(cellSize));        // 3 destructores
        shipsToPlace.add(new Destroyer(cellSize));
        shipsToPlace.add(new Destroyer(cellSize));
        shipsToPlace.add(new Frigate(cellSize));          // 4 fragatas
        shipsToPlace.add(new Frigate(cellSize));
        shipsToPlace.add(new Frigate(cellSize));
        shipsToPlace.add(new Frigate(cellSize));

        Random random = new Random();

        // 2️⃣ Colocar cada barco
        for (Ship ship : shipsToPlace) {

            boolean placed = false;
            int attempts = 0;
            final int MAX_ATTEMPTS = 1000;

            while (!placed && attempts < MAX_ATTEMPTS) {
                attempts++;

                int row = random.nextInt(10);
                int col = random.nextInt(10);
                boolean horizontal = random.nextBoolean();

                // 🔒 Validación centralizada
                if (canPlace(ship, row, col, horizontal)) {

                    placeShip(ship, row, col, horizontal);
                    placed = true;
                }
            }

            // 3️⃣ Protección contra bucle infinito
            if (!placed) {
                throw new ShipPlacementException(
                        "No se pudo colocar el barco: " + ship.getClass().getSimpleName()
                );
            }
        }
    }

}

