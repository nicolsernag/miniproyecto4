package com.example.battleship.model;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

/**
 * Represents a Destroyer ship in the Battleship game.
 * The Destroyer occupies 2 cells on the game board.
 */
public class Destroyer extends Ship {

    /**
     * Constructs a Destroyer ship with a specified cell size for scaling.
     *
     * @param cellSize the size of one cell on the game board
     */
    public Destroyer(double cellSize) {
        super(2); // Size of the ship = 2 cells

        // ----- standardization -----
        // I took the design from the actual FXML (approx. 80x40).
        // Here I'm putting it with clean and centered coordinates.
        this.setPrefSize(cellSize * 2, cellSize);

        // ==============================
        //   SHIP PARTS
        // ==============================

        // Main body
        Rectangle cuerpo = new Rectangle(35, 18);
        cuerpo.setLayoutX(23);
        cuerpo.setLayoutY(10);
        cuerpo.setArcWidth(5);
        cuerpo.setArcHeight(5);
        cuerpo.setFill(Color.web("#4d6d8c"));
        cuerpo.setStroke(Color.BLACK);

        // Bow (front)
        Polygon proa = new Polygon(
                29.4, -8.2,
                52.0, 2.0,
                52.0, -16.0
        );
        proa.setLayoutX(-27);
        proa.setLayoutY(26);
        proa.setFill(Color.web("#4d6d8c"));
        proa.setStroke(Color.BLACK);

        // Stern (back)
        Polygon popa = new Polygon(
                -34.4, -11,
                -12.4, -20.8,
                -34.4, -29
        );
        popa.setLayoutX(90);
        popa.setLayoutY(39);
        popa.setFill(Color.web("#4d6d8c"));
        popa.setStroke(Color.BLACK);

        // Towers
        Rectangle t1 = torre(29, 4);
        Rectangle t2 = torre(39, 4);
        Rectangle t3 = torre(34, 22);
        Rectangle t4 = torre(45, 22);
        Rectangle t5 = torre(49, 4);

        getChildren().addAll(
                cuerpo, proa, popa,
                t1, t2, t3, t4, t5
        );

        // ==============================
        //SCALE TO EXACT 2 CELLS
        // ==============================
        this.applyCss();
        this.layout();

        Bounds b = this.getBoundsInLocal();
        double realWidth = b.getWidth();

        double targetWidth = cellSize * 2;
        double scaleFactor = targetWidth / realWidth;

        this.getTransforms().add(new Scale(scaleFactor, scaleFactor));
    }

    /**
     * Creates a tower (turret) rectangle at the specified position.
     *
     * @param x the x-coordinate of the tower
     * @param y the y-coordinate of the tower
     * @return a Rectangle representing the tower
     */
    private Rectangle torre(double x, double y) {
        Rectangle r = new Rectangle(4, 13);
        r.setLayoutX(x);
        r.setLayoutY(y);
        r.setArcWidth(5);
        r.setArcHeight(5);
        r.setFill(Color.web("#3f4f5e"));
        r.setStroke(Color.BLACK);
        return r;
    }
}


