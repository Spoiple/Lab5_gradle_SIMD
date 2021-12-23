package he1027_lab5.model.ContrastWindowLevel;

import he1027_lab5.AVX_Contrast;
import he1027_lab5.model.ImageProcessing;


public class SIMDChangeContrast implements ImageProcessing {

    private final int level, window;

    public SIMDChangeContrast(int window, int level) {
        this.window = window;
        this.level = level;
    }

    @Override
    public void processImage(int[] src, int[] dst, int w, int h) throws IllegalArgumentException {
        if (src.length != dst.length)
            throw new IllegalArgumentException("src and dst array must be of equal length");
        long time = System.currentTimeMillis();
        // TODO: Crash with window = 0
        new AVX_Contrast().simd8x(src, dst, window, level);
        System.out.println(System.currentTimeMillis() - time);

    }
}
