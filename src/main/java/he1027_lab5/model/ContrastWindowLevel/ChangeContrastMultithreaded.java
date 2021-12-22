package he1027_lab5.model.ContrastWindowLevel;

import he1027_lab5.model.ImageProcessing;


/**
 * ChangeContrast implements the interface ImageProcessing.
 */
public class ChangeContrastMultithreaded implements ImageProcessing {

    private final int noOfThreads;
    private final WindowLevelContrastAlgorithm algorithm;

    /**
     * Creates multithreaded instance of Window / Level contrast adjustment image processing implementation
     * @param window window size
     * @param level value of level
     * @param n number of threads to use
     */
    public ChangeContrastMultithreaded(int window, int level, int n) {
        algorithm = new WindowLevelContrastAlgorithm(window, level);
        this.noOfThreads = n;
    }
    /**
     * This override method uses the Windows/level method to change an image contrast.
     * @param src is used as parameter.
     * @return pixel matrix is returned by function.
     */
    @Override
    public void processImage(int[] src, int[] dst, int w, int h) {

        long time = System.currentTimeMillis();
//        int[][] matrix = new int[originalImg.length][originalImg[0].length];
//        IntBuffer result = IntBuffer.allocate(w*h);
        Thread[] threads = new Thread[noOfThreads];
        for (int i = 0; i < noOfThreads; i++) {
            threads[i] = new Thread(new PartialPixelMatrixProcessing(src, dst, i, noOfThreads, w, h));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (Exception e) {

        }
        System.out.println(System.currentTimeMillis() - time);
    }

    private class PartialPixelMatrixProcessing implements Runnable {
        private final int index, n, w, h;
        private final int[] src, dst;

        public PartialPixelMatrixProcessing(int[] src, int[] dst, int index, int n, int w, int h) {
            this.w = w;
            this.h = h;
            this.src = src;
            this.dst = dst;
            this.index = index;
            this.n = n;
        }
        @Override
        public void run() {
            int yStart = index * (w*h) / n;
            int yEnd = yStart + (w*h) / n;
            for (int i = yStart; i < yEnd; i++) {
                // TODO: plocka ut array i lokal variabel först?
                dst[i] = algorithm.adjustLevel(src[i]);
            }
//            for (int y = yStart; y < yEnd; y++) {
//                for (int x = 0; x < matrix[0].length; x++) {
//                    matrix[y][x] = algorithm.adjustLevel(originalImg[y][x]);
//                }
//            }
        }
    }
}
