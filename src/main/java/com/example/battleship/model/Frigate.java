package com.example.battleship.model;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

public class Frigate extends Ship {

    public Frigate(double cellSize) {
        super(1); // ocupa 1 celda

        double targetSize = cellSize;
        this.setPrefSize(targetSize, targetSize);

        /* ----- FIGURAS ORIGINALES DEL FXML (normalizadas ≈40 px) ----- */

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

        // Añadir shapes al Pane
        this.getChildren().addAll(curve, body, w1, w2, tower);

        /* ----- ESCALADO AUTOMÁTICO ----- */

        this.applyCss();
        this.layout();
        Bounds b = this.getBoundsInLocal();

        double scale = targetSize / Math.max(b.getWidth(), b.getHeight());
        this.getTransforms().add(new Scale(scale, scale, 0, 0));
    }
}



