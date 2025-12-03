package com.example.battleship.model;

import com.example.battleship.model.Cell;

import java.util.*;

public class BoardPlayer {

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

        int size = (int) ship.getProperties().get("size");

        for (int i = 0; i < size; i++) {

            int r = horizontal ? row : row + i;
            int c = horizontal ? col + i : col;

            if (!isInside(r, c)) return false;               // fuera de tablero
            if (occupiedMap.containsKey(getCell(r, c))) return false; // ya ocupado
        }
        return true;
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
}

