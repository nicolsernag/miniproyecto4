package com.example.battleship.model;

import com.example.battleship.model.exceptions.ShipPlacementException;
import com.example.battleship.model.serializable.CellData;
import com.example.battleship.model.serializable.ShipData;

import java.io.Serializable;
import java.util.*;

public class BoardPlayer implements Serializable {

    private final int rows = 10;
    private final int cols = 10;

    // Estructura principal del tablero
    private final ArrayList<ArrayList<Cell>> grid = new ArrayList<>();

    // Barcos colocados en orden
    private final Deque<Ship> placedShips = new LinkedList<>();

    // Mapa de celdas ocupadas → barco
    private final Map<Cell, Ship> occupiedMap = new HashMap<>();

    public BoardPlayer() {
        for (int r = 0; r < rows; r++) {
            ArrayList<Cell> rowList = new ArrayList<>();
            for (int c = 0; c < cols; c++) {
                rowList.add(new Cell(r, c));
            }
            grid.add(rowList);
        }
    }

    public void addShipFromData(ShipData sd) {
        Ship ship;
        switch (sd.getSize()) {
            case 1 -> ship = new Frigate(40);      // tamaño de celda fijo
            case 2 -> ship = new Destroyer(40);
            case 3 -> ship = new Carrier(40);      // si tienes Cruiser
            case 4 -> ship = new Submarine(40);   // si tienes Battleship
            default -> throw new IllegalArgumentException("Barco de tamaño desconocido");
        }

        ship.setHorizontal(sd.isHorizontal());
        for (CellData cd : sd.getOccupiedCells()) {
            ship.addCell(new Cell(cd.getRow(), cd.getCol()));
        }

        ship.setPlaced(true);   // marcar como colocado
        placedShips.add(ship);  // agregar al deque del BoardPlayer
    }



    public Cell getCell(int row, int col) {
        return grid.get(row).get(col);
    }

    public boolean isInside(int row, int col) {
        return row >= 0 && col >= 0 && row < rows && col < cols;
    }

    // Verifica si el barco cabe y no hay conflicto
    public boolean canPlace(Ship ship, int row, int col, boolean horizontal) {

        int size = ship.getSize();

        // 1) Validación de límites
        if (row < 0 || col < 0) return false;
        if (horizontal) {
            if (col + size > 10) return false; // se sale por derecha
        } else {
            if (row + size > 10) return false; // se sale por abajo
        }

        // 2) Validar colisiones revisando el mapa ocupado
        for (int i = 0; i < size; i++) {
            int r = horizontal ? row : row + i;
            int c = horizontal ? col + i : col;

            Cell cell = getCell(r, c);

            // Si ya está ocupada → no se puede
            if (occupiedMap.containsKey(cell)) {
                return false;
            }
        }

        return true;
    }



    public void placeShip(Ship ship, int row, int col, boolean horizontal)
            throws ShipPlacementException {

        int size = ship.getSize();

        // 1. Validar límites
        for (int i = 0; i < size; i++) {
            int r = horizontal ? row : row + i;
            int c = horizontal ? col + i : col;

            if (!isInside(r, c)) {
                throw new ShipPlacementException("El barco no cabe en el tablero.");
            }
        }

        // 2. Validar colisiones
        if (!canPlace(ship, row, col, horizontal)) {
            throw new ShipPlacementException("El barco choca con otro barco.");
        }

        // 3. Si todo está bien, colocarlo
        for (int i = 0; i < size; i++) {
            int r = horizontal ? row : row + i;
            int c = horizontal ? col + i : col;

            Cell cell = getCell(r, c);
            cell.setOccupied(true);

            ship.addCell(cell);
            occupiedMap.put(cell, ship);
        }

        placedShips.add(ship);
    }


    public ShotResult shoot(int row, int col){
        Cell cell = getCell(row, col);
        if(cell.isShot()){
            return null;
        }
        cell.markShot();
        if(!cell.isOccupied()){
            return ShotResult.WATER;
        }

        Ship ship = occupiedMap.get(cell);
        int index = ship.getOccupiedCells().indexOf(cell);
        ship.registerHit(index);

        if(ship.isSunk()) {
            return ShotResult.SUNK;
        }
        return ShotResult.HIT;

    }

    public boolean allShipsSunk() {
        for (Ship s : placedShips) {
            if (!s.isSunk()) {
                return false;
            }
        }
        return true;
    }


    public void relocateShipAfterRotation(Ship ship, int row, int col, boolean horizontal) {

        // 1. Eliminar ocupación anterior
        for (Cell c : ship.getOccupiedCells()) {
            occupiedMap.remove(c);
            c.setOccupied(false);
        }

        ship.clearCells();

        // 2. Reasignar celdas nuevas según orientación
        int size = ship.getSize();
        for (int i = 0; i < size; i++) {
            int r = horizontal ? row : row + i;
            int c = horizontal ? col + i : col;

            Cell cell = getCell(r, c);
            cell.setOccupied(true);
            occupiedMap.put(cell, ship);
            ship.addCell(cell);
        }

        // 3. Actualizar orientación interna
        ship.setHorizontal(horizontal);
    }

    public Deque<Ship> getPlacedShips() {
        return placedShips;
    }

    public void placeShipsAutomatically(double cellSize) throws ShipPlacementException {

        // 1. Crear los barcos que la IA debe posicionar
        ArrayList<Ship> shipsToPlace = new ArrayList<>();
        shipsToPlace.add(new Carrier(cellSize));  // 1 carrier
        for (int i = 0; i < 2; i++) shipsToPlace.add(new Submarine(cellSize));  // 2 submarinos
        for (int i = 0; i < 3; i++) shipsToPlace.add(new Destroyer(cellSize));  // 3 destructores
        for (int i = 0; i < 4; i++) shipsToPlace.add(new Frigate(cellSize));    // 4 fragatas


        Random random = new Random();

        // 2. Iterar y colocar cada barco
        for (Ship ship : shipsToPlace) {
            boolean placed = false;
            while (!placed) {
                // Generar coordenadas aleatorias (0-9)
                int row = random.nextInt(10);
                int col = random.nextInt(10);

                // Generar orientación aleatoria (true = horizontal, false = vertical)
                boolean horizontal = random.nextBoolean();


                if (canPlace(ship, row, col, horizontal)) {


                    placeShip(ship, row, col, horizontal);
                    placed = true;
                }
            }
        }
    }

    // Devuelve una matriz 10x10 donde:
// 0 = agua sin disparar
// 1 = agua disparada
// 2 = barco sin disparar
// 3 = barco disparado
    public int[][] toMatrix() {
        int[][] m = new int[10][10];

        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                Cell cell = getCell(r, c);

                if (!cell.isOccupied() && !cell.isShot()) m[r][c] = 0;
                if (!cell.isOccupied() && cell.isShot())  m[r][c] = 1;

                if (cell.isOccupied() && !cell.isShot())  m[r][c] = 2;
                if (cell.isOccupied() && cell.isShot())   m[r][c] = 3;
            }
        }
        return m;
    }



    public Cell[][] getCells() {
        Cell[][] matrix = new Cell[10][10];
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                matrix[r][c] = grid.get(r).get(c);
            }
        }
        return matrix;
    }

    public ShotResult getShotResultAt(int row, int col) {
        Cell cell = getCell(row, col);

        // Si no hay tiro, no hay resultado
        if (!cell.isShot()) return null;

        // Si no está ocupado, es agua
        if (!cell.isOccupied()) return ShotResult.WATER;

        Ship ship = occupiedMap.get(cell);

        // Seguridad extra: si por alguna razón el mapa no contiene el barco
        if (ship == null) return ShotResult.WATER;

        return ship.isSunk() ? ShotResult.SUNK : ShotResult.HIT;
    }



    public int countSunkShips() {
        int count = 0;
        for (Ship ship : placedShips) {
            if (ship.isSunk()) {
                count++;
            }
        }
        return count;
    }

    public void rebuildShipsFromBoard() {

        placedShips.clear();
        occupiedMap.clear();

        boolean[][] visited = new boolean[10][10];

        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {

                Cell start = getCell(r, c);

                if (!start.isOccupied() || visited[r][c]) continue;

                // Detectar barco completo
                List<Cell> cells = new ArrayList<>();
                cells.add(start);
                visited[r][c] = true;

                // ¿Horizontal?
                boolean horizontal = false;
                if (c + 1 < 10 && getCell(r, c + 1).isOccupied())
                    horizontal = true;

                // Expandir
                int rr = r, cc = c;
                if (horizontal) {
                    while (cc + 1 < 10 && getCell(r, cc + 1).isOccupied()) {
                        cc++;
                        visited[r][cc] = true;
                        cells.add(getCell(r, cc));
                    }
                } else {
                    while (rr + 1 < 10 && getCell(rr + 1, c).isOccupied()) {
                        rr++;
                        visited[rr][c] = true;
                        cells.add(getCell(rr, c));
                    }
                }

                int size = cells.size();
                Ship ship;

                // Crear barco del tipo correcto
                switch (size) {
                    case 5 -> ship = new Carrier(30);     // usa tu cellSize
                    case 3 -> ship = new Destroyer(30);
                    case 2 -> ship = new Frigate(30);
                    case 4 -> ship = new Submarine(30);
                    default -> throw new RuntimeException("Barco de tamaño inválido: " + size);
                }

                ship.setHorizontal(horizontal);

                for (Cell cell : cells) {
                    ship.addCell(cell);
                    occupiedMap.put(cell, ship);
                }

                placedShips.add(ship);

                // Restaurar disparos
                for (Cell cell : cells) {
                    if (cell.isShot()) {
                        int idx = ship.getOccupiedCells().indexOf(cell);
                        ship.registerHit(idx);
                    }
                }
            }
        }
    }
}

