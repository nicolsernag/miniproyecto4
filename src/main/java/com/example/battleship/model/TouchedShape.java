package com.example.battleship.model;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class TouchedShape extends Group {

    public TouchedShape(double size) {

        double scale = size / 40.0;

        Rectangle r = new Rectangle(4, 20);
        r.setArcHeight(5);
        r.setArcWidth(5);
        r.setFill(Color.web("#1c4b76"));
        r.setRotate(52.8);
        r.setLayoutX(21 * scale);
        r.setLayoutY(5 * scale);
        r.setScaleX(scale); r.setScaleY(scale);

        Circle c = new Circle(12);
        c.setFill(Color.web("#11395e"));
        c.setLayoutX(14 * scale);
        c.setLayoutY(23 * scale);
        c.setScaleX(scale); c.setScaleY(scale);

        Polygon p1 = new Polygon(
                54.0, 22.36,
                64.8, 33.39,
                68.4, 19.19
        );
        p1.setFill(Color.web("#ff1f1f"));
        p1.setLayoutX(-31 * scale);
        p1.setLayoutY(-15 * scale);
        p1.setScaleX(scale); p1.setScaleY(scale);

        Polygon p2 = new Polygon(
                32.39999, 24.99999,
                47.0, 22.60000,
                36.39999, 11.60000
        );
        p2.setFill(Color.web("#ff231f"));
        p2.setLayoutX(-7 * scale);
        p2.setLayoutY(-10 * scale);
        p2.setScaleX(scale); p2.setScaleY(scale);

        getChildren().addAll(r, c, p1, p2);
    }
}

