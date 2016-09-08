package edu.byu.cs.imageeditor.studentCode;

/**
 * Created by Marshall Garey on 8/31/2016.
 * 2-d array of pixels and methods that operate on an image.
 */
public class Image {

    private Pixel[][] pixels;
    private int rows;
    private int cols;

    public Image(int numRows, int numCols) {
        pixels = new Pixel[numRows][numCols];
        this.rows = numRows;
        this.cols = numCols;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Pixel getPixelAt(int row, int col) {
        if (row < 0 || col < 0) {
            System.out.printf("error at getPixelAt(), bad indices: row=%d, col=%d\n", row, col);
            System.out.println("Returning pixel[0][0]");
            return pixels[0][0];
        }
        else {
            return pixels[row][col];
        }
    }

    public void setPixelAt(int row, int col, int r, int g, int b) {
        if (row < 0 || col < 0) {
            System.out.printf("error at setPixelAt(), bad indices: row=%d, col=%d\n", row, col);
        }
        else {
            this.pixels[row][col].setRed(r);
            this.pixels[row][col].setGreen(g);
            this.pixels[row][col].setBlue(b);
        }
    }

    public void newPixelAt(int row, int col, int r, int g, int b) {
        if (row < 0 || col < 0) {
            System.out.printf("error at setPixelAt(), bad indices: row=%d, col=%d\n", row, col);
        }
        else {
            this.pixels[row][col] = new Pixel(r,g,b);
        }
    }

    // writes image to a string in ppm file format
    public String toString_ppm_format() {
        StringBuilder s = new StringBuilder("");
        s.append("P3\n");
        s.append(String.format("%d %d\n", this.cols, this.rows));
        s.append("255\n");

        for (int row_i = 0; row_i < this.rows; row_i++) {
            for (int col_i = 0; col_i < this.cols; col_i++) {
                s.append(String.format("%d\n%d\n%d\n", this.pixels[row_i][col_i].getRed(),
                        this.pixels[row_i][col_i].getGreen(), this.pixels[row_i][col_i].getBlue()));
            }
        }
        return s.toString();
    }

    // invert
    public String invertImage() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.pixels[i][j].invertPixel();
            }
        }
        return this.toString_ppm_format();
    }

    // grayscale
    public String grayscaleImage() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.pixels[i][j].grayscalePixel();
            }
        }
        return this.toString_ppm_format();
    }

    // emboss
    public String embossImage() {
        int maxDifference = 0;
        int newValue = 0;
        int rDiff, gDiff, bDiff;

        for (int row_i = this.rows - 1; row_i >= 0; row_i--) {
            for (int col_i = this.cols - 1; col_i >= 0; col_i--) {
                if ((row_i - 1 < 0) || (col_i - 1 < 0)) {
                    // set border pixel rgb to 128
                    this.pixels[row_i][col_i].setRed(128);
                    this.pixels[row_i][col_i].setGreen(128);
                    this.pixels[row_i][col_i].setBlue(128);
                }
                else {
                    // get max difference of current pixel rgb and ulh pixel rgb,
                    // giving red highest priority (if abs(diffs) are same), then green, then blue
                    // add 128, but limit this value to between 0 and 255,
                    // and then set pixel value to this value
                    rDiff = this.pixels[row_i][col_i].getRed() -
                            this.pixels[row_i-1][col_i-1].getRed();
                    gDiff = this.pixels[row_i][col_i].getGreen() -
                            this.pixels[row_i-1][col_i-1].getGreen();
                    bDiff = this.pixels[row_i][col_i].getBlue() -
                            this.pixels[row_i-1][col_i-1].getBlue();

                    // get max difference:
                    if (Math.abs(rDiff) >= Math.abs(gDiff)) {
                        if ( Math.abs(rDiff) >= Math.abs(bDiff)) {
                            maxDifference = rDiff;
                        }
                        else {
                            maxDifference = bDiff;
                        }
                    }
                    else {
                        if (Math.abs(gDiff) >= Math.abs(bDiff)) {
                            maxDifference = gDiff;
                        }
                        else {
                            maxDifference = bDiff;
                        }
                    }

                    // get new value
                    newValue = 128 + maxDifference;
                    if (newValue < 0) {
                        newValue = 0;
                    }
                    else if (newValue > 255) {
                        newValue = 255;
                    }

                    // set new value
                    this.pixels[row_i][col_i].setRed(newValue);
                    this.pixels[row_i][col_i].setGreen(newValue);
                    this.pixels[row_i][col_i].setBlue(newValue);
                } // end if-else
            } // end for (each column)
        } // end for (each row)
        return this.toString_ppm_format();
    }

    // motionblur
    public String motionblurImage(int numPixels, Image origImage) {
        // Make sure to not accept invalid blur length due to keyboard monkeys
        if (numPixels <= 0) {
            return this.toString_ppm_format();
        }

        int r, g, b;
        int numPixelsAveraged = 0;
        for (int row_i = this.rows - 1; row_i >= 0; row_i--) {
            for (int col_i = this.cols - 1; col_i >= 0; col_i--) {
                // average the colors
                r = g = b = 0;
                numPixelsAveraged = 0;
                for (int i = 0; i < numPixels; i++) {
                    if ((col_i + i) < this.cols) {
                        // pixel in bounds, add this to total value.
                        numPixelsAveraged++;
                        r += origImage.pixels[row_i][col_i+i].getRed();
                        g += origImage.pixels[row_i][col_i+i].getGreen();
                        b += origImage.pixels[row_i][col_i+i].getBlue();
                    }
                } // end for
                // average colors: divide by number of pixels added
                r /= numPixelsAveraged;
                g /= numPixelsAveraged;
                b /= numPixelsAveraged;
                // set pixel
                this.setPixelAt(row_i, col_i, r, g, b);
            } // end for (each col)
        } // end for (each row)
        return this.toString_ppm_format();
    }
}
