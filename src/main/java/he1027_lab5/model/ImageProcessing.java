package he1027_lab5.model;

/**
 * Interface with an abstract method to process images.
 * An ImageProcessing represents the algorithm with which to process an image
 */

public interface ImageProcessing {
    /**
     * Applies processing an array of pixel-values
     * @param src
     * @param dst
     * @param w
     * @param h
     * @throws IllegalArgumentException
     */
    void processImage(int[] src, int[] dst, int w, int h) throws IllegalArgumentException;
    // TODO: exception if src and dst is different size
}
