package com.example.battleship.model;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

/**
 * Represents a Frigate ship in the Battleship game.
 * The Frigate occupies 1 cell on the game board.
 */
public class Frigate extends Ship {

    /**
     * Constructs a Frigate ship with visual representation scaled to the given cell size.
     *
     * @param cellSize The size of a single cell on the game board.
     */
    public Frigate(double cellSize) {
        super(1); // It occupies 1 cell

        double targetSize = cellSize;
        this.setPrefSize(targetSize, targetSize);

        /* ----- ORIGINAL FIGURES FROM FXML (normalized ≈40 px) ----- */

        QuadCurve curve = new QuadCurve();
        curve.setStartX(41.0);
        curve.setStartY(14.0);
        curve.setControlX(4.0);
        curve.setControlY(-0.2);
        curve.setEndX(41.0);
        curve.setEndY(-14.0);
        curve.setFill(Color.DODGERBLUE);
        curve.setStroke(Color.BLACK);
        curve.setLayoutX(-22.0);
        curve.setLayoutY(20.0);

        Rectangle body = new Rectangle(20, 28);
        body.setFill(Color.DODGERBLUE);
        body.setStroke(Color.BLACK);
        body.setArcWidth(5.0);
        body.setArcHeight(5.0);
        body.setLayoutX(19);
        body.setLayoutY(6);

        Rectangle w1 = new Rectangle(20, 6);
        w1.setFill(Color.web("#5a5c5e"));
        w1.setStroke(Color.BLACK);
        w1.setArcWidth(5);
        w1.setArcHeight(5);
        w1.setLayoutX(11);
        w1.setLayoutY(11);

        Rectangle w2 = new Rectangle(20, 6);
        w2.setFill(Color.web("#5a5c5e"));
        w2.setStroke(Color.BLACK);
        w2.setArcWidth(5);
        w2.setArcHeight(5);
        w2.setLayoutX(11);
        w2.setLayoutY(23);

        Rectangle tower = new Rectangle(13, 22);
        tower.setFill(Color.web("#6d8ca9"));
        tower.setStroke(Color.BLACK);
        tower.setArcWidth(5);
        tower.setArcHeight(5);
        tower.setLayoutX(23);
        tower.setLayoutY(9);

        // Adds shapes to Pane
        this.getChildren().addAll(curve, body, w1, w2, tower);

        /* ----- AUTO SCALING ----- */

        this.applyCss();
        this.layout();
        Bounds b = this.getBoundsInLocal();

        double scale = targetSize / Math.max(b.getWidth(), b.getHeight());
        this.getTransforms().add(new Scale(scale, scale, 0, 0));
    }
}



