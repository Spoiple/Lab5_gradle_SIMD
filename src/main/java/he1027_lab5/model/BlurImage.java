package he1027_lab5.model;

import java.nio.IntBuffer;

public class BlurImage implements ImageProcessing {

    private int boxSize;

    public BlurImage(int boxSize) {
        if (boxSize % 2 == 0)
            boxSize++;
        this.boxSize = boxSize;
    }

    @Override
    public IntBuffer processImage(IntBuffer originalImg, int w, int h) {
        // TODO
//        int[][] matrix = new int[originalImg.length][originalImg[0].length];
//
//        for (int x = 0; x < originalImg.length; x++) {
//            for (int y = 0; y < originalImg[0].length; y++) {
//                matrix[x][y] = blur(x, y, boxSize);
//            }
//        }
        return originalImg;
    }

    public int blur(int x, int y, int boxSize) {
        int[][] box = new int[boxSize][boxSize];
        return 1;
    }


}
