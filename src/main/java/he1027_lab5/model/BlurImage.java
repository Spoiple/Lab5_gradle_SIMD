package he1027_lab5.model;

public class BlurImage implements ImageProcessing {

    private int boxSize;

    public BlurImage(int boxSize) {
        if (boxSize % 2 == 0)
            boxSize++;
        this.boxSize = boxSize;
    }

    @Override
    public void processImage(int[] src, int[] dst, int w, int h) {
        // TODO: multi thread
//        int boxSize = 41;
        int[][] tmpSrc = new int[h][w];
        int[][] tmpDst = new int[h][w];
        for (int i = 0; i < src.length; i++) {
                tmpSrc[i/w][i%w] = src[i];
//                tmpDst[i/w][i%w] = src[i];
        }
        for (int i = (boxSize-1)/2; i < h - (boxSize-1)/2; i++) {
            for (int j = (boxSize-1)/2; j < w - (boxSize-1)/2; j++) {
                int tmp1 = 0;
                int tmp2 = 0;
                int tmp3 = 0;
                for (int k = i - (boxSize-1)/2; k < i + 1 + (boxSize-1)/2; k++) {
                    for (int l = j - (boxSize-1)/2; l < j + 1 + (boxSize-1)/2; l++ ) {
                        tmp1 += (tmpSrc[k][l] & 0x00FF0000) >> 16;
                        tmp2 += (tmpSrc[k][l] & 0x0000FF00) >> 8;
                        tmp3 += tmpSrc[k][l] & 0x000000FF;
                    }
                }
                tmp1 /= boxSize*boxSize;
                tmp2 /= boxSize*boxSize;
                tmp3 /= boxSize*boxSize;
                tmpDst[i][j] = (tmp1 << 16) | (tmp2 << 8) | tmp3 | 0xFF000000;
            }
        }
        for (int i = 0; i < src.length; i++) {
            dst[i] = tmpDst[i/w][i%w];
        }
    }

    public int blur(int x, int y, int boxSize) {
        int[][] box = new int[boxSize][boxSize];
        return 1;
    }
}
