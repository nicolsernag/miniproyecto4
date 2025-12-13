package com.example.battleship.model;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase abstracta Ship.
 * Se usa una implementación concreta de prueba (TestShip).
 */
class ShipTest {

    private Ship ship;

    /**
     * Inicializa JavaFX una sola vez para permitir el uso de Pane en pruebas.
     */
    @BeforeAll
    static void initJavaFX() {
        Platform.startup(() -> {});
    }

    /**
     * Crea un barco de prueba antes de cada test.
     */
    @BeforeEach
    void setUp() {
        ship = new TestShip(3);
    }

    // 🚢 Verifica que el tamaño del barco se inicializa correctamente
    @Test
    void shipSizeIsCorrect() {
        assertEquals(3, ship.getSize());
    }

    // 📐 Comprueba que la orientación por defecto del barco es horizontal
    @Test
    void shipIsHorizontalByDefault() {
        assertTrue(ship.isHorizontal());
    }

    // 🔄 Verifica que al cambiar la orientación se actualiza correctamente
    @Test
    void setHorizontalChangesOrientation() {
        ship.setHorizontal(false);
        assertFalse(ship.isHorizontal());
    }

    // 📏 Comprueba que el tamaño visual cambia según la orientación
    @Test
    void updateVisualSizeUpdatesWidthAndHeight() {
        double cellSize = 40;

        ship.setHorizontal(true);
        ship.updateVisualSize(cellSize);
        assertEquals(120, ship.getPrefWidth());
        assertEquals(40, ship.getPrefHeight());

        ship.setHorizontal(false);
        ship.updateVisualSize(cellSize);
        assertEquals(40, ship.getPrefWidth());
        assertEquals(120, ship.getPrefHeight());
    }

    // 🧩 Verifica que se pueden añadir celdas al barco hasta su tamaño máximo
    @Test
    void addCellAddsCellsUpToShipSize() {
        ship.addCell(new Cell(0, 0));
        ship.addCell(new Cell(0, 1));
        ship.addCell(new Cell(0, 2));
        ship.addCell(new Cell(0, 3)); // esta no debería entrar

        assertEquals(3, ship.getOccupiedCells().size());
    }

    // 🧹 Comprueba que clearCells elimina todas las celdas ocupadas
    @Test
    void clearCellsRemovesAllOccupiedCells() {
        ship.addCell(new Cell(1, 1));
        ship.addCell(new Cell(1, 2));

        ship.clearCells();

        assertTrue(ship.getOccupiedCells().isEmpty());
    }

    // 💥 Verifica que un barco no se hunde si no todos los segmentos han sido golpeados
    @Test
    void shipIsNotSunkWhenNotAllSegmentsHit() {
        ship.registerHit(0);
        ship.registerHit(1);

        assertFalse(ship.isSunk());
    }

    // 🚢💣 Comprueba que el barco se hunde cuando todos los segmentos son golpeados
    @Test
    void shipIsSunkWhenAllSegmentsAreHit() {
        ship.registerHit(0);
        ship.registerHit(1);
        ship.registerHit(2);

        assertTrue(ship.isSunk());
    }

    /**
     * Implementación concreta de Ship solo para pruebas.
     */
    private static class TestShip extends Ship {
        public TestShip(int size) {
            super(size);
        }
    }
}

