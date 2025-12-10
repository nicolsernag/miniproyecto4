package com.example.battleship.model.GameState;

import com.example.battleship.model.BoardPlayer;
import java.io.Serializable;

public class GameState implements Serializable {

    private static final long serialVersionUID = 1L;

    private int[][] playerBoard;
    private int[][] aiBoard;
    private boolean playerTurn;

    public GameState(int[][] playerBoard, int[][] aiBoard, boolean playerTurn) {
        this.playerBoard = playerBoard;
        this.aiBoard = aiBoard;
        this.playerTurn = playerTurn;
    }

    public int[][] getPlayerBoard() {
        return playerBoard;
    }

    public int[][] getAiBoard() {
        return aiBoard;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }
}
