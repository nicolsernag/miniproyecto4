package com.example.battleship.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a cell on the Battleship game board.
 */
public class Cell implements Serializable {
    private final int row;
    private final int col;
    private boolean occupied = false;
    private boolean shot = false;


    /**     * Constructs a Cell at the specified row and column.
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**     * Returns the row index of the cell.
     *
     * @return the row index
     */
    public int getRow() { return row; }
    public int getCol() { return col; }

    /**     * Checks if the cell is occupied by a ship.
     *
     * @return true if occupied, false otherwise
     */
    public boolean isOccupied() { return occupied; }

    /**     * Sets the occupied status of the cell.
     *
     * @param occupied true to mark as occupied, false otherwise
     */
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    /**     * Checks if the cell has been shot at.
     *
     * @return true if shot, false otherwise
     */
    public boolean isShot() { return shot; }

    /**     * Marks the cell as having been shot at.
     */
    public void markShot() { this.shot = true; }

    /**     * Checks equality based on row and column.
     *
     * @param o the object to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;
        Cell cell = (Cell) o;
        return row == cell.row && col == cell.col;
    }

    /**     * Generates hash code based on row and column.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    /**     * Returns a string representation of the cell.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return "Cell(" + row + "," + col + ", occ=" + occupied + ")";
    }
}

