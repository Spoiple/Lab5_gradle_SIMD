package he1027_lab5.controller;

import he1027_lab5.model.ImageProcessingModel;
import he1027_lab5.view.MainView;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Popup;

import java.io.File;

public class MainViewController {

    private final ImageProcessingModel model;
    private final MainView view;

    public MainViewController(ImageProcessingModel model, MainView view) {
        this.model = model;
        this.view  = view;
    }

    public void handleImport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png", "*.jpg")
        );
        File file = fileChooser.showOpenDialog(new Popup());
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            model.setImage(image);
            view.setEnableEditing(true);
        }
    }

    public void handleSave() throws NullPointerException {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("PNG", "*.png"),
//                new FileChooser.ExtensionFilter("JPG", "*.jpg")
//        );
//        File file = fileChooser.showSaveDialog(new Popup());
//        if (file != null) {
//            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(model.getProcessedImage(), null);
//            try {
//                ImageIO.write(bufferedImage, fileChooser.getSelectedExtensionFilter().getExtensions().get(0).replace("*.", ""), file);
//            } catch (Exception e) {
//                view.showAlert(e.getMessage());
//            }
//        }
    }
}
