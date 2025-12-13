package com.example.battleship.model;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

/**
 * Class representing a Submarine ship in the Battleship game.
 */
public class Submarine extends Ship {

    /**
     * Constructs a Submarine with the specified cell size for scaling.
     *
     * @param cellSize the size of a single cell in the game grid
     */
    public Submarine(double cellSize) {
        super(3);

        double targetWidth = cellSize * 3;
        double targetHeight = cellSize * 1.1;
        this.setPrefSize(targetWidth, targetHeight);

        Rectangle body = new Rectangle(150, 30);
        body.setFill(Color.web("#2D572C"));
        body.setStroke(Color.BLACK);
        body.setArcWidth(20);
        body.setArcHeight(20);
        body.setLayoutX(0);
        body.setLayoutY(10);

        Rectangle tower = new Rectangle(40, 18);
        tower.setFill(Color.web("#3C7A3B"));
        tower.setStroke(Color.BLACK);
        tower.setLayoutX(55);
        tower.setLayoutY(-2);

        Circle window = new Circle(6);
        window.setFill(Color.WHITE);
        window.setStroke(Color.BLACK);
        window.setLayoutX(75);
        window.setLayoutY(22);

        this.getChildren().addAll(body, tower, window);

        /* AUTO SCALING */

        this.applyCss();
        this.layout();
        Bounds b = this.getBoundsInLocal();

        double scaleX = targetWidth / b.getWidth();
        double scaleY = targetHeight / b.getHeight();

        this.getTransforms().add(new Scale(scaleX, scaleY, 0, 0));
    }
}
