package com.example.battleship.model.serializable;

import com.example.battleship.model.GameState.GameState;

import java.io.*;

public class SerializableFileHandler {

        private static final String STATE_FILE = "savegame.ser";
        private static final String PLAYER_FILE = "player.txt";

        // ---------------- SERIALIZADO ----------------
        public static void saveGameState(GameState state) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(STATE_FILE))) {
                out.writeObject(state);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static GameState loadGameState() {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(STATE_FILE))) {
                return (GameState) in.readObject();
            } catch (Exception e) {
                return null; // no hay archivo
            }
        }
}

