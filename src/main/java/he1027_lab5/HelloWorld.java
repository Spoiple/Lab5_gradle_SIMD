package he1027_lab5;

public class HelloWorld {
    public native void print();
    public native void print2();
    public native void print3();
    public native void print5(int[][] pixelMatrix, int window, int level);

    static {
        System.loadLibrary("hello");
    }
}
