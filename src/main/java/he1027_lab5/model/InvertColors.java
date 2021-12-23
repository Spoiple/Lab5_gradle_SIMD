package he1027_lab5.model;


/**
 * Class Implements the interface ImageProcessing.
 */
public class InvertColors implements ImageProcessing {

    /**
     * This override method inverts the rgb bit pattern of an image pixel matrix.
     * @param src converted to a pixel matrix.
     * @return inverted matrix.
     */
    @Override
    public void processImage(int[] src, int[] dst, int w, int h) {
        // TODO
//        int[][] matrix = new int[originalImg.length][originalImg[0].length];
//
//        for (int x = 0; x < originalImg.length; x++) {
//            for (int y = 0; y < originalImg[0].length; y++) {
//                int alpha = (0xFF000000 &  originalImg[x][y]);  // extract alpha
//                int rgb   = (0x00FFFFFF & ~originalImg[x][y]);  // invert rgb
//                matrix[x][y] = alpha | rgb;
//            }
//        }
    }
}
