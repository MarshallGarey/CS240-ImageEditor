package edu.byu.cs.imageeditor.studentCode;

/**
 * Created by Marshall Garey on 8/31/2016.
 * A valid color is an integer in the range [0,255]
 */
public class Color {
    private int color;
    private static final int MAX_COLOR = 255;
    private static final int MIN_COLOR = 0;

    public Color(int color) throws Exception {
        if (color < MIN_COLOR || color > MAX_COLOR) {
            throw new Exception();
        }
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
