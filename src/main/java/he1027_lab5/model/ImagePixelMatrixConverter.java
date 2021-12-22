package he1027_lab5.model;

import javafx.scene.image.*;

import java.nio.IntBuffer;

/**
 * Class for converting image to pixel matrix and vice versa.
 */
public class ImagePixelMatrixConverter {

    private ImagePixelMatrixConverter() {
    }

    /**
     * Method for converting an image to a pixel matrix.
     * @param image Image object to extract 2D pixel matrix from
     * @return a matrix of the image pixels.
     */
    public static IntBuffer getPixelMatrix(Image image) {
        long time = System.currentTimeMillis();
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        System.out.println(width + " " + height);

//        int[][] pixelMatrix = new int[height][width];
        IntBuffer buffer = IntBuffer.allocate(height*width);
        PixelReader reader = image.getPixelReader();
        reader.getPixels(0, 0, width, height, WritablePixelFormat.getIntArgbInstance(), buffer, width);
        System.out.println(System.currentTimeMillis() - time + " ms convert to matrix buffer");
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                pixelMatrix[y][x] = reader.getArgb(x, y);
//            }
//        }
//        for (int y = 0; y < height; y++) {
//            reader.getPixels(0, y, width, 1, WritablePixelFormat.getIntArgbInstance(), pixelMatrix[y], 0, 1);
//        }

//        Thread t1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int y = 0; y < height/4; y++) {
//                    reader.getPixels(0, y, width, 1, WritablePixelFormat.getIntArgbInstance(), pixelMatrix[y], 0, 1);
//                }
//            }
//        });
//        Thread t2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int y = height/4; y < (height/4)*2; y++) {
//                    reader.getPixels(0, y, width, 1, WritablePixelFormat.getIntArgbInstance(), pixelMatrix[y], 0, 1);
//                }
//            }
//        });
//        Thread t3 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int y = (height/4)*2; y < (height/4)*3; y++) {
//                    reader.getPixels(0, y, width, 1, WritablePixelFormat.getIntArgbInstance(), pixelMatrix[y], 0, 1);
//                }
//            }
//        });
//        Thread t4 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int y = (height/4)*3; y < height; y++) {
//                    reader.getPixels(0, y, width, 1, WritablePixelFormat.getIntArgbInstance(), pixelMatrix[y], 0, 1);
//                }
//            }
//        });
//        t1.start();
//        t2.start();
//        t3.start();
//        t4.start();
//        try {
//            t1.join();
//            t2.join();
//            t3.join();
//            t4.join();
//        } catch (Exception e) {
//        }
//        System.out.println(System.currentTimeMillis() - time + " ms convert to matrix threads");
        return buffer;
    }

    /**
     * Method for converting a pixel matrix to an image.
     * @param w width of image
     * @param h height of image
     * @return new image
     */
    public static Image getImage(IntBuffer buffer, int w, int h)  {
        long time = System.currentTimeMillis();

        WritableImage image = new WritableImage(w, h);
        PixelWriter writer = image.getPixelWriter();
        writer.setPixels(0, 0, w, h, PixelFormat.getIntArgbInstance(), buffer, w);
        System.out.println(System.currentTimeMillis() - time + " ms convert to image buffer");
//        for (int x = 0; x < image.getWidth(); x++) {
//            for (int y = 0; y < image.getHeight(); y++) {
//                writer.setArgb(x, y, pixelMatrix[x][y]);
//            }
//        }
//        Thread t1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int y = 0; y < image.getHeight()/4; y++) {
//                    writer.setPixels(0, y, width, 1, PixelFormat.getIntArgbInstance(), pixelMatrix[y], 0, 1);
//                }
//            }
//        });
//        Thread t2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int y = height/4; y < (image.getHeight()/4)*2; y++) {
//                    writer.setPixels(0, y, width, 1, PixelFormat.getIntArgbInstance(), pixelMatrix[y], 0, 1);
//                }
//            }
//        });
//        Thread t3 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int y = (height/4)*2; y < (image.getHeight()/4)*3; y++) {
//                    writer.setPixels(0, y, width, 1, PixelFormat.getIntArgbInstance(), pixelMatrix[y], 0, 1);
//                }
//            }
//        });
//        Thread t4 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int y = (height/4)*3; y < image.getHeight(); y++) {
//                    writer.setPixels(0, y, width, 1, PixelFormat.getIntArgbInstance(), pixelMatrix[y], 0, 1);
//                }
//            }
//        });
//        t1.start();
//        t2.start();
//        t3.start();
//        t4.start();
//        try {
//            t1.join();
//            t2.join();
//            t3.join();
//            t4.join();
//        } catch (Exception e) {
//        }
////        for (int y = 0; y < image.getHeight(); y++) {
////            writer.setPixels(0, y, width, 1, PixelFormat.getIntArgbInstance(), pixelMatrix[y], 0, 1);
////        }
//        System.out.println(System.currentTimeMillis() - time + " ms convert to image threads");
        return image;
    }
//    public static Image getImage(int[][] pixelMatrix) {
//        long time = System.currentTimeMillis();
//        int width = pixelMatrix.length;
//        int height = pixelMatrix[0].length;
//
//        WritableImage image = new WritableImage(width, height);
//        PixelWriter writer = image.getPixelWriter();
////        for (int x = 0; x < image.getWidth(); x++) {
////            for (int y = 0; y < image.getHeight(); y++) {
////                writer.setArgb(x, y, pixelMatrix[x][y]);
////            }
////        }
//        for (int y = 0; y < image.getHeight(); y++) {
//            writer.setPixels(0, y, width, height, PixelFormat.getIntArgbInstance(), pixelMatrix[y], 0, 1);
//        }
//        System.out.println(System.currentTimeMillis() - time + " ms convert to image");
//        return image;
//    }
}
