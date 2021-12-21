package he1027_lab5.model.ContrastWindowLevel;

import he1027_lab5.HelloWorld;
import he1027_lab5.model.ImageProcessing;

import java.nio.IntBuffer;


public class SIMDChangeContrast implements ImageProcessing {

    private final int level, window;

    public SIMDChangeContrast(int window, int level) {
        this.window = window;
        this.level = level;
    }

    @Override
    public IntBuffer processImage(IntBuffer originalImg, int w, int h) {

        // TODO : move result buffer to model
        IntBuffer result = IntBuffer.allocate(w*h);

        long time = System.currentTimeMillis();

        new HelloWorld().print5(originalImg.array(), result.array(), window, level);
        System.out.println(System.currentTimeMillis() - time);

        return result;
    }
}
