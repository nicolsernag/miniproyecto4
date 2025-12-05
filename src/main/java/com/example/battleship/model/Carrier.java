package com.example.battleship.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class Carrier extends Ship {

    public Carrier(double cellSize) {
        super(4); // ocupa 4 celdas

        /* ==============================
           FIGURAS BASE (SIN ESCALAR)
           ============================== */

        Rectangle cuerpo = new Rectangle(120, 40);
        cuerpo.setFill(Color.web("#90a9c1"));
        cuerpo.setStroke(Color.web("#00000033"));
        cuerpo.setArcWidth(5);
        cuerpo.setArcHeight(5);
        cuerpo.setLayoutX(0);
        cuerpo.setLayoutY(10);

        Polygon proa = new Polygon(
                0.0, 20.0,
                -25.0, 0.0,
                -25.0, 40.0
        );
        proa.setFill(Color.web("#8aa5bf"));
        proa.setStroke(Color.web("#00000022"));
        proa.setLayoutX(0);
        proa.setLayoutY(10);

        Rectangle cabina = new Rectangle(80, 15);
        cabina.setFill(Color.web("#2f475e"));
        cabina.setStroke(Color.BLACK);
        cabina.setArcWidth(4);
        cabina.setArcHeight(4);
        cabina.setLayoutX(20);
        cabina.setLayoutY(12);

        Line l1 = new Line(15, 0, 35, 0);
        l1.setStroke(Color.WHITE);
        l1.setLayoutX(20);
        l1.setLayoutY(45);

        Line l2 = new Line(15, 0, 35, 0);
        l2.setStroke(Color.WHITE);
        l2.setLayoutX(50);
        l2.setLayoutY(45);

        Line l3 = new Line(15, 0, 35, 0);
        l3.setStroke(Color.WHITE);
        l3.setLayoutX(80);
        l3.setLayoutY(45);

        this.getChildren().addAll(cuerpo, proa, cabina, l1, l2, l3);

        /* ==============================
           DIMENSIONES LOGICAS DEL BARCO
           ============================== */
        updateVisualSize(cellSize);
    }
}


