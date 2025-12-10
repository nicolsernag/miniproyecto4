package com.example.battleship.model.GameState;

import java.io.Serializable;

public class GameState implements Serializable {

    private static final long serialVersionUID = 1L;

    private int[][] playerMatrix;
    private int[][] enemyMatrix;

    private boolean playerTurn;
    private String nickname;
    private int sunkHuman;
    private int sunkMachine;

    public GameState(int[][] playerMatrix, int[][] enemyMatrix,
                     boolean playerTurn,
                     String nickname, int sunkHuman, int sunkMachine) {
        this.playerMatrix = playerMatrix;
        this.enemyMatrix = enemyMatrix;
        this.playerTurn = playerTurn;
        this.nickname = nickname;
        this.sunkHuman = sunkHuman;
        this.sunkMachine = sunkMachine;
    }

    public int[][] getPlayerMatrix() { return playerMatrix; }
    public int[][] getEnemyMatrix() { return enemyMatrix; }
    public boolean isPlayerTurn() { return playerTurn; }

    public String getNickname() { return nickname; }
    public int getSunkHuman() { return sunkHuman; }
    public int getSunkMachine() { return sunkMachine; }
}

