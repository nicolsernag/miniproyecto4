package com.example.battleship.model;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class WaterShape extends Group {

    public WaterShape(double size) {

        double scale = size / 40.0;

        Line l1 = new Line(-94.27, 0.05, -71.34, 21.45);
        l1.setStroke(Color.web("#dc2828"));
        l1.setStrokeWidth(7);
        l1.setLayoutX(104 * scale);
        l1.setLayoutY(9 * scale);
        l1.setScaleX(scale); l1.setScaleY(scale);

        Line l2 = new Line(-29.8, 28.99, -7.87, 6.05);
        l2.setStroke(Color.web("#dc3333"));
        l2.setStrokeWidth(7);
        l2.setLayoutX(39 * scale);
        l2.setLayoutY(2 * scale);
        l2.setScaleX(scale); l2.setScaleY(scale);

        getChildren().addAll(l1, l2);
    }
}

