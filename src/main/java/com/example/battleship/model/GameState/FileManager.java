package com.example.battleship.model.GameState;

import java.io.*;
import java.util.List;

public class FileManager {

    public static void saveBoard(int[][] board, String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {

            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board[row].length; col++) {
                    pw.print(board[row][col] + " ");
                }
                pw.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[][] loadBoard(String filename) {
        int[][] board = new int[10][10];
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            for (int row = 0; row < 10; row++) {
                String[] parts = br.readLine().split(" ");
                for (int col = 0; col < 10; col++) {
                    board[row][col] = Integer.parseInt(parts[col]);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return board;
    }
}

