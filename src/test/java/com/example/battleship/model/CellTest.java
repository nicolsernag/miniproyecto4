package com.example.battleship.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    private Cell cell;

    /**
     * Crea una celda nueva antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
        cell = new Cell(3, 5);
    }

    // 📍 Verifica que la fila y la columna se asignan correctamente al crear la celda
    @Test
    void cellStoresRowAndColumnCorrectly() {
        assertEquals(3, cell.getRow());
        assertEquals(5, cell.getCol());
    }

    // 🚢 Comprueba que una celda no está ocupada por defecto
    @Test
    void cellIsNotOccupiedByDefault() {
        assertFalse(cell.isOccupied());
    }

    // 🧱 Verifica que setOccupied cambia correctamente el estado de ocupación
    @Test
    void setOccupiedUpdatesOccupiedState() {
        cell.setOccupied(true);
        assertTrue(cell.isOccupied());

        cell.setOccupied(false);
        assertFalse(cell.isOccupied());
    }

    // 💥 Comprueba que al marcar un disparo la celda queda marcada como disparada
    @Test
    void markShotSetsShotToTrue() {
        assertFalse(cell.isShot());

        cell.markShot();
        assertTrue(cell.isShot());
    }

    // 🔁 Verifica que dos celdas con la misma fila y columna son iguales
    @Test
    void cellsWithSameRowAndColumnAreEqual() {
        Cell sameCell = new Cell(3, 5);
        Cell differentCell = new Cell(4, 5);

        assertEquals(cell, sameCell);
        assertNotEquals(cell, differentCell);
    }
}

