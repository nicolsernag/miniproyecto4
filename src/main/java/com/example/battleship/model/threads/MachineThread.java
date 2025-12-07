package com.example.battleship.model.threads;
import com.example.battleship.model.BoardPlayer;

import com.example.battleship.model.ShotResult;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.util.Random;

public class MachineThread {

    private final BoardPlayer targetBoard;   // Tablero humano
    private final Random random = new Random();

    /**
     * Listener que el GameController implementará
     */
    public interface MachineShotListener {
        void onMachineShot(int row, int col, ShotResult result);
    }

    private MachineShotListener listener;

    public MachineThread(BoardPlayer targetBoard) {
        this.targetBoard = targetBoard;
    }

    public void setListener(MachineShotListener listener) {
        this.listener = listener;
    }

    public void playTurn() {

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {

                // Simula que la IA “piensa”
                Thread.sleep(600);

                final int[] pos = new int[2];
                ShotResult result;

                do {
                    pos[0] = random.nextInt(10);
                    pos[1] = random.nextInt(10);
                } while (targetBoard.getCell(pos[0], pos[1]).isShot());


                result = targetBoard.shoot(pos[0], pos[1]);

                // Notificar al controlador → el que actualiza UI
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
