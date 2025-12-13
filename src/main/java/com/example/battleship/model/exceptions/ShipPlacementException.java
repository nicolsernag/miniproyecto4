package com.example.battleship.model.exceptions;

/**
 * Exception thrown when there is an error in placing a ship on the game board.
 */
public class ShipPlacementException extends Exception {
    public ShipPlacementException(String message) {
        super(message);
    }
}
