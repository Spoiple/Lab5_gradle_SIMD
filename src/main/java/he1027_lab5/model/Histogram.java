package he1027_lab5.model;

import javafx.scene.image.Image;

/**
 * Class for a Histogram.
 */
public class Histogram {
    private int[][] histogram;

    /**
     * Constructor for a Histogram.
     * @param image is a parameter for this constructor.
     */
    public Histogram(Image image) {
        histogram = createHistogram(ImagePixelMatrixConverter.getPixelMatrix(image).array());
    }

    private int[][] createHistogram(int[] pixelArray) {
        int[][] result = new int[3][256];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < pixelArray.length; j++) {
                    result[i][(pixelArray[j] >> 8*(2-i)) & 0x000000FF]++;
                }
            }
        return result;
    }

    /**
     * Function to get the Histograms red channel array.
     * @return an array with data of the red channel.
     */
    public int[] getRedHistogram() {
        return histogram[0];
    }

    /**
     * Function to get the Histograms green channel array.
     * @return an array with data of the green channel.
     */
    public int[] getGreenHistogram() {
        return histogram[1];
    }

    /**
     * Function to get the Histograms blue channel array.
     * @return an array with data of the blue channel.
     */
    public int[] getBlueHistogram() {
        return histogram[2];
    }
}
