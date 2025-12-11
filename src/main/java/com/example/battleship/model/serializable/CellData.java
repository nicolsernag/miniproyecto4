package com.example.battleship.model.serializable;

import java.io.Serializable;

public class CellData implements Serializable {
    private int row;
    private int col;

    public CellData(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
}
