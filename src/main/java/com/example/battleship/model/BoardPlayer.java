package com.example.battleship.model;

import com.example.battleship.model.exceptions.ShipPlacementException;
import java.io.Serializable;
import java.util.*;

/**
 * Represents the player's board in the Battleship game.
 * Manages ship placement, shooting mechanics, and board state.
 */
public class BoardPlayer implements Serializable {

    private final int rows = 10;
    private final int cols = 10;

    // main board structure
    private final ArrayList<ArrayList<Cell>> grid = new ArrayList<>();

    // ships placed in order
    private final Deque<Ship> placedShips = new LinkedList<>();

    // map of occupied cells
    private final Map<Cell, Ship> occupiedMap = new HashMap<>();

    /** Constructor initializes the board grid with empty cells */
    public BoardPlayer() {
        for (int r = 0; r < rows; r++) {
            ArrayList<Cell> rowList = new ArrayList<>();
            for (int c = 0; c < cols; c++) {
                rowList.add(new Cell(r, c));
            }
            grid.add(rowList);
        }
    }


    /**     * Retrieves the cell at the specified row and column.
     *
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @return The Cell object at the specified coordinates.
     */
    public Cell getCell(int row, int col) {
        return grid.get(row).get(col);
    }

    /** Check if the given coordinates are inside the board */
    public boolean isInside(int row, int col) {
        return row >= 0 && col >= 0 && row < rows && col < cols;
    }

    // Check if the ship fits and there are no conflicts
    /**
     * Checks if a ship can be placed at the specified coordinates and orientation.
     *
     * @param ship       The ship to be placed.
     * @param row        The starting row index.
     * @param col        The starting column index.
     * @param horizontal True if the ship is to be placed horizontally, false for vertical.
     * @return True if the ship can be placed, false otherwise.
     */
    public boolean canPlace(Ship ship, int row, int col, boolean horizontal) {

        int size = ship.getSize();

        // Validate initial coordinates
        if (row < 0 || col < 0) return false;

        // Verify that the boat fits completely according to the orientation.
        if (horizontal) {
            if (col + size > 10) return false;   //exits to the right
        } else {
            if (row + size > 10) return false;   //exit to the right
        }

        // Validate collisions cell by cell
        for (int i = 0; i < size; i++) {

            int r = horizontal ? row : row + i;
            int c = horizontal ? col + i : col;

            Cell cell = getCell(r, c);

            // If the cell is already occupied, it cannot be placed
            if (cell.isOccupied()) {
                return false;
            }
        }

        // Everything is valid
        return true;
    }




    /**
     * Places a ship on the board at the specified coordinates and orientation.
     *
     * @param ship       The ship to be placed.
     * @param row        The starting row index.
     * @param col        The starting column index.
     * @param horizontal True if the ship is to be placed horizontally, false for vertical.
     * @throws ShipPlacementException If the ship cannot be placed due to collisions or boundaries.
     */
    public void placeShip(Ship ship, int row, int col, boolean horizontal)
            throws ShipPlacementException {

        int size = ship.getSize();

        // Validate collisions and boundaries
        if (!canPlace(ship, row, col, horizontal)) {
            throw new ShipPlacementException("El barco no cabe o choca.");
        }

        //CLEAN the ship's previous condition
        ship.clearCells();

        //Permanently place
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


    /**
     * Shoots at the specified cell on the board.
     *
     * @param row The row index of the target cell.
     * @param col The column index of the target cell.
     * @return The result of the shot (HIT, MISS, SUNK) or null if the cell was already shot.
     */
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

    /**
     * Checks if all ships on the board have been sunk.
     *
     * @return True if all ships are sunk, false otherwise.
     */
    public boolean allShipsSunk() {
        for (Ship s : placedShips) {
            if (!s.isSunk()) {
                return false;
            }
        }
        return true;
    }


    /**
     * Relocates a ship after rotation to new coordinates and orientation.
     *
     * @param ship       The ship to be relocated.
     * @param row        The new starting row index.
     * @param col        The new starting column index.
     * @param horizontal True if the ship is to be placed horizontally, false for vertical.
     */
    public void relocateShipAfterRotation(Ship ship, int row, int col, boolean horizontal) {

        //Delete previous occupation
        for (Cell c : ship.getOccupiedCells()) {
            occupiedMap.remove(c);
            c.setOccupied(false);
        }

        ship.clearCells();

        //Reassign new cells according to orientation
        int size = ship.getSize();
        for (int i = 0; i < size; i++) {
            int r = horizontal ? row : row + i;
            int c = horizontal ? col + i : col;

            Cell cell = getCell(r, c);
            cell.setOccupied(true);
            occupiedMap.put(cell, ship);
            ship.addCell(cell);
        }

        // Update internal guidance
        ship.setHorizontal(horizontal);
    }

    /** Getter for placed ships */
    public Deque<Ship> getPlacedShips() {
        return placedShips;
    }

    /**
     * Automatically places ships on the board for an AI player.
     *
     * @param cellSize The size of each cell for ship dimensions.
     * @throws ShipPlacementException If a ship cannot be placed after multiple attempts.
     */
    public void placeShipsAutomatically(double cellSize) throws ShipPlacementException {

        //Create the ships that the AI must position
        List<Ship> shipsToPlace = new ArrayList<>();

        shipsToPlace.add(new Carrier(cellSize));          // 1 carrier
        shipsToPlace.add(new Submarine(cellSize));        // 2 submarines
        shipsToPlace.add(new Submarine(cellSize));
        shipsToPlace.add(new Destroyer(cellSize));        // 3 destroyers
        shipsToPlace.add(new Destroyer(cellSize));
        shipsToPlace.add(new Destroyer(cellSize));
        shipsToPlace.add(new Frigate(cellSize));          // 4 fragates
        shipsToPlace.add(new Frigate(cellSize));
        shipsToPlace.add(new Frigate(cellSize));
        shipsToPlace.add(new Frigate(cellSize));

        Random random = new Random();

        // Places every ship
        for (Ship ship : shipsToPlace) {

            boolean placed = false;
            int attempts = 0;
            final int MAX_ATTEMPTS = 1000;

            while (!placed && attempts < MAX_ATTEMPTS) {
                attempts++;

                int row = random.nextInt(10);
                int col = random.nextInt(10);
                boolean horizontal = random.nextBoolean();

                // Centralized validation
                if (canPlace(ship, row, col, horizontal)) {

                    placeShip(ship, row, col, horizontal);
                    placed = true;
                }
            }

            //Infinite loop protection
            if (!placed) {
                throw new ShipPlacementException(
                        "No se pudo colocar el barco: " + ship.getClass().getSimpleName()
                );
            }
        }
    }

}

