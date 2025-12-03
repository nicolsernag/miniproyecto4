package com.example.battleship.model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

public class Destroyer extends Ship {

    public Destroyer(double cellSize) {
        super(2);
        double originalWidth = 200;  // Ancho estimado del barco original del FXML
        double scaleFactor = (cellSize * 2) / originalWidth;

        this.setPrefSize(cellSize * 2, cellSize);

        // ===== CUERPO CENTRAL =====
        Rectangle body = new Rectangle(166, 60);
        body.setFill(Color.web("#4d6d8c"));
        body.setStroke(Color.BLACK);
        body.setArcWidth(5);
        body.setArcHeight(5);
        body.setLayoutX(200);
        body.setLayoutY(140);

        // ===== PROA =====
        Polygon front = new Polygon(
                -31.6, -10.4,
                50.0, 20.0,
                50.0, -40.0
        );
        front.setLayoutX(150);
        front.setLayoutY(180);
        front.setFill(Color.web("#4d6d8c"));
        front.setStroke(Color.BLACK);

        // ===== POPA =====
        Polygon back = new Polygon(
                -50.0, 20.0,
                32.4, -13.6,
                -50.0, -40.0
        );
        back.setLayoutX(416);
        back.setLayoutY(180);
        back.setFill(Color.web("#4d6d8c"));
        back.setStroke(Color.BLACK);

        // ===== TORRETAS / DETALLES (Rectángulos superiores) =====
        Rectangle t1 = new Rectangle(15, 41);
        t1.setFill(Color.web("#3f4f5e"));
        t1.setStroke(Color.BLACK);
        t1.setArcWidth(5);
        t1.setArcHeight(5);
        t1.setLayoutX(226);
        t1.setLayoutY(119);

        Rectangle t2 = new Rectangle(15, 41);
        t2.setFill(Color.DODGERBLUE);
        t2.setStroke(Color.BLACK);
        t2.setArcWidth(5);
        t2.setArcHeight(5);
        t2.setLayoutX(248);
        t2.setLayoutY(119);

        Rectangle t3 = new Rectangle(15, 41);
        t3.setFill(Color.DODGERBLUE);
        t3.setStroke(Color.BLACK);
        t3.setArcWidth(5);
        t3.setArcHeight(5);
        t3.setLayoutX(322);
        t3.setLayoutY(119);

        Rectangle t4 = new Rectangle(15, 41);
        t4.setFill(Color.DODGERBLUE);
        t4.setStroke(Color.BLACK);
        t4.setArcWidth(5);
        t4.setArcHeight(5);
        t4.setLayoutX(300);
        t4.setLayoutY(119);

        Rectangle t5 = new Rectangle(15, 41);
        t5.setFill(Color.DODGERBLUE);
        t5.setStroke(Color.BLACK);
        t5.setArcWidth(5);
        t5.setArcHeight(5);
        t5.setLayoutX(263);
        t5.setLayoutY(180);

        Rectangle t6 = new Rectangle(15, 41);
        t6.setFill(Color.DODGERBLUE);
        t6.setStroke(Color.BLACK);
        t6.setArcWidth(5);
        t6.setArcHeight(5);
        t6.setLayoutX(285);
        t6.setLayoutY(180);

        // ===== LUCES / PUERTOS (Círculos) =====
        Circle c1 = new Circle(7);
        c1.setFill(Color.DODGERBLUE);
        c1.setStroke(Color.BLACK);
        c1.setLayoutX(233);
        c1.setLayoutY(119);

        Circle c2 = new Circle(7);
        c2.setFill(Color.DODGERBLUE);
        c2.setStroke(Color.BLACK);
        c2.setLayoutX(256);
        c2.setLayoutY(119);

        Circle c3 = new Circle(7);
        c3.setFill(Color.DODGERBLUE);
        c3.setStroke(Color.BLACK);
        c3.setLayoutX(307);
        c3.setLayoutY(119);

        Circle c4 = new Circle(7);
        c4.setFill(Color.DODGERBLUE);
        c4.setStroke(Color.BLACK);
        c4.setLayoutX(330);
        c4.setLayoutY(119);

        Circle c5 = new Circle(7);
        c5.setFill(Color.DODGERBLUE);
        c5.setStroke(Color.BLACK);
        c5.setLayoutX(271);
        c5.setLayoutY(221);

        Circle c6 = new Circle(7);
        c6.setFill(Color.DODGERBLUE);
        c6.setStroke(Color.BLACK);
        c6.setLayoutX(293);
        c6.setLayoutY(221);

        // Agregar todo al Pane
        this.getChildren().addAll(
                body, front, back,
                t1, t2, t3, t4, t5, t6,
                c1, c2, c3, c4, c5, c6
        );

        // ===== ESCALAR EL BARCO PARA OCUPAR EXACTAMENTE 2 CELDAS =====
        Scale s = new Scale(scaleFactor, scaleFactor);
        this.getTransforms().add(s);
    }
}

