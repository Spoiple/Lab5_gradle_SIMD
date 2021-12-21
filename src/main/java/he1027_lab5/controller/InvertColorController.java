package he1027_lab5.controller;

import he1027_lab5.model.ImageProcessingModel;
import he1027_lab5.model.InvertColors;
import he1027_lab5.view.InvertColorView;

public class InvertColorController {
    private final ImageProcessingModel model;
    private final InvertColorView view;

    public InvertColorController(ImageProcessingModel model, InvertColorView view) {
        this.model = model;
        this.view = view;
    }

    public void handleInvertColors() {
        new Thread(() -> {
            {
                model.processImage(new InvertColors());
                javafx.application.Platform.runLater(() -> {
                    view.showProcessedImage();
                    view.updateStatusText("Preview");
                });
            }
        }).start();
    }

    public void handleApply() {
        model.applyProcessing();
        view.updateFromModel();
        view.updateStatusText("Color inverted");
    }
}
