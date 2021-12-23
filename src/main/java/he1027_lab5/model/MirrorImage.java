package he1027_lab5.model;


/**
 * Class implements interface ImageProcessing.
 * Can mirror an image vertically, horizontally or a combination of both.
 */
public class MirrorImage implements ImageProcessing {

    private final MirrorOrientation orientation;
    private final ORIENTATION mirrorOrientation;
    public enum ORIENTATION {
        VERTICAL, HORIZONTAL, VERTICAL_HORIZONTAL
    }
    
    private MirrorImage(MirrorOrientation orientation, ORIENTATION mirrorOrientation) {
        this.orientation = orientation;
        this.mirrorOrientation = mirrorOrientation;
    }

    /**
     * Creates an instance which will mirror an image vertically
     * @return a new MirrorImage
     */
    public static MirrorImage createVerticalMirror() {
        return new MirrorImage(new VerticalMirror(), ORIENTATION.VERTICAL);
    }
    /**
     * Creates an instance which will mirror an image horizontally
     * @return a new MirrorImage
     */
    public static MirrorImage createHorizontalMirror() {
        return new MirrorImage(new HorizontalMirror(), ORIENTATION.HORIZONTAL);
    }
    /**
     * Creates an instance which will mirror an image vertically and horizontally
     * @return a new MirrorImage
     */
    public static MirrorImage createVerticalHorizontalMirror() {
        return new MirrorImage(new VerticalHorizontalMirror(), ORIENTATION.VERTICAL_HORIZONTAL);
    }

    /**
     * This override method creates an array according to the instance setup. 
     * @param src the array of pixels to process
     * @return an pixel matrix.
     */
    @Override
    public void processImage(int[] src, int[] dst, int w, int h) throws IllegalArgumentException {
        if (src.length != dst.length)
            throw new IllegalArgumentException("src and dst array must be of equal length");
        // TODO
//        int[][] matrix = new int[originalImg.length][originalImg[0].length];
//        int width = originalImg.length;
//        int height = originalImg[0].length;
        orientation.mirror(src, dst, w);
//        for (int i = 0; i < w*h; i++) {
//            dst[i] = orientation.mirror(src[i], i%w, i/w);
//        }
//
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                matrix[x][y] = orientation.mirror(originalImg, x, y);
//            }
//        }
    }

    /**
     * Get the orientation of the mirror processing algorithm
     * @return processing ORIENTATION of this instance
     */
    public ORIENTATION getMirrorOrientation() {
        return mirrorOrientation;
    }

    private abstract static class MirrorOrientation {
        abstract void mirror(int[] src, int[] dst, int w);
    }

    private static class VerticalMirror extends MirrorOrientation {
        @Override
        void mirror(int[] src, int[] dst, int w) {
            for (int i = 0; i < src.length; i++) {
                dst[i] = src[(((src.length - 1) - i ) / w) * w + (i%w)];
            }
        }
    }

    private static class HorizontalMirror extends MirrorOrientation {
        @Override
        public void mirror(int[] src, int[] dst, int w) {
            for (int i = 0; i < src.length; i++) {
                dst[i] = src[ (i / w) * w + (w - 1) - (i % w) ];
            }
        }
    }

    private static class VerticalHorizontalMirror extends MirrorOrientation {
        @Override
        public void mirror(int[] src, int[] dst, int w) {
            for (int i = 0; i < src.length; i++) {
                int i1 = (((src.length - 1) - i) / w) * w + (i % w);
                dst[i] = src[ (i1 / w) * w + (w - 1) - (i1 % w) ];
            }
        }
    }
}
