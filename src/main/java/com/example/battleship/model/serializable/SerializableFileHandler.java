package com.example.battleship.model.serializable;

import com.example.battleship.model.GameState.GameState;

import java.io.*;

public class SerializableFileHandler {

        private static final String STATE_FILE = "savegame.ser";


        // ---------------- SERIALIZADO ----------------
        public static void saveGame(GameState gameSave, String filename) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
                oos.writeObject(gameSave);
                System.out.println("Partida guardada con éxito");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public static GameState loadGame(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (GameState) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}

