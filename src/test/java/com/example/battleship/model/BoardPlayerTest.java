package com.example.battleship.model;

import com.example.battleship.model.exceptions.ShipPlacementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link BoardPlayer} class.
 * <p>
 * These tests verify the correct placement of ships on the board,
 * handling of overlapping ships, and the results of shooting at
 * different coordinates on the board.
 * </p>
 */
class BoardPlayerTest {

    private BoardPlayer board;
    private Ship destroyer;

    /**
     * Initializes a fresh {@link BoardPlayer} instance and a {@link Destroyer}
     * ship before each test execution.
     */
    @BeforeEach
    void setUp() {
        board = new BoardPlayer();
        destroyer = new Destroyer(40);
    }

    // The ship fits inside the board
    /**
     * Tests that a ship can be placed within the boundaries of the board.
     * <p>
     * The destroyer ship of size 2 is checked for placement at (0,0)
     * in a horizontal orientation, which should succeed.
     * </p>
     */
    @Test
    void canPlaceShipInsideBoard() {
        boolean result = board.canPlace(destroyer, 0, 0, true);
        assertTrue(result);
    }

    // The ship does NOT fit outside the board
    /**
     * Tests that a ship cannot be placed outside the boundaries of the board.
     * <p>
     * The destroyer ship of size 2 is checked for placement at (0,9)
     * in a horizontal orientation, which should fail.
     * </p>
     */
    @Test
    void cannotPlaceShipOutsideBoard() {
        boolean result = board.canPlace(destroyer, 0, 9, true);
        assertFalse(result);
    }

    // A ship can be positioned correctly
    /**
     * Tests that a ship can be successfully placed on the board.
     * <p>
     * The destroyer ship is placed at (2,2) in a horizontal orientation,
     * and the test verifies that it is recorded in the list of placed ships.
     * </p>
     *
     * @throws ShipPlacementException if the ship cannot be placed
     */
    @Test
    void placeShipSuccessfully() throws ShipPlacementException {
        board.placeShip(destroyer, 2, 2, true);
        assertEquals(1, board.getPlacedShips().size());
    }

    //Ships cannot overlap.
    /**
     * Tests that ships cannot overlap on the board.
     * <p>
     * A destroyer ship is first placed at (0,0) in a horizontal orientation.
     * Then, an attempt is made to place another destroyer at the same coordinates,
     * which should fail due to overlap.
     * </p>
     *
     * @throws ShipPlacementException if the first ship cannot be placed
     */
    @Test
    void cannotOverlapShips() throws ShipPlacementException {
        Ship s1 = new Destroyer(40);
        Ship s2 = new Destroyer(40);

        board.placeShip(s1, 0, 0, true);

        boolean result = board.canPlace(s2, 0, 0, true);
        assertFalse(result);
    }

    // Shot into the water
    /**
     * Tests that shooting at an empty cell returns WATER.
     * <p>
     * A destroyer ship is placed at (0,0) in a horizontal orientation.
     * A shot is then fired at (5,5), which should return WATER since
     * there is no ship at that location.
     * </p>
     *
     * @throws ShipPlacementException if the ship cannot be placed
     */
    @Test
    void shootWaterReturnsWater() throws ShipPlacementException {
        board.placeShip(destroyer, 0, 0, true);

        ShotResult result = board.shoot(5, 5);

        assertEquals(ShotResult.WATER, result);
    }

    // Shot at a ship returns HIT
    /**
     * Tests that shooting at a ship returns HIT.
     * <p>
     * A destroyer ship is placed at (0,0) in a horizontal orientation.
     * A shot is then fired at (0,0), which should return HIT since
     * there is a ship at that location.
     * </p>
     *
     * @throws ShipPlacementException if the ship cannot be placed
     */
    @Test
    void shootShipReturnsHit() throws ShipPlacementException {
        board.placeShip(destroyer, 0, 0, true);

        ShotResult result = board.shoot(0, 0);

        assertEquals(ShotResult.HIT, result);
    }

    // Sinking a ship returns SUNK
    /**
     * Tests that sinking a ship returns SUNK.
     * <p>
     * A destroyer ship is placed at (0,0) in a horizontal orientation.
     * Shots are then fired at all parts of the destroyer, and the final
     * shot should return SUNK.
     * </p>
     *
     * @throws ShipPlacementException if the ship cannot be placed
     */
    @Test
    void sinkShipReturnsSunk() throws ShipPlacementException {
        board.placeShip(destroyer, 0, 0, true);

        //Hit all cells of the destroyer (size 2)
        board.shoot(0, 0);
        ShotResult result = board.shoot(0, 1);

        assertEquals(ShotResult.SUNK, result);
    }
}

