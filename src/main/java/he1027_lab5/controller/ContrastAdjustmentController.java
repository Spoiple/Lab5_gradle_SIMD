package he1027_lab5.controller;


import he1027_lab5.model.*;
import he1027_lab5.model.ContrastWindowLevel.ChangeContrastMultithreaded;
import he1027_lab5.model.ContrastWindowLevel.SIMDChangeContrast;
import he1027_lab5.view.ContrastAdjustmentView;

public class ContrastAdjustmentController {
    private final ContrastAdjustmentView view;
    private final ImageProcessingModel model;
    private int noOfThreads;

    public ContrastAdjustmentController(ContrastAdjustmentView view, ImageProcessingModel model) {
        this.view = view;
        this.model = model;
        this.noOfThreads = 0;
    }

    public void handleSliderAdjustment() {
        ThreadPool.getThreadPool().execute(() -> {
//            String info = "";
//            long time = System.currentTimeMillis();
            if (noOfThreads > 0) {
                model.processImage(new ChangeContrastMultithreaded(view.getWindowValue(), view.getLevelValue(), noOfThreads));
//                time = System.currentTimeMillis() - time;
//                info += "Java " + noOfThreads + " threads: ";
            }
            else {
                model.processImage(new SIMDChangeContrast(view.getWindowValue(), view.getLevelValue()));
//                time = System.currentTimeMillis() - time;
//                info += "SIMD: ";
            }
//            info += time + " ms";
//            String finalInfo = info;
            javafx.application.Platform.runLater(() -> {
                view.updateFromModel();
                view.updateStatusText("Window: " + view.getWindowValue() +
                        "\t" + "Level: " + view.getLevelValue());
//                System.out.println(finalInfo);
            });
        });
    }

    public void handleMultiThreadSettings() {
        noOfThreads = view.getNoOfThreads();
    }

    public void handleReset() {
        model.clearPreview();
        view.resetValues();
        view.updateFromModel();
        view.updateStatusText("Reset");
    }

    public void handleApply() {
        model.applyProcessing();
        view.updateFromModel();
    }
}
