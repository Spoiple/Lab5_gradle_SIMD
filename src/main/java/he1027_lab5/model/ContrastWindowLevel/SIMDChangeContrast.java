package he1027_lab5.model.ContrastWindowLevel;

import he1027_lab5.HelloWorld;
import he1027_lab5.model.ImageProcessing;

import java.nio.IntBuffer;


public class SIMDChangeContrast implements ImageProcessing {

    private final int level, window;

    public SIMDChangeContrast(int level, int window) {
        this.level = level;
        this.window = window;
    }

    @Override
    public IntBuffer processImage(IntBuffer originalImg, int w, int h) {
        // TODO
////        long time = System.currentTimeMillis();
//        int[][] matrix = new int[originalImg.length][originalImg[0].length];
//        for (int i = 0; i < originalImg.length; i++) {
//            matrix[i] = originalImg[i].clone();
//        }
//
//        new HelloWorld().print5(matrix, level, window);
//
////        System.out.println(System.currentTimeMillis() - time);
        return originalImg;
    }
}
