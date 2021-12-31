package he1027_lab5.controller;

import he1027_lab5.model.ImageProcessingModel;
import he1027_lab5.model.MirrorImage;
import he1027_lab5.view.MirrorImageView;

public class MirrorImageController {
    private final ImageProcessingModel model;
    private final MirrorImageView view;

    public MirrorImageController(ImageProcessingModel model, MirrorImageView view) {
        this.model = model;
        this.view = view;
    }

    public void handleMirror() {
        if (view.isVerticalChecked() && view.isHorizontalChecked()) {
//            model.addLayer(MirrorImage.createVerticalHorizontalMirror());
            model.processImage("Vertical and horizontal mirror", MirrorImage.createVerticalHorizontalMirror());
            view.showPreviewImage();
            view.updateStatusText("Mirrored vertically and horizontally");
        }
        else if (view.isVerticalChecked()) {
//            model.addLayer(MirrorImage.createVerticalMirror());
            model.processImage("Vertical mirror" ,MirrorImage.createVerticalMirror());
            view.showPreviewImage();
            view.updateStatusText("Mirrored vertically");
        }
        else if (view.isHorizontalChecked()) {
//            model.addLayer(MirrorImage.createHorizontalMirror());
            model.processImage("Horizontal mirror", MirrorImage.createHorizontalMirror());
            view.showPreviewImage();
            view.updateStatusText("Mirrored horizontally");
        }
        else {
            model.clearPreview();
            view.showOriginalImage();
        }
        view.updateFromModel();
    }

    public void handleApply() {
        model.applyProcessing();
        view.updateFromModel();
        view.updateStatusText("Applied changes");
    }
}
