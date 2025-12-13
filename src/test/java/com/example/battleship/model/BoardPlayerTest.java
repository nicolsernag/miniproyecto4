package com.example.battleship.model;

import com.example.battleship.model.exceptions.ShipPlacementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardPlayerTest {

    private BoardPlayer board;
    private Ship destroyer;

    @BeforeEach
    void setUp() {
        board = new BoardPlayer();
        destroyer = new Destroyer(40);
    }

    // 1️⃣ El barco cabe dentro del tablero
    @Test
    void canPlaceShipInsideBoard() {
        boolean result = board.canPlace(destroyer, 0, 0, true);
        assertTrue(result);
    }

    // 2️⃣ El barco NO cabe fuera del tablero
    @Test
    void cannotPlaceShipOutsideBoard() {
        boolean result = board.canPlace(destroyer, 0, 9, true);
        assertFalse(result);
    }

    // 3️⃣ Se puede colocar un barco correctamente
    @Test
    void placeShipSuccessfully() throws ShipPlacementException {
        board.placeShip(destroyer, 2, 2, true);
        assertEquals(1, board.getPlacedShips().size());
    }

    // 4️⃣ NO se pueden sobreponer barcos
    @Test
    void cannotOverlapShips() throws ShipPlacementException {
        Ship s1 = new Destroyer(40);
        Ship s2 = new Destroyer(40);

        board.placeShip(s1, 0, 0, true);

        boolean result = board.canPlace(s2, 0, 0, true);
        assertFalse(result);
    }

    // 5️⃣ Disparo al agua
    @Test
    void shootWaterReturnsWater() throws ShipPlacementException {
        board.placeShip(destroyer, 0, 0, true);

        ShotResult result = board.shoot(5, 5);

        assertEquals(ShotResult.WATER, result);
    }

    // 6️⃣ Disparo a un barco devuelve HIT
    @Test
    void shootShipReturnsHit() throws ShipPlacementException {
        board.placeShip(destroyer, 0, 0, true);

        ShotResult result = board.shoot(0, 0);

        assertEquals(ShotResult.HIT, result);
    }

    // 7️⃣ Hundir un barco devuelve SUNK
    @Test
    void sinkShipReturnsSunk() throws ShipPlacementException {
        board.placeShip(destroyer, 0, 0, true);

        // Golpear todas las celdas del destroyer (tamaño 2)
        board.shoot(0, 0);
        ShotResult result = board.shoot(0, 1);

        assertEquals(ShotResult.SUNK, result);
    }
}

