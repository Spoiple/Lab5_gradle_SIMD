package he1027_lab5;

public class AVX_Contrast {
//    public native void print();
//    public native void print2();
//    public native void print3();
//    public native void simd4x(int[] srcMatrix, int[] dstMatrix, int window, int level);
    public native void simd8x(int[] srcMatrix, int[] dstMatrix, int window, int level);

    static {
        System.loadLibrary("CFunctionsLib");
    }
}
