package he1027_lab5.model;

import java.nio.IntBuffer;

/**
 * Interface with an abstract method to process images.
 * An ImageProcessing represents the algorithm with which to process an image
 */

public interface ImageProcessing {
    /**
     * Applies processing an array of pixel-values
     * @param originalImg the array of pixels to process
     * @return the processed array of pixel
     */
    IntBuffer processImage(IntBuffer originalImg, int w, int h);
}
