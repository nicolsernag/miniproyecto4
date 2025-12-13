package com.example.battleship.model;

import com.example.battleship.model.exceptions.ShipPlacementException;
import javafx.scene.layout.GridPane;
import java.util.function.Consumer;

/**
 * Class responsible for enabling drag-and-drop functionality for ships on the game board.
 */
public class ShipPlacer {

    private final GridPane grid;
    private final BoardPlayer board;
    private final double cellSize;
    private final Consumer<Ship> onStartDrag;

    /**
     * Constructor for ShipPlacer.
     *
     * @param grid       The GridPane representing the game board.
     * @param board      The BoardPlayer instance managing ship placements.
     * @param cellSize   The size of each cell in the grid.
     * @param onStartDrag A callback function invoked when a ship drag starts.
     */
    public ShipPlacer(GridPane grid, BoardPlayer board, double cellSize, Consumer<Ship> onStartDrag) {
        this.grid = grid;
        this.board = board;
        this.cellSize = cellSize;
        this.onStartDrag = onStartDrag;
    }


    // ----------------------------------------------------------
    // DRAG & DROP
    // ----------------------------------------------------------

    /**
     * Enables drag-and-drop functionality for the given ship.
     *
     * @param ship The ship to enable drag-and-drop for.
     */
    public void enableDrag(Ship ship) {

        final double[] dragOffset = new double[2];

        // SAVE ORIGINAL POSITION (translateX/Y)
        ship.setOnMousePressed(e -> {

            onStartDrag.accept(ship);

            dragOffset[0] = e.getX();
            dragOffset[1] = e.getY();

            ship.setUserData(new double[]{
                    ship.getTranslateX(),
                    ship.getTranslateY()
            });

            e.consume();
        });

        // DRAG INTO SIDE PANEL
        ship.setOnMouseDragged(e -> {

            double sceneX = e.getSceneX();
            double sceneY = e.getSceneY();

            // get the current position in scene
            double nodeX = ship.localToScene(0,0).getX();
            double nodeY = ship.localToScene(0,0).getY();

            ship.setTranslateX(ship.getTranslateX() + (sceneX - nodeX - dragOffset[0]));
            ship.setTranslateY(ship.getTranslateY() + (sceneY - nodeY - dragOffset[1]));

            e.consume();
        });

        // DROP
        ship.setOnMouseReleased(e -> {

            // convert to grid coordinates
            double sceneX = e.getSceneX();
            double sceneY = e.getSceneY();

            double gridX = grid.localToScene(0, 0).getX();
            double gridY = grid.localToScene(0, 0).getY();

            int col = (int) ((sceneX - gridX) / cellSize);
            int row = (int) ((sceneY - gridY) / cellSize);

            boolean horizontal = ship.isHorizontal();

            if (board.canPlace(ship, row, col, horizontal)) {

                try {
                    board.placeShip(ship, row, col, horizontal);
                } catch (ShipPlacementException ex) {
                    throw new RuntimeException(ex);
                }

                if (!grid.getChildren().contains(ship)) {
                    grid.getChildren().add(ship);
                }

                GridPane.setColumnIndex(ship, col);
                GridPane.setRowIndex(ship, row);

                ship.setTranslateX(0);
                ship.setTranslateY(0);

                ship.setPlaced(true);// blocks rotation

                ship.setOnMouseDragged(null);
                ship.setOnMousePressed(null);
                ship.setOnMouseReleased(null);

            } else {
                // return to the original position
                double[] orig = (double[]) ship.getUserData();
                ship.setTranslateX(orig[0]);
                ship.setTranslateY(orig[1]);
            }

            onStartDrag.accept(null);
            e.consume();
        });
    }

}


