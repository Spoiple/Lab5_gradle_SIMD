package he1027_lab5.model.ContrastWindowLevel;

import he1027_lab5.model.ImageProcessing;
import he1027_lab5.ThreadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * ChangeContrast implements the interface ImageProcessing.
 */
public class ChangeContrastMultithreaded implements ImageProcessing {

    private static int noOfThreads;
    protected CountDownLatch latch;
    private static WindowLevelContrastAlgorithm algorithm;
    private static final ThreadPoolExecutor tp = new ThreadPoolExecutor(8, 8,
                           120,
                                        java.util.concurrent.TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(8));;
    private static ChangeContrastMultithreaded ccm; // TODO: ?

    private ChangeContrastMultithreaded() {
        ThreadPool.addToPool(tp);
//        this.tp = new ThreadPoolExecutor(8, 8,
//                120, java.util.concurrent.TimeUnit.SECONDS,
//                new ArrayBlockingQueue<>(4));
    }

    public static ChangeContrastMultithreaded SetChangeContrastMultithreaded(int window, int level, int n) {
        if (ccm == null)
            ccm = new ChangeContrastMultithreaded();
        algorithm = new WindowLevelContrastAlgorithm(window, level);
        noOfThreads = n;
        return ccm;
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
//        Thread[] threads = new Thread[noOfThreads];

        latch = new CountDownLatch(noOfThreads);
        for (int i = 0; i < noOfThreads; i++) {
            tp.execute(new PartialPixelMatrixProcessing(src, dst, i, noOfThreads, w, h));
//            threads[i] = new Thread(new PartialPixelMatrixProcessing(src, dst, i, noOfThreads, w, h));
        }
//        for (Thread thread : threads) {
//            thread.start();
//        }
//        try {
//            for (Thread thread : threads) {
//                thread.join();
//            }
//        } catch (Exception e) {
//
//        }
        try {
            latch.await();
        } catch (Exception e) {
            System.out.println("latch await failed");
            e.printStackTrace();
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
            long time = System.currentTimeMillis();
            int yStart = index * (w*h) / n;
            int yEnd = yStart + (w*h) / n;
            for (int i = yStart; i < yEnd; i++) {
                dst[i] = algorithm.adjustLevel(src[i]);
            }
            System.out.println(System.currentTimeMillis() - time + " ms thread: " + index);
            latch.countDown();
//            for (int y = yStart; y < yEnd; y++) {
//                for (int x = 0; x < matrix[0].length; x++) {
//                    matrix[y][x] = algorithm.adjustLevel(originalImg[y][x]);
//                }
//            }
        }
    }
}
