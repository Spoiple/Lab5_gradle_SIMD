package he1027_lab5.model;

/**
 * Interface with an abstract method to process images.
 * An ImageProcessing represents the algorithm with which to process an image
 */

public interface ImageProcessing {
    /**
     * Applies processing an array of pixel-values
     * @param src the array of pixels to process
     * @return the processed array of pixel
     */
    void processImage(int[] src, int[] dst, int w, int h);
    // TODO: exception if src and dst is different size
}
