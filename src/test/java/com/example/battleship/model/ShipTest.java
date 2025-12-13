package com.example.battleship.model;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Ship class.
 */
class ShipTest {

    private Ship ship;


    /**
     * * Initializes the JavaFX platform before running tests.
     */
    @BeforeAll
    static void initJavaFX() {
        Platform.startup(() -> {});
    }

    /**
     * Sets up a new Ship instance before each test.
     */
    @BeforeEach
    void setUp() {
        ship = new TestShip(3);
    }

    // Verify that the ship size is initialized correctly
    /**
     * Test to verify that the ship size is initialized correctly.
     */
    @Test
    void shipSizeIsCorrect() {
        assertEquals(3, ship.getSize());
    }

    // Check that the ship's default orientation is horizontal.
    /**
     * Test to check that the ship's default orientation is horizontal.
     */
    @Test
    void shipIsHorizontalByDefault() {
        assertTrue(ship.isHorizontal());
    }

    // Check that the ship's default orientation is horizontal.
    /**
     * Test to check that setting the ship's orientation works correctly.
     */
    @Test
    void setHorizontalChangesOrientation() {
        ship.setHorizontal(false);
        assertFalse(ship.isHorizontal());
    }

    // Check that the visual size changes depending on the orientation.
    /**
     * Test to check that the visual size updates correctly based on orientation.
     */
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

    // Verify that cells can be added to the ship up to its maximum size
    /**
     * Test to verify that cells can be added to the ship up to its maximum size.
     */
    @Test
    void addCellAddsCellsUpToShipSize() {
        ship.addCell(new Cell(0, 0));
        ship.addCell(new Cell(0, 1));
        ship.addCell(new Cell(0, 2));
        ship.addCell(new Cell(0, 3)); // this one shouldn't go in

        assertEquals(3, ship.getOccupiedCells().size());
    }

    // Verify that clearCells removes all occupied cells
    /**
     * Test to verify that clearCells removes all occupied cells.
     */
    @Test
    void clearCellsRemovesAllOccupiedCells() {
        ship.addCell(new Cell(1, 1));
        ship.addCell(new Cell(1, 2));

        ship.clearCells();

        assertTrue(ship.getOccupiedCells().isEmpty());
    }

    // Check that a ship does not sink if not all segments have been hit
    /**
     * Test to check that a ship does not sink if not all segments have been hit.
     */
    @Test
    void shipIsNotSunkWhenNotAllSegmentsHit() {
        ship.registerHit(0);
        ship.registerHit(1);

        assertFalse(ship.isSunk());
    }

    // Verify that the ship sinks when all segments are hit.
    /**
     * Test to verify that the ship sinks when all segments are hit.
     */
    @Test
    void shipIsSunkWhenAllSegmentsAreHit() {
        ship.registerHit(0);
        ship.registerHit(1);
        ship.registerHit(2);

        assertTrue(ship.isSunk());
    }

    /**
     * A simple subclass of Ship for testing purposes.
     */
    private static class TestShip extends Ship {
        public TestShip(int size) {
            super(size);
        }
    }
}

