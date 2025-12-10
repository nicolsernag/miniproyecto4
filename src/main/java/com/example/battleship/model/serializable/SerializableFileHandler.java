package com.example.battleship.model.serializable;

import com.example.battleship.model.GameState.GameState;

import java.io.*;

public class SerializableFileHandler {


    private static final String GAME_FILE = "gameState.ser";
    private static final String PLAYER_FILE = "playerData.txt";

    public static void saveGame(GameState state) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(GAME_FILE))) {
            out.writeObject(state);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static GameState loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(GAME_FILE))) {
            return (GameState) in.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    public static void savePlayerData(String nickname, int hundidos) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(PLAYER_FILE))) {
            pw.println("nickname=" + nickname);
            pw.println("hundidos=" + hundidos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] loadPlayerData() {
        String nickname = "";
        int hundidos = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(PLAYER_FILE))) {
            nickname = br.readLine().split("=")[1];
            hundidos = Integer.parseInt(br.readLine().split("=")[1]);
        } catch (Exception e) { }

        return new String[] { nickname, String.valueOf(hundidos) };
    }
}

