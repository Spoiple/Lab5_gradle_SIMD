package he1027_lab5.controller;

import he1027_lab5.model.BlurImage;
import he1027_lab5.model.ImageProcessingModel;
import he1027_lab5.ThreadPool;
import he1027_lab5.view.BlurView;

public class BlurController {
    private final ImageProcessingModel model;
    private final BlurView view;

    public BlurController(ImageProcessingModel model, BlurView view) {
        this.model = model;
        this.view = view;
    }

    public void handleBlur(int boxSize) {
        ThreadPool.getThreadPool().execute(() -> {
            model.processImage(new BlurImage(boxSize));
            javafx.application.Platform.runLater(() -> {
                view.updateFromModel();
                view.showProcessedImage();
                view.updateStatusText("Preview");
            });
        });
    }

    public void handleApply() {
        model.applyProcessing();
        view.updateFromModel();
        view.updateStatusText("Image blurred");
    }
}
