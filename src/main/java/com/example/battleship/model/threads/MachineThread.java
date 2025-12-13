package com.example.battleship.model.threads;
import com.example.battleship.model.BoardPlayer;

import com.example.battleship.model.ShotResult;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.util.Random;

/**
 * Thread that handles the machine's turn in the Battleship game.
 */
public class MachineThread {

    private final BoardPlayer targetBoard;   // Player's board
    private final Random random = new Random();

    /**
     * Listener interface for machine shot events.
     */
    public interface MachineShotListener {
        void onMachineShot(int row, int col, ShotResult result);
    }

    private MachineShotListener listener;

    /**
     * Constructor for MachineThread.
     *
     * @param targetBoard The player's board to target.
     */
    public MachineThread(BoardPlayer targetBoard) {
        this.targetBoard = targetBoard;
    }

    /**
     * Sets the listener for machine shot events.
     *
     * @param listener The listener to set.
     */
    public void setListener(MachineShotListener listener) {
        this.listener = listener;
    }

    /**
     * Starts the machine's turn to shoot at the player's board.
     */
    public void playTurn() {

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {

                // AI "thinking" time
                Thread.sleep(1000);

                final int[] pos = new int[2];
                ShotResult result;

                do {
                    pos[0] = random.nextInt(10);
                    pos[1] = random.nextInt(10);
                } while (targetBoard.getCell(pos[0], pos[1]).isShot());


                result = targetBoard.shoot(pos[0], pos[1]);

                // Notifies the controller
                if (listener != null) {
                    Platform.runLater(() -> {
                        listener.onMachineShot(pos[0], pos[1], result);
                    });
                }

                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}
