package he1027_lab5.model.ContrastWindowLevel;

public class WindowLevelContrastAlgorithm {

    protected int level;
    protected int window;
    protected double slope;

    protected WindowLevelContrastAlgorithm(int window, int level) {
        this.level = level;
        this.window = window;
        this.slope = 255.0 / (window - level);
    }

    protected int adjustLevel(int argb) {
        int result = argb;
        if ((argb & 0xFF) <= level)
            result &= 0xFFFFFF00;
        else if ((argb & 0xFF) > window)
            result |= 0x000000FF;
        else if ((argb & 0xFF) > level && (argb & 0xFF) <= window) {
            int newColor = (int) slope * ((argb & 0xFF) - level);
            result &= 0xFFFFFF00;
            result |= newColor;
        }
        if ((argb >> 8 & 0xFF) <= level)
            result &= 0xFFFF00FF;
        else if ((argb >> 8 & 0xFF) > window)
            result |= 0x0000FF00;
        else if ((argb >> 8 & 0xFF) > level && (argb >> 8 & 0xFF) <= window) {
            int newColor = (int) (slope * (((argb >> 8) & 0xFF) - level)) << 8;
            result &= 0xFFFF00FF;
            result |= newColor;
        }
        if ((argb >> 16 & 0xFF) <= level)
            result &= 0xFF00FFFF;
        else if ((argb >> 16 & 0xFF) > window)
            result |= 0x00FF0000;
        else if ((argb >> 16 & 0xFF) > level && (argb >> 16 & 0xFF) <= window) {
            int newColor = (int) (slope * ((argb >> 16 & 0xFF) - level)) << 16;
            result &= 0xFF00FFFF;
            result |= newColor;
        }
        return result;
    }
}
