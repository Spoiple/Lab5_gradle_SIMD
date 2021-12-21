package he1027_lab5.model.ContrastWindowLevel;

import he1027_lab5.model.ImageProcessing;

import java.nio.IntBuffer;

/**
 * ChangeContrast implements the interface ImageProcessing.
 */
public class ChangeContrast implements ImageProcessing {

    private final WindowLevelContrastAlgorithm algorithm;

    /**
     * Constructor for ChangeContrast class with below to parameters.
     * @param window value to apply
     * @param level value to apply
     */
    public ChangeContrast(int window, int level) {
        algorithm = new WindowLevelContrastAlgorithm(window, level);
    }

    /**
     * This override method uses the Windows/level method to change an image contrast.
     * @param originalImg is used as parameter.
     * @return pixel matrix is returned by function.
     */
    @Override
    public IntBuffer processImage(IntBuffer originalImg, int w, int h) {
        // TODO
//        int[][] matrix = new int[originalImg.length][originalImg[0].length];
//
//        for (int y = 0; y < originalImg.length; y++) {
//            for (int x = 0; x < originalImg[0].length; x++) {
//                matrix[y][x] = algorithm.adjustLevel(originalImg[y][x]);
//            }
//        }
        return originalImg;
    }
}
