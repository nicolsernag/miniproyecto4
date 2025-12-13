package com.example.battleship.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Cell class.
 */
class CellTest {

    private Cell cell;


    /**
     * Sets up a new Cell instance before each test.
     */
    @BeforeEach
    void setUp() {
        cell = new Cell(3, 5);
    }

    // Verify that the row and column are assigned correctly when creating the cell
    /**
     * Tests that the Cell constructor correctly assigns row and column values.
     */
    @Test
    void cellStoresRowAndColumnCorrectly() {
        assertEquals(3, cell.getRow());
        assertEquals(5, cell.getCol());
    }

    // Check that a cell is not occupied by default
    /**
     * Tests that a newly created Cell is not occupied by default.
     */
    @Test
    void cellIsNotOccupiedByDefault() {
        assertFalse(cell.isOccupied());
    }

    // Verify that setOccupied correctly changes the occupancy status
    /**
     * Tests that the setOccupied method correctly updates the occupied state of the Cell.
     */
    @Test
    void setOccupiedUpdatesOccupiedState() {
        cell.setOccupied(true);
        assertTrue(cell.isOccupied());

        cell.setOccupied(false);
        assertFalse(cell.isOccupied());
    }

    // Verify that when a shot is triggered, the cell is marked as triggered.
    /**
     * Tests that the markShot method correctly marks the Cell as shot.
     */
    @Test
    void markShotSetsShotToTrue() {
        assertFalse(cell.isShot());

        cell.markShot();
        assertTrue(cell.isShot());
    }

    // Verify that two cells with the same row and column are equal
    /**
     * Tests that two Cell instances with the same row and column are considered equal.
     */
    @Test
    void cellsWithSameRowAndColumnAreEqual() {
        Cell sameCell = new Cell(3, 5);
        Cell differentCell = new Cell(4, 5);

        assertEquals(cell, sameCell);
        assertNotEquals(cell, differentCell);
    }
}

