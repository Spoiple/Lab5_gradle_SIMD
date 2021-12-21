package he1027_lab5.controller;

import he1027_lab5.model.ImageProcessingModel;
import he1027_lab5.view.HistogramView;

public class HistogramController {
    private final HistogramView view;
    private final ImageProcessingModel model;

    public HistogramController(ImageProcessingModel model, HistogramView view) {
        this.view = view;
        this.model = model;
    }

    public void handleUpdate() {
        model.createHistogram();
        view.updateFromModel();
        view.updateStatusText("Histogram created");
    }
}
