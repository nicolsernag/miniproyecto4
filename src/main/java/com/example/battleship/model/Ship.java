package com.example.battleship.model;

import javafx.scene.layout.Pane;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Abstract class representing a Ship in the Battleship game.
 * Contains common properties and methods for all ship types.
 */
public abstract class Ship extends Pane implements Serializable {

    private final int size;


    //requested structures
    protected final ArrayList<Cell> occupiedCells = new ArrayList<>();        // ArrayList
    protected final Map<Integer, Boolean> segmentHit = new HashMap<>();      // Map: segment index -> HIT?
    protected final Deque<ArrayList<Cell>> lastPositions = new LinkedList<>(); // Deque for position history

    // Default orientation (true = horizontal, false = vertical)
    protected boolean horizontal = true;


    /**     * Constructor for Ship.
     *
     * @param size Size of the ship.
     */
    public Ship(int size) {
        this.size = size;
        this.getProperties().put("size", size);

        for (int i = 0; i < size; i++) {
            segmentHit.put(i, false);
        }
    }

    /* ----------------- GETTERS/SETTERS ----------------- */

    /**
     * Gets the size of the ship.
     *
     * @return Size of the ship.
     */
    public int getSize() {
        return size;
    }

    /**
     * Checks if the ship is oriented horizontally.
     *
     * @return True if horizontal, false if vertical.
     */
    public boolean isHorizontal() {
        return horizontal;
    }

    /**
     * Sets the orientation of the ship.
     *
     * @param horizontal True for horizontal, false for vertical.
     */
    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
        this.getProperties().put("orientation", horizontal ? "H" : "V");
    }

    /**
     * Toggles the orientation of the ship between horizontal and vertical.
     *
     * @param cellSize Size of each cell in the grid.
     */
    public void toggleOrientation(double cellSize) {
        if (placed) return;
        horizontal = !horizontal;
        this.setRotate((this.getRotate() + 90) % 360);
        updateVisualSize(cellSize);
    }



    /* ----------------- POSITION AND CELLS ----------------- */



    private boolean placed = false;


    /**
     * Checks if the ship is placed on the board.
     * @param placed
     */
    public void setPlaced(boolean placed) {
        this.placed = placed;
    }

    /**
     * Updates the visual size of the ship based on its orientation and cell size.
     *
     * @param cellSize Size of each cell in the grid.
     */
    public void updateVisualSize(double cellSize) {
        if (horizontal) {
            setPrefWidth(cellSize * size);
            setPrefHeight(cellSize);
        } else {
            setPrefWidth(cellSize);
            setPrefHeight(cellSize * size);
        }
    }


    /**
     * Adds a cell to the ship's occupied cells.
     *
     * @param cell Cell to be added.
     */
    public void addCell(Cell cell) {
        if (occupiedCells.size() < size) {
            occupiedCells.add(cell);
        }
    }

    /**
     * Clears all occupied cells of the ship.
     */
    public void clearCells() {
        occupiedCells.clear();
    }

    /**
     * Gets the list of occupied cells by the ship.
     *
     * @return List of occupied cells.
     */
    public ArrayList<Cell> getOccupiedCells() {
        return occupiedCells;
    }


    public void undoPosition() {
        if (!lastPositions.isEmpty()) {
            ArrayList<Cell> prev = lastPositions.pop();
            occupiedCells.clear();
            occupiedCells.addAll(prev);
        }
    }


    /* ----------------- BLOWS / STATUS ----------------- */


    /**
     * Registers a hit on the ship at the specified segment index.
     *
     * @param index Index of the segment that was hit.
     */
    public void registerHit(int index) {
        if (segmentHit.containsKey(index)) segmentHit.put(index, true);
    }


    /**
     * Checks if the ship is sunk (all segments hit).
     *
     * @return True if sunk, false otherwise.
     */
    public boolean isSunk() {
        return segmentHit.values().stream().allMatch(Boolean::booleanValue);
    }
}



