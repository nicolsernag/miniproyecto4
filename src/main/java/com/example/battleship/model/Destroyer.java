package com.example.battleship.model;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

public class Destroyer extends Ship {
    public Destroyer(double cellSize) {
        super(2);

        this.setPrefSize(cellSize * 2, cellSize);

        // ===== NORMALIZAR POSICIONES =====
        // TODAS las shapes deben usar coordenadas pequeñas
        // Centré todo manualmente

        Rectangle body = new Rectangle(166, 60);
        body.setFill(Color.web("#4d6d8c"));
        body.setStroke(Color.BLACK);
        body.setLayoutX(0);
        body.setLayoutY(20);

        Polygon front = new Polygon(
                0, 0,
                40, 20,
                40, -20
        );
        front.setLayoutX(0);
        front.setLayoutY(50);

        Polygon back = new Polygon(
                0, 20,
                -40, 0,
                0, -20
        );
        back.setLayoutX(166);
        back.setLayoutY(50);

        // torretas
        Rectangle t1 = new Rectangle(15, 30);
        t1.setFill(Color.DODGERBLUE);
        t1.setStroke(Color.BLACK);
        t1.setLayoutX(20);
        t1.setLayoutY(10);

        Rectangle t2 = new Rectangle(15, 30);
        t2.setFill(Color.DODGERBLUE);
        t2.setStroke(Color.BLACK);
        t2.setLayoutX(45);
        t2.setLayoutY(10);

        Circle c1 = new Circle(7);
        c1.setFill(Color.DODGERBLUE);
        c1.setStroke(Color.BLACK);
        c1.setLayoutX(30);
        c1.setLayoutY(60);

        this.getChildren().addAll(body, front, back, t1, t2, c1);

        // ===== OBTENER ANCHO REAL =====
        this.applyCss();
        this.layout();

        Bounds bounds = this.getBoundsInLocal();
        double realWidth = bounds.getWidth();

        // ===== ESCALAR A 2 CELDAS EXACTAS =====
        double targetWidth = cellSize * 2;
        double scaleFactor = targetWidth / realWidth;

        this.getTransforms().add(new Scale(scaleFactor, scaleFactor));
    }

}

