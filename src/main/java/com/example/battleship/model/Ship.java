package com.example.battleship.model;

import javafx.scene.layout.Pane;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Clase base para todos los barcos.
 * - Extiende Pane para poder contener Shapes (getChildren()) y ser añadido al GridPane.
 * - Mantiene ArrayList, Map y Deque para cumplir los requisitos de estructuras de datos.
 */
public abstract class Ship extends Pane implements Serializable {

    private final int size;


    // Estructuras solicitadas
    protected final ArrayList<Cell> occupiedCells = new ArrayList<>();        // ArrayList
    protected final Map<Integer, Boolean> segmentHit = new HashMap<>();      // Map: índice de segmento -> golpeado?
    protected final Deque<ArrayList<Cell>> lastPositions = new LinkedList<>(); // Deque para historial de posiciones

    // Orientación por defecto (true = horizontal, false = vertical)
    protected boolean horizontal = true;

    /**
     * Constructor: todos los barcos deben llamar super(size)
     * @param size número de celdas que ocupa el barco
     */
    public Ship(int size) {
        this.size = size;
        this.getProperties().put("size", size);

        for (int i = 0; i < size; i++) {
            segmentHit.put(i, false);
        }
    }

    /* ----------------- GETTERS/SETTERS ----------------- */

    public int getSize() {
        return size;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
        this.getProperties().put("orientation", horizontal ? "H" : "V");
    }

    public void toggleOrientation(double cellSize) {
        if (placed) return;
        horizontal = !horizontal;
        this.setRotate((this.getRotate() + 90) % 360);
        updateVisualSize(cellSize);
    }



    /* ----------------- POSICIÓN Y CELDAS ----------------- */



    private boolean placed = false;


    public void setPlaced(boolean placed) {
        this.placed = placed;
    }

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
     * Añade una celda al barco (usa BoardPlayer.placeShip para la lógica completa).
     */
    public void addCell(Cell cell) {
        if (occupiedCells.size() < size) {
            occupiedCells.add(cell);
        }
    }

    public void clearCells() {
        occupiedCells.clear();
    }

    public ArrayList<Cell> getOccupiedCells() {
        return occupiedCells;
    }

    /**
     * Recupera la última posición guardada (si existe).
     */
    public void undoPosition() {
        if (!lastPositions.isEmpty()) {
            ArrayList<Cell> prev = lastPositions.pop();
            occupiedCells.clear();
            occupiedCells.addAll(prev);
        }
    }


    /* ----------------- GOLPES / ESTADO ----------------- */

    /**
     * Marca el segmento index como golpeado.
     * @param index índice de segmento (0..size-1)
     */
    public void registerHit(int index) {
        if (segmentHit.containsKey(index)) segmentHit.put(index, true);
    }

    /**
     * Indica si el barco está hundido (todos los segmentos golpeados).
     */
    public boolean isSunk() {
        return segmentHit.values().stream().allMatch(Boolean::booleanValue);
    }
}



