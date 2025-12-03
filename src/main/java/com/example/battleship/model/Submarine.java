package com.example.battleship.model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

public class Submarine extends Ship {

    public Submarine(double cellSize) {
        super(3);
        // El FXML original mide aprox 150 px de ancho
        double originalWidth = 150;

        // Escalar para que ocupe 3 casillas del tablero
        double targetWidth = cellSize * 3;
        double scaleFactor = targetWidth / originalWidth;

        // Tamaño del contenedor
        this.setPrefSize(targetWidth, cellSize * 1.1);

        // ===== CUERPO PRINCIPAL =====
        Rectangle body = new Rectangle(150, 35);
        body.setFill(Color.web("#2D572C"));
        body.setStroke(Color.BLACK);
        body.setStrokeWidth(3);
        body.setArcWidth(20);
        body.setArcHeight(20);
        body.setLayoutX(0);
        body.setLayoutY(0);

        // ===== TORRE SUPERIOR =====
        Rectangle tower = new Rectangle(40, 20);
        tower.setFill(Color.web("#3C7A3B"));
        tower.setStroke(Color.BLACK);
        tower.setStrokeWidth(2);
        tower.setLayoutX(60);
        tower.setLayoutY(-5);

        // ===== VENTANA =====
        Circle window = new Circle(6);
        window.setFill(Color.WHITE);
        window.setStroke(Color.BLACK);
        window.setLayoutX(80);
        window.setLayoutY(12);

        // Agregamos las figuras
        this.getChildren().addAll(body, tower, window);

        // ===== APLICAR ESCALADO A 3 CASES =====
        Scale scale = new Scale(scaleFactor, scaleFactor);
        this.getTransforms().add(scale);
    }
}

