package com.example.battleship.model.serializable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShipData implements Serializable {
    private int size;
    private boolean horizontal;
    private List<CellData> occupiedCells; // posiciones lógicas
    private Map<Integer, Boolean> segmentHit;

    public ShipData(int size, boolean horizontal) {
        this.size = size;
        this.horizontal = horizontal;
        this.occupiedCells = new ArrayList<>();
        this.segmentHit = new HashMap<>();
        for (int i = 0; i < size; i++) segmentHit.put(i, false);
    }

    // GETTERS / SETTERS
    public int getSize() { return size; }
    public boolean isHorizontal() { return horizontal; }
    public List<CellData> getOccupiedCells() { return occupiedCells; }
    public Map<Integer, Boolean> getSegmentHit() { return segmentHit; }

    public void addCell(CellData cell) { occupiedCells.add(cell); }
    public void setSegmentHit(int index, boolean hit) { segmentHit.put(index, hit); }
}

