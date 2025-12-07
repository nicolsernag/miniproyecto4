package com.example.battleship.model;

import java.util.Objects;

/**
 * Representa una celda del tablero.
 * Debe usarse la misma instancia devuelta por BoardPlayer.getCell(row,col)
 * para evitar confusiones en el Map<Cell,Ship>.
 */
public class Cell {
    private final int row;
    private final int col;
    private boolean occupied = false;
    private boolean shot = false;


    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }

    public boolean isOccupied() { return occupied; }

    // <-- método público que necesitas -->
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public boolean isShot() { return shot; }
    public void markShot() { this.shot = true; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;
        Cell cell = (Cell) o;
        return row == cell.row && col == cell.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "Cell(" + row + "," + col + ", occ=" + occupied + ")";
    }
}

