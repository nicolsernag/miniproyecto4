package com.example.battleship.model;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.QuadCurve;

public class SunkenShape extends Group {

    public SunkenShape(double size) {

        double scale = size / 40.0;

        Polygon p1 = new Polygon(
                15.19999, 38.99999,
                34.19999, 38.59999,
                38.39999, 22.39999
        );
        p1.setFill(Color.web("#ff371f"));
        p1.setLayoutX(-4 * scale);
        p1.setLayoutY(-12 * scale);
        p1.setScaleX(scale); p1.setScaleY(scale);

        QuadCurve q1 = new QuadCurve(
                13.19999, -72.4,
                -9.60000, -72.4,
                2.39999, -54.2
        );
        q1.setFill(Color.web("#ff371f"));
        q1.setLayoutX(17 * scale);
        q1.setLayoutY(99 * scale);
        q1.setScaleX(scale); q1.setScaleY(scale);

        Polygon p2 = new Polygon(
                -1.39999, 14.59999,
                1.39999, 28.59998,
                22.60000, 28.59998
        );
        p2.setFill(Color.web("#ff371f"));
        p2.setLayoutX(6 * scale);
        p2.setLayoutY(-2 * scale);
        p2.setScaleX(scale); p2.setScaleY(scale);

        Polygon p3 = new Polygon(
                21.0, 41.59998,
                34.39999, 38.99999,
                22.60000, 19.99999
        );
        p3.setFill(Color.web("#ff371f"));
        p3.setLayoutX(-11 * scale);
        p3.setLayoutY(-14 * scale);
        p3.setScaleX(scale); p3.setScaleY(scale);

        Polygon p4 = new Polygon(
                21.0, 41.59998,
                32.60000, 43.99999,
                32.60000, 25.59999
        );
        p4.setFill(Color.web("#ff371f"));
        p4.setLayoutX(-5 * scale);
        p4.setLayoutY(-19 * scale);
        p4.setScaleX(scale); p4.setScaleY(scale);

        Polygon p5 = new Polygon(
                23.39999, 43.99998,
                32.60000, 43.99999,
                30.0, 18.99999
        );
        p5.setFill(Color.web("#ff371f"));
        p5.setLayoutX(-10 * scale);
        p5.setLayoutY(-19 * scale);
        p5.setScaleX(scale); p5.setScaleY(scale);

        QuadCurve q2 = new QuadCurve(
                11.39999, -70.4,
                -5.0, -70.4,
                2.39999, -54.2
        );
        q2.setFill(Color.web("#ffb021"));
        q2.setLayoutX(16 * scale);
        q2.setLayoutY(94 * scale);
        q2.setScaleX(scale); q2.setScaleY(scale);

        Polygon y1 = new Polygon(
                21.0, 31.59999,
                28.79999, 34.59999,
                23.0, 20.79999
        );
        y1.setFill(Color.web("#ffb021"));
        y1.setLayoutX(-10 * scale);
        y1.setLayoutY(-8 * scale);
        y1.setScaleX(scale); y1.setScaleY(scale);

        Polygon y2 = new Polygon(
                21.19999, 34.59999,
                27.80000, 34.59999,
                25.39999, 19.59999
        );
        y2.setFill(Color.web("#ffb021"));
        y2.setLayoutX(-6 * scale);
        y2.setLayoutY(-10 * scale);
        y2.setScaleX(scale); y2.setScaleY(scale);

        Polygon y3 = new Polygon(
                19.19999, 32.59999,
                27.39999, 32.59999,
                25.80000, 20.99999
        );
        y3.setFill(Color.web("#ffb021"));
        y3.setLayoutY(-9 * scale);
        y3.setScaleX(scale); y3.setScaleY(scale);

        Polygon y4 = new Polygon(
                25.39999, 29.99999,
                31.60000, 31.59999,
                22.0, 22.19999
        );
        y4.setFill(Color.web("#ffb021"));
        y4.setLayoutX(-14 * scale);
        y4.setLayoutY(-6 * scale);
        y4.setScaleX(scale); y4.setScaleY(scale);

        Polygon y5 = new Polygon(
                15.19999, 30.99999,
                23.39999, 32.59999,
                27.80000, 23.79999
        );
        y5.setFill(Color.web("#ffb021"));
        y5.setLayoutX(4 * scale);
        y5.setLayoutY(-9 * scale);
        y5.setScaleX(scale); y5.setScaleY(scale);

        getChildren().addAll(
                p1, q1, p2, p3, p4, p5,
                q2, y1, y2, y3, y4, y5
        );
    }
}


