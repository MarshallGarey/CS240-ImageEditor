package edu.byu.cs.imageeditor.studentCode;

/**
 * Created by Marshall Garey on 8/31/2016.
 * Just a set of colors
 */
public class Pixel {
    private Color red;
    private Color green;
    private Color blue;
    private final int PIXEL_MAX = 255;

    public Pixel(int red, int green, int blue) {
        try {
            this.red = new Color(red);
            this.green = new Color(green);
            this.blue = new Color(blue);
        } catch (Exception e) {
            System.out.printf("Exception creating color.\n");
            e.printStackTrace();
        }
    }

    public int getRed() {
        return red.getColor();
    }

    public void setRed(int red) {
        this.red.setColor(red);
    }

    public int getGreen() {
        return green.getColor();
    }

    public void setGreen(int green) {
        this.green.setColor(green);
    }

    public int getBlue() {
        return blue.getColor();
    }

    public void setBlue(int blue) {
        this.blue.setColor(blue);
    }

    // invert: 255-colorValue
    public void invertPixel() {
        this.red.setColor(PIXEL_MAX - this.red.getColor());
        this.green.setColor(PIXEL_MAX - this.green.getColor());
        this.blue.setColor(PIXEL_MAX - this.blue.getColor());
    }

    public void grayscalePixel() {
        int ave = (this.red.getColor() + this.green.getColor() + this.blue.getColor()) / 3;
        this.red.setColor(ave);
        this.green.setColor(ave);
        this.blue.setColor(ave);
    }
}
