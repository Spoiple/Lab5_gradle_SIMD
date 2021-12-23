package he1027_lab5.view;

import he1027_lab5.controller.MainViewController;
import he1027_lab5.model.ImageProcessingModel;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

public class MainView extends VBox {

    private final ImageProcessingModel model;
    private final Menu fileMenu, generateMenu;

    public MainView(ImageProcessingModel model) {
        this.model = model;
        fileMenu = new Menu("File");
        generateMenu = new Menu("Generate");
        initView();
        generateMenu.setDisable(true);
    }

    private void initView() {
        MenuBar menuBar = new MenuBar();
        MainViewController mainViewController = new MainViewController(model, this);

        MenuItem importFile = new MenuItem("Import image");
        importFile.setOnAction(a -> mainViewController.handleImport());

        MenuItem saveImage = new MenuItem("Save image");
        saveImage.setDisable(true);
        saveImage.setOnAction(a -> mainViewController.handleSave());

        fileMenu.getItems().add(importFile);
        fileMenu.getItems().add(saveImage);

        MenuItem invertColors = new MenuItem("Invert Colors");
        invertColors.setOnAction(a -> {
            clearView();
            this.getChildren().add(new InvertColorView(model));
        });

        MenuItem blurImage = new MenuItem("Blur Image");
        blurImage.setOnAction(a -> {
            clearView();
            this.getChildren().add(new BlurView(model));
        });

        MenuItem adjustContras = new MenuItem("Adjust contrast");
        adjustContras.setOnAction(a -> {
            clearView();
            this.getChildren().add(new ContrastAdjustmentView(model));
        });

        MenuItem mirrorImage = new MenuItem("Mirror image");
        mirrorImage.setOnAction(a -> {
            clearView();
            this.getChildren().add(new MirrorImageView(model));
        });

        MenuItem histogram = new MenuItem("Histogram");
        histogram.setOnAction(a -> {
            clearView();
            this.getChildren().add(new HistogramView(model));
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

        this.getChildren().addAll(menuBar);
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

    public void showAlert(String message) {
        Alert info = new Alert(Alert.AlertType.ERROR, message);
        info.show();
    }
}