package com.example.battleship.model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

public class Frigate extends Ship {

    public Frigate(double cellSize) {
        super(1);
        // El barco original era aprox 120 px de ancho.
        double originalWidth = 110;
        double scaleFactor = cellSize / originalWidth;

        // Ajustar tamaño del contenedor
        this.setPrefSize(cellSize, cellSize);

        // ===== Proa del barco =====
        QuadCurve curve = new QuadCurve();
        curve.setStartX(50);
        curve.setStartY(24);
        curve.setControlX(-52.8);
        curve.setControlY(1);
        curve.setEndX(50.4);
        curve.setEndY(-24.6);
        curve.setFill(Color.DODGERBLUE);
        curve.setStroke(Color.BLACK);
        curve.setLayoutX(192 - 192);  // normalizamos a 0
        curve.setLayoutY(203 - 178);

        // ===== Cuerpo =====
        Rectangle body = new Rectangle(52, 49);
        body.setFill(Color.DODGERBLUE);
        body.setStroke(Color.BLACK);
        body.setArcWidth(5);
        body.setArcHeight(5);
        body.setLayoutX(242 - 192);
        body.setLayoutY(178 - 178);

        // ===== Ventanas rectangulares =====
        Rectangle window1 = new Rectangle(44, 15);
        window1.setFill(Color.web("#5a5c5e"));
        window1.setStroke(Color.BLACK);
        window1.setArcWidth(5);
        window1.setArcHeight(5);
        window1.setLayoutX(220 - 192);
        window1.setLayoutY(185 - 178);

        Rectangle window2 = new Rectangle(44, 15);
        window2.setFill(Color.web("#5a5c5e"));
        window2.setStroke(Color.BLACK);
        window2.setArcWidth(5);
        window2.setArcHeight(5);
        window2.setLayoutX(220 - 192);
        window2.setLayoutY(203 - 178);

        // ===== Ventanas circulares =====
        Circle p1 = new Circle(7);
        p1.setFill(Color.web("#8da3b7"));
        p1.setStroke(Color.BLACK);
        p1.setLayoutX(220 - 192);
        p1.setLayoutY(193 - 178);

        Circle p2 = new Circle(7);
        p2.setFill(Color.web("#8da3b7"));
        p2.setStroke(Color.BLACK);
        p2.setLayoutX(220 - 192);
        p2.setLayoutY(211 - 178);

        // ===== Torre =====
        Rectangle tower = new Rectangle(26, 33);
        tower.setFill(Color.web("#6d8ca9"));
        tower.setStroke(Color.BLACK);
        tower.setArcWidth(5);
        tower.setArcHeight(5);
        tower.setLayoutX(264 - 192);
        tower.setLayoutY(186 - 178);

        Rectangle towerWin = new Rectangle(14, 15);
        towerWin.setFill(Color.web("#bbcbd9"));
        towerWin.setStroke(Color.BLACK);
        towerWin.setArcWidth(5);
        towerWin.setArcHeight(5);
        towerWin.setLayoutX(270 - 192);
        towerWin.setLayoutY(196 - 178);

        // Agregamos Shapes
        this.getChildren().addAll(curve, body, window1, window2, p1, p2, tower, towerWin);

        // === Escalar TODO el barco al tamaño de 1 celda ===
        Scale scale = new Scale(scaleFactor, scaleFactor);
        this.getTransforms().add(scale);
    }
}

