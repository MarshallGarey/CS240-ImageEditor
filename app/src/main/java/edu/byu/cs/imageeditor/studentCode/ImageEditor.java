package edu.byu.cs.imageeditor.studentCode;

import java.io.BufferedReader;
import java.util.Scanner;

/**
 * Created by Marshall Garey on 8/31/2016.
 * Lab 1 of CS240.
 */
public class ImageEditor implements IImageEditor {

    private Image image; // the original image that we read in
    private Image editedImage; // working copy - we'll change this one

    public ImageEditor() {
        // nothing to do for the constructor
    }

    @Override
    public void load(BufferedReader bufferedReader) {
        // read the file and store it an a new image
        Scanner s = new Scanner(bufferedReader);
        int width = 0;
        int height = 0;

        // regular expression for delimiter to skip comments and whitespace
        s.useDelimiter("(\\s+(#[^\\n]*\\n)?|(#[^\\n]*\\n))+");

        s.next(); // skip P3
        // get width and height: (num cols and num rows)
        width = s.nextInt();
        height = s.nextInt();
        // skip max color value:
        s.next();
        // create image object (rows, columns)
        image = new Image(height, width);
        // set pixels
        int r, g, b;

        for (int rows_i = 0; rows_i < height; ++rows_i) {
            for (int cols_i = 0; cols_i < width; ++cols_i) {
                r = s.nextInt();
                g = s.nextInt();
                b = s.nextInt();
                image.newPixelAt(rows_i, cols_i, r, g, b);
            }
        }
        s.close();
    }

    private Image copyImage(Image image) {
        int r, g, b;
        int numRows = image.getRows();
        int numCols = image.getCols();
        Image newImage = new Image(numRows, numCols);
        for (int row_i = 0; row_i < numRows; ++row_i) {
            for (int col_i = 0; col_i < numCols; ++col_i) {
                r = image.getPixelAt(row_i, col_i).getRed();
                g = image.getPixelAt(row_i, col_i).getGreen();
                b = image.getPixelAt(row_i, col_i).getBlue();
                newImage.newPixelAt(row_i, col_i, r, g, b);
            }
        }
        return newImage;
    }

    @Override
    public String invert() {
        editedImage = copyImage(image);
        return editedImage.invertImage();
    }

    @Override
    public String grayscale() {
        editedImage = copyImage(image);
        return editedImage.grayscaleImage();
    }

    @Override
    public String emboss() {
        editedImage = copyImage(image);
        return editedImage.embossImage();
    }

    @Override
    public String motionblur(int blurLength) {
        editedImage = copyImage(image);
        return editedImage.motionblurImage(blurLength, image);
    }
}
