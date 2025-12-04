package com.example.battleship.model;

import javafx.scene.layout.Pane;

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
public abstract class Ship extends Pane {

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
        // guardar tamaño en propiedades (útil para FXML / controller)
        this.getProperties().put("size", size);

        // inicializar mapa de segmentos
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

    public void toggleOrientation() {
        setHorizontal(!horizontal);
        // rotación visual opcional: 0 / 90 grados
        this.setRotate(this.getRotate() + 90);
    }

    /* ----------------- POSICIÓN Y CELDAS ----------------- */

    public void rotateAt(int startRow, int startCol) {
        // 1. Cambiar orientación
        this.horizontal = !this.horizontal;
        this.setRotate(this.getRotate() == 0 ? 90 : 0);

        // 2. Recalcular celdas según orientación
        occupiedCells.clear();
        for (int i = 0; i < size; i++) {
            int r = horizontal ? startRow : startRow + i;
            int c = horizontal ? startCol + i : startCol;
            occupiedCells.add(new Cell(r, c));
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
     * Guarda la posición actual del barco en el historial (Deque).
     */
    public void savePosition() {
        // guardamos una copia superficial de la lista de celdas
        lastPositions.push(new ArrayList<>(occupiedCells));
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

    /**
     * Establece posición lógica del barco a partir de fila/col y orientación.
     * No cambia la parte gráfica; BoardPlayer debe invocar esto y luego anclar visualmente.
     *
     * @param startRow fila inicial
     * @param startCol columna inicial
     * @param horizontal true si horizontal, false si vertical
     */
    public void setPosition(int startRow, int startCol, boolean horizontal) {
        this.horizontal = horizontal;
        occupiedCells.clear();
        for (int i = 0; i < size; i++) {
            int r = horizontal ? startRow : startRow + i;
            int c = horizontal ? startCol + i : startCol;
            occupiedCells.add(new Cell(r, c));
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

    public boolean isHitAtSegment(int index) {
        return segmentHit.getOrDefault(index, false);
    }

    /**
     * Indica si el barco está hundido (todos los segmentos golpeados).
     */
    public boolean isSunk() {
        return segmentHit.values().stream().allMatch(Boolean::booleanValue);
    }
}



