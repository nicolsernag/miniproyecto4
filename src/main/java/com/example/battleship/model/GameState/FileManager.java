package com.example.battleship.model.GameState;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileManager {

    private static final String PLAYER_FILE = "player.txt";

    // Guardar nickname y contadores de barcos hundidos
    public static void savePlayerData(String nickname, int sunkHuman, int sunkMachine) {
        if (nickname == null || nickname.isBlank()) nickname = "Jugador";

        try (PrintWriter pw = new PrintWriter(new FileWriter(PLAYER_FILE))) {
            pw.println("nickname=" + nickname);
            pw.println("sunkHuman=" + sunkHuman);
            pw.println("sunkMachine=" + sunkMachine);
            System.out.println("[Persistence] Datos jugador guardados: " + PLAYER_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Cargar datos del jugador
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

    // Obtener nickname directamente
    public static String loadPlayerNickname() {
        Map<String, String> data = loadPlayerData();
        return data.getOrDefault("nickname", "Jugador");
    }

    // Obtener contadores
    public static int loadSunkHuman() {
        Map<String, String> data = loadPlayerData();
        return Integer.parseInt(data.getOrDefault("sunkHuman", "0"));
    }

    public static int loadSunkMachine() {
        Map<String, String> data = loadPlayerData();
        return Integer.parseInt(data.getOrDefault("sunkMachine", "0"));
    }
}


