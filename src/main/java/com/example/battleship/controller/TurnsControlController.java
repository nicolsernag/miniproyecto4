package com.example.battleship.controller;

import com.example.battleship.model.BoardPlayer;
import com.example.battleship.controller.GameController;
import com.example.battleship.model.ShotResult;
import com.example.battleship.model.threads.MachineThread;
import com.example.battleship.model.threads.TimerThread;
import javafx.scene.layout.Pane;

public class TurnsControlController {
    private final GameController controller;
    private final BoardPlayer playerBoard;
    private final BoardPlayer enemyBoard;
    private final MachineThread machineThread;
    private  TimerThread timerThread;

    public TurnsControlController(GameController controller, BoardPlayer playerBoard, BoardPlayer enemyBoard, MachineThread machineThread) {
        this.controller = controller;
        this.playerBoard = playerBoard;
        this.enemyBoard = enemyBoard;
        this.machineThread = machineThread;


        setupMachineListener();
    }

    public void processPlayerShot(int row, int col, Pane clickedCell) {
        ShotResult result = enemyBoard.shoot(row, col);
        if (result == null) return;
        controller.getRenderer().paintShot(controller.getEnemyGrid(), row, col, result);



        if (result == ShotResult.SUNK && enemyBoard.allShipsSunk()) {
            controller.handlePlayerWin();
            return;
            }




        if (result == ShotResult.SUNK || result == ShotResult.HIT){
            return;
        }
        if (result == ShotResult.WATER) {
            controller.stopPlayerTimer();
            machineThread.playTurn();
        }
    }


    private void setupMachineListener(){
        machineThread.setListener((row, col, result) -> {
            controller.getRenderer().paintShot(controller.getPlayerGrid(), row, col, result);
            if (playerBoard.allShipsSunk()) {
                controller.handlePlayerLoss(); //new method
                return;
            }

            if (result == ShotResult.SUNK || result == ShotResult.HIT) {
                machineThread.playTurn();
                return;
            }
            if (result == ShotResult.WATER) {
                controller.handlePlayerTurn();
        }

    });
}
}
