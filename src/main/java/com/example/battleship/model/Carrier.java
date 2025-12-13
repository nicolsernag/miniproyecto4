package com.example.battleship.model;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;

/** * Represents an aircraft carrier ship in the Battleship game.
 * The carrier occupies 4 cells on the game board.
 */
public class Carrier extends Ship {

    /**
     * Constructs an aircraft carrier with a specified cell size for scaling.
     *
     * @param cellSize the size of a single cell on the game board
     */
    public Carrier(double cellSize) {
        super(4); // carrier occupies 4 cells


        // Base panel of the aircraft carrier (taken from FXML)
        Rectangle cuerpo = new Rectangle(134, 22);
        cuerpo.setFill(Color.web("#5d7b96"));
        cuerpo.setStroke(Color.BLACK);
        cuerpo.setArcWidth(5);
        cuerpo.setArcHeight(5);
        cuerpo.setLayoutX(17);
        cuerpo.setLayoutY(8);

        // Runway (upper/side)
        Rectangle pista1 = new Rectangle(57, 11);
        pista1.setFill(Color.web("#21496e"));
        pista1.setStroke(Color.BLACK);
        pista1.setArcWidth(5);
        pista1.setArcHeight(5);
        pista1.setLayoutX(98);
        pista1.setLayoutY(7);
        pista1.getTransforms().add(new Rotate(-7.4, 0, 0));

        Rectangle pista2 = new Rectangle(57, 11);
        pista2.setFill(Color.web("#21496e"));
        pista2.setStroke(Color.BLACK);
        pista2.setArcWidth(5);
        pista2.setArcHeight(5);
        pista2.setLayoutX(99);
        pista2.setLayoutY(24);
        pista2.getTransforms().add(new Rotate(7.4, 0, 0));

        // White lines on the runway
        Line w1 = linea(20.2, 1.8, 31, 0.2, 83, 14);
        Line w2 = linea(7.4, 1.4, 17.2, 3.0, 97, 25);
        Line w3 = linea(21.8, 1.4, 34, -0.2, 97, 12);
        Line w4 = linea(20.2, 1.8, 34, -0.2, 116, 9);
        Line w5 = linea(7.6, 1.6, 20.6, 3.4, 111, 27);
        Line w6 = linea(8.6, 1.8, 22.6, 3.4, 127, 29);

        // bridge/cabin
        Rectangle cabina1 = rect(18, 4, "#1e3143", 5, 13);
        Rectangle cabina2 = rect(18, 4, "#1e3143", 5, 20);

        //superstructure
        Rectangle torre = rect(13, 17, "#2468a6", 19, 10);
        Rectangle deck = rect(62, 16, "#21496e", 35, 11);

        // Deck's white lines
        Line d1 = linea(13, -0.2, 22.4, -0.2, 26, 19);
        Line d2 = linea(12, -0.2, 22.4, -0.2, 42, 19);
        Line d3 = linea(12, -0.2, 20.8, -0.2, 58, 19);
        Line d4 = linea(13.4, -0.2, 22.4, -0.2, 71, 19);

        getChildren().addAll(
                cuerpo,
                pista1, pista2,
                w1, w2, w3, w4, w5, w6,
                cabina1, cabina2,
                torre, deck,
                d1, d2, d3, d4
        );

        // ==============================
        //SCALE TO 4 CELLS
        // ==============================
        this.applyCss();
        this.layout();

        Bounds b = this.getBoundsInLocal();
        double realWidth = b.getWidth();

        double targetWidth = cellSize * 4;
        double scaleFactor = targetWidth / realWidth;

        this.getTransforms().add(new Scale(scaleFactor, scaleFactor));
    }

    // ------------------ Helpers ------------------------

    /**
     * Creates a white line with specified start and end coordinates and layout position.
     *
     * @param sx the starting x-coordinate of the line
     * @param sy the starting y-coordinate of the line
     * @param ex the ending x-coordinate of the line
     * @param ey the ending y-coordinate of the line
     * @param lx the layout x-position of the line
     * @param ly the layout y-position of the line
     * @return the created Line object
     */
    private Line linea(double sx, double sy, double ex, double ey, double lx, double ly) {
        Line l = new Line(sx, sy, ex, ey);
        l.setStroke(Color.WHITE);
        l.setLayoutX(lx);
        l.setLayoutY(ly);
        return l;
    }

    /**
     * Creates a rectangle with specified dimensions, color, and layout position.
     *
     * @param w     the width of the rectangle
     * @param h     the height of the rectangle
     * @param color the fill color of the rectangle in hex format
     * @param x     the layout x-position of the rectangle
     * @param y     the layout y-position of the rectangle
     * @return the created Rectangle object
     */
    private Rectangle rect(double w, double h, String color, double x, double y) {
        Rectangle r = new Rectangle(w, h);
        r.setFill(Color.web(color));
        r.setStroke(Color.BLACK);
        r.setArcWidth(5);
        r.setArcHeight(5);
        r.setLayoutX(x);
        r.setLayoutY(y);
        return r;
    }
}
