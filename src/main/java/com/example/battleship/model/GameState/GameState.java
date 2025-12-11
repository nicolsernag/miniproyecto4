package com.example.battleship.model.GameState;

import com.example.battleship.model.BoardPlayer;
import com.example.battleship.model.Ship;

import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable {

        private final BoardPlayer playerBoard;
        private final BoardPlayer machineBoard;
        private final List<Ship> playerShips;
        private final List<Ship> machineShips;
        private final boolean playerTurn;

        public GameState(BoardPlayer playerBoard, BoardPlayer machineBoard,
                        List<Ship> playerShips, List<Ship> machineShips,
                        boolean playerTurn) {
            this.playerBoard = playerBoard;
            this.machineBoard = machineBoard;
            this.playerShips = playerShips;
            this.machineShips = machineShips;
            this.playerTurn = playerTurn;
        }

        // Getters
        public BoardPlayer getPlayerBoard() { return playerBoard; }
        public BoardPlayer getMachineBoard() { return machineBoard; }
        public List<Ship> getPlayerShips() { return playerShips; }
        public List<Ship> getMachineShips() { return machineShips; }
        public boolean isPlayerTurn() { return playerTurn; }

}

