package com.example.battleship.model.GameState;

import com.example.battleship.model.serializable.ShipData;
import java.io.Serializable;
import java.util.Deque;

public class GameState implements Serializable {
    private Deque<ShipData> playerShips;
    private Deque<ShipData> machineShips;
    private boolean playerTurn;
    private String playerNickname;

    public GameState(Deque<ShipData> playerShips,
                     Deque<ShipData> machineShips,
                     boolean playerTurn,
                     String playerNickname) {
        this.playerShips = playerShips;
        this.machineShips = machineShips;
        this.playerTurn = playerTurn;
        this.playerNickname = playerNickname;
    }

    public Deque<ShipData> getPlayerShips() { return playerShips; }
    public Deque<ShipData> getMachineShips() { return machineShips; }
    public boolean isPlayerTurn() { return playerTurn; }
    public String getPlayerNickname() { return playerNickname; }
}


