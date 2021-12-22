package he1027_lab5.model.ContrastWindowLevel;

import he1027_lab5.HelloWorld;
import he1027_lab5.model.ImageProcessing;


public class SIMDChangeContrast implements ImageProcessing {

    private final int level, window;

    public SIMDChangeContrast(int window, int level) {
        this.window = window;
        this.level = level;
    }

    @Override
    public void processImage(int[] src, int[] dst, int w, int h) {

        long time = System.currentTimeMillis();
        // TODO: Crash with window = 0
        new HelloWorld().print5(src, dst, window, level);
        System.out.println(System.currentTimeMillis() - time);

    }
}
