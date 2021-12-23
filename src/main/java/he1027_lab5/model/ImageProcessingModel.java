package he1027_lab5.model;

import javafx.scene.image.Image;

import java.nio.IntBuffer;

/**
 *  Model to process an image using algorithms implemented with the ImageProcessing interface.
 *  Capable of stacking multiple changes to the image and can provide a preview image before changes are applied.
 *  Possible to reset all changes made to image.
 *  Histogram represents the image with currently applied processing
 */
public class ImageProcessingModel {
    private Image rawImage, processedImage, previewImage;
    private int[] srcImgArr, processedImgArray;
    private Histogram histogram;
    private int w, h;

    /**
     * Creates a new ImageProcessingModel with the supplied image as the base image
     * @param image Image object to process
     */
    public ImageProcessingModel(Image image) {
        setImage(image);
    }
    /**
     * Creates a new ImageProcessingModel with the supplied image as the base image
     */
    public ImageProcessingModel() {
        this.rawImage = null;
    }
    /**
     * Creates new local histogram from currently processed image
     * @throws NullPointerException if no image is present in the model
     */
    public void createHistogram() throws NullPointerException {
        if (rawImage == null)
            throw new NullPointerException("No image has been added to model");
        if (processedImage == null)
            processedImage = rawImage;

        histogram = new Histogram(processedImage);
    }
    /**
     * Set image to process
     * @param image Image the model will process
     */
    public void setImage(Image image) {
        w = (int) image.getWidth();
        h = (int) image.getHeight();
        rawImage = image;
        srcImgArr = ImagePixelMatrixConverter.getPixelMatrix(rawImage).array();
        processedImgArray = new int[w*h];
        processedImage = null;
        createHistogram();
    }
    /**
     * Get red color channel frequency
     * @return array of red channel frequency
     * @throws NullPointerException if no image is present in model
     */
    public int[] getRedHistogram() throws NullPointerException {
        if (histogram == null)
            createHistogram();
        return histogram.getRedHistogram();
    }
    /**
     * Get green color channel frequency
     * @return array of green channel frequency
     * @throws NullPointerException if no image is present in model
     */
    public int[] getGreenHistogram() throws NullPointerException {
        if (histogram == null)
            createHistogram();
        return histogram.getGreenHistogram();
    }
    /**
     * Get blue color channel frequency
     * @return array of blue channel frequency
     * @throws NullPointerException if no image is present in model
     */
    public int[] getBlueHistogram() throws NullPointerException {
        if (histogram == null)
            createHistogram();
        return histogram.getBlueHistogram();
    }
    /**
     * Get preview image with changes applied
     * @return preview image with changes applied
     */
    public Image getPreviewImage() {
        if (previewImage == null)
            return getProcessedImage();
        else return previewImage;
    }
    /**
     * Applies currently pending changes to image
     */
    public void applyProcessing() {
        processedImage = previewImage;
        srcImgArr = ImagePixelMatrixConverter.getPixelMatrix(processedImage).array();
    }
    /**
     * Processes the image with the supplied ImageProcessing object.
     * The result will be pending after this is called,
     * use applyChanges() to apply the changes to the final image.
     * @param processing implementing ImageProcessing
     * @throws NullPointerException if no image is present in model
     */
    public void processImage(ImageProcessing processing) throws NullPointerException {
        if (rawImage == null)
            throw new NullPointerException("No image has been added to model");
        if (processedImage == null)
            processedImage = rawImage;
        processing.processImage(srcImgArr, processedImgArray, w, h);
        previewImage = ImagePixelMatrixConverter.getImage(IntBuffer.wrap(processedImgArray), w, h);
    }
    /**
     * Get processed image
     * @return processed image
     */
    public Image getProcessedImage() {
        if (processedImage == null)
            return rawImage;
        else return processedImage;
    }
    /**
     * Get original image
     * @return original image
     */
    public Image getRawImage() {
        return rawImage;
    }
    /**
     * Resets any processed changes.
     */
    public void resetModel() {
        processedImage = rawImage;
        srcImgArr = ImagePixelMatrixConverter.getPixelMatrix(rawImage).array();
        histogram = new Histogram(processedImage);
    }
    /**
     * Resets any pending changes
     */
    public void clearPreview() {
        previewImage = processedImage;
    }
}
