package com.example.battleship.model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

public class Carrier extends Ship {

    public Carrier(double cellSize) {
        super(4);
        double originalWidth = 126;               // ancho del FXML original
        double targetWidth = cellSize * 4;        // ancho deseado: 4 casillas
        double scaleFactor = targetWidth / originalWidth;

        this.setPrefSize(targetWidth, cellSize * 1.2); // altura ajustable

        // =============== PROA ===============
        Polygon proa = new Polygon(
                -45.0, 2.0,
                -45.0, -44.0,
                -86.8, -23.0
        );
        proa.setFill(Color.web("#8aa5bf"));
        proa.setStroke(Color.web("#00000001"));
        proa.setLayoutX(282 - 237);
        proa.setLayoutY(195 - 151);

        // =============== CUERPO ===============
        Rectangle cuerpo = new Rectangle(126, 46);
        cuerpo.setArcWidth(5);
        cuerpo.setArcHeight(5);
        cuerpo.setFill(Color.web("#90a9c1"));
        cuerpo.setStroke(Color.web("#00000003"));
        cuerpo.setLayoutX(0);
        cuerpo.setLayoutY(0);

        // =============== CABINA ===============
        Rectangle cabina = new Rectangle(108, 20);
        cabina.setArcWidth(5);
        cabina.setArcHeight(5);
        cabina.setFill(Color.web("#2f475e"));
        cabina.setStroke(Color.BLACK);
        cabina.setLayoutX(18);
        cabina.setLayoutY(13);

        // =============== LÍNEAS ===============
        Line l1 = new Line(22, -81.8, 45.2, -81.8);
        l1.setStroke(Color.web("#f7efef"));
        l1.setLayoutX(68);
        l1.setLayoutY(106);

        Line l2 = new Line(22, -81.8, 45.2, -81.8);
        l2.setStroke(Color.web("#f7efef"));
        l2.setLayoutX(35);
        l2.setLayoutY(106);

        Line l3 = new Line(22, -81.8, 45.2, -81.8);
        l3.setStroke(Color.web("#f7efef"));
        l3.setLayoutX(4);
        l3.setLayoutY(106);

        this.getChildren().addAll(cuerpo, proa, cabina, l1, l2, l3);

        // =============== ESCALAR A 4 CASILLAS ===============
        Scale scale = new Scale(scaleFactor, scaleFactor);
        this.getTransforms().add(scale);
    }
}

