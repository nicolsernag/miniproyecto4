package com.example.battleship.model.GameState;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileManager {
    private static final String PLAYER_FILE = "player.txt";
    public static void savePlayerData(String nickname, int sunkHuman, int sunkMachine) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(PLAYER_FILE))) {
            pw.println("nickname=" + (nickname == null ? "" : nickname));
            pw.println("sunkHuman=" + sunkHuman);
            pw.println("sunkMachine=" + sunkMachine);
            System.out.println("[Persistence] Datos jugador guardados: " + PLAYER_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> loadPlayerData() {
        Map<String, String> out = new HashMap<>();
        File f = new File(PLAYER_FILE);
        if (!f.exists()) return out;
        try (BufferedReader br = new BufferedReader(new FileReader(PLAYER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("=", 2);
                if (p.length == 2) out.put(p[0], p[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }
}

