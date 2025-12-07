package com.example.battleship.model;

import java.util.*;

public class BoardPlayer {

    private final int rows = 10;
    private final int cols = 10;
    private final Set<Cell> shots = new HashSet<>();


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
    public boolean canPlace(Ship ship, int startRow, int startCol, boolean horizontal) {

        int size = ship.getSize();

        // 1) Validar límites
        if (horizontal) {
            if (startCol + size > 10) return false;
            if (startRow < 0 || startRow >= 10) return false;
        } else {
            if (startRow + size > 10) return false;
            if (startCol < 0 || startCol >= 10) return false;
        }

        // 2) Validar colisiones con barcos existentes
        for (Ship placed : placedShips) {
            for (Cell occ : placed.getOccupiedCells()) {

                for (int i = 0; i < size; i++) {
                    int r = horizontal ? startRow : startRow + i;
                    int c = horizontal ? startCol + i : startCol;

                    // Si coincide con alguna celda ocupada → no se puede
                    if (occ.getRow() == r && occ.getCol() == c) {
                        return false;
                    }
                }
            }
        }

        return true;  // todo bien
    }


    public void placeShip(Ship ship, int row, int col, boolean horizontal) {

        int size = (int) ship.getProperties().get("size");

        for (int i = 0; i < size; i++) {

            int r = horizontal ? row : row + i;
            int c = horizontal ? col + i : col;

            Cell cell = getCell(r, c);
            cell.setOccupied(true);

            ship.addCell(cell);
            occupiedMap.put(cell, ship);
        }

        placedShips.add(ship);
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

}

