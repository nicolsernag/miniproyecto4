package com.example.battleship.model.threads;


import com.example.battleship.controller.GameController;
import javafx.application.Platform;

public class TimerThread extends Thread {
    private final GameController controller;
    private final int timeLimitSeconds = 15; //time limit
    private volatile boolean isRunning = true; // Flag to control the thread execution

    /**
     * Constructs a TimerThread with the specified controller.
     * @param controller
     */
    public TimerThread(GameController controller) {
        this.controller = controller;
    }

    /**
     * Stops the timer thread gracefully.
     */
    public void stopTimer() {
        this.isRunning = false;
    }

    /**
     * Runs the timer thread, updating the UI every second and handling time expiration.
     */
    @Override
    public void run() {
        for (int i = timeLimitSeconds; i >= 0 && isRunning; i--) {
            final int secondsLeft = i;

            Platform.runLater(() -> {
                controller.updateTimerLabel(secondsLeft);
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        if (isRunning) {
            Platform.runLater(() -> {
                controller.handleTimeExpired();
            });
        }
    }


    }

