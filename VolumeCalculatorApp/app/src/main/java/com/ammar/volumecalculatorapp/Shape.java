package com.ammar.volumecalculatorapp;

/**
 * This class is acting as: "Model Class"
 * it represents the data structure or individual data items
 * that your adapter is going to display
 * In our grid_item_layout we have two things to display one is imageView and another one is
 * textView.
 * So a model class defines the structure for each individual data item that your adapter will
 * display.
 */
public class Shape {
    int shapeImage;
    String shapeName;

    public Shape(int shapeImage, String shapeName) {
        this.shapeImage = shapeImage;
        this.shapeName = shapeName;
    }

    public int getShapeImage() {
        return shapeImage;
    }

    public void setShapeImage(int shapeImage) {
        this.shapeImage = shapeImage;
    }

    public String getShapeName() {
        return shapeName;
    }

    public void setShapeName(String shapeName) {
        this.shapeName = shapeName;
    }
}
