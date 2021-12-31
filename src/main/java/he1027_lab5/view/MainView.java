package he1027_lab5.view;

import he1027_lab5.controller.LayersViewController;
import he1027_lab5.controller.MainViewController;
import he1027_lab5.model.ImageProcessingModel;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class MainView extends VBox {

    private final ImageProcessingModel model;
    private final Menu fileMenu, generateMenu;
    private HBox editorView;
    private LayersView layersView;

    public MainView(ImageProcessingModel model) {
        this.model = model;
        fileMenu = new Menu("File");
        generateMenu = new Menu("Generate");
        initView();
        generateMenu.setDisable(true);
    }

    public void updateLayerView(List<ImageProcessingModel.ImageProcessingInformation> info) {
        layersView.refresh(info);
    }

    private void initView() {
        MenuBar menuBar = new MenuBar();
        MainViewController mainViewController = new MainViewController(model, this);

        MenuItem importFile = new MenuItem("Import image");
        importFile.setOnAction(a -> mainViewController.handleImport());

        MenuItem saveImage = new MenuItem("Save image");
        saveImage.setDisable(true);
        saveImage.setOnAction(a -> mainViewController.handleSave());

        editorView = new HBox();
        layersView = new LayersView(model);
        editorView.getChildren().add(layersView);

        fileMenu.getItems().add(importFile);
        fileMenu.getItems().add(saveImage);

        MenuItem invertColors = new MenuItem("Invert Colors");
        invertColors.setOnAction(a -> {
            clearEditor();
            editorView.getChildren().add(0, new InvertColorView(model));
//            clearView();
//            this.getChildren().add(new InvertColorView(model));
        });

        MenuItem blurImage = new MenuItem("Blur Image");
        blurImage.setOnAction(a -> {
            clearEditor();
            editorView.getChildren().add(0, new BlurView(model));
//            clearView();
//            this.getChildren().add(new BlurView(model));
        });

        MenuItem adjustContras = new MenuItem("Adjust contrast");
        adjustContras.setOnAction(a -> {
            clearEditor();
            editorView.getChildren().add(0, new ContrastAdjustmentView(model));
//            clearView();
//            this.getChildren().add(new ContrastAdjustmentView(model));
        });

        MenuItem mirrorImage = new MenuItem("Mirror image");
        mirrorImage.setOnAction(a -> {
            clearEditor();
            editorView.getChildren().add(0, new MirrorImageView(model));
//            clearView();
//            this.getChildren().add(new MirrorImageView(model));
        });

        MenuItem histogram = new MenuItem("Histogram");
        histogram.setOnAction(a -> {
            clearEditor();
            editorView.getChildren().add(0, new HistogramView(model));
//            clearView();
//            this.getChildren().add(new HistogramView(model));
        });

        MenuItem reset = new MenuItem("Reset");
        reset.setOnAction(a -> {
            model.resetModel();
            clearView();
        });

        generateMenu.getItems().add(invertColors);
        generateMenu.getItems().add(blurImage);
        generateMenu.getItems().add(adjustContras);
        generateMenu.getItems().add(mirrorImage);
        generateMenu.getItems().add(histogram);
        generateMenu.getItems().add(reset);

        menuBar.getMenus().addAll(fileMenu, generateMenu);

        this.getChildren().addAll(menuBar, editorView);
    }

    public void setEnableEditing(boolean value) {
        generateMenu.setDisable(!value);
        fileMenu.getItems().get(1).setDisable(false);
    }

    private void clearView() {
        model.clearPreview();
        try {
            this.getChildren().remove(1);
        } catch (Exception ignored) {
        }
    }

    private void clearEditor() {
        model.clearPreview();
        try {
            if (editorView.getChildren().size() > 1)
                editorView.getChildren().remove(0);
        } catch (Exception ignored) {
        }
    }

    public void showAlert(String message) {
        Alert info = new Alert(Alert.AlertType.ERROR, message);
        info.show();
    }
}