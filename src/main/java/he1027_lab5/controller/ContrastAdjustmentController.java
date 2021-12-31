package he1027_lab5.controller;


import he1027_lab5.ThreadPool;
import he1027_lab5.model.*;
import he1027_lab5.model.ContrastWindowLevel.SIMDChangeContrast;
import he1027_lab5.view.ContrastAdjustmentView;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static he1027_lab5.model.ContrastWindowLevel.ChangeContrastMultithreaded.SetChangeContrastMultithreaded;

public class ContrastAdjustmentController {
    private final ContrastAdjustmentView view;
    private final ImageProcessingModel model;
    private int noOfThreads;
    private final ThreadPoolExecutor tpe;

    public ContrastAdjustmentController(ContrastAdjustmentView view, ImageProcessingModel model) {
        this.view = view;
        this.model = model;
        this.noOfThreads = 0;
        tpe = new ThreadPoolExecutor(1,
                1,
                120,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(200));

    }

    public void handleSliderAdjustment() {
        ThreadPool.getThreadPool().execute(() -> {
//            String info = "";
//            long time = System.currentTimeMillis();
            int w = view.getWindowValue();
            int l = view.getLevelValue();
            String info = "Contrast, Window: " + w + ", level: " + l;
            if (noOfThreads > 0) {
                model.processImage(info, SetChangeContrastMultithreaded(w, l, noOfThreads));
//                time = System.currentTimeMillis() - time;
//                info += "Java " + noOfThreads + " threads: ";
            }
            else {
                model.processImage(info, new SIMDChangeContrast(w, l));
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
