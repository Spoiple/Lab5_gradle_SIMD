package he1027_lab5.view;

import he1027_lab5.controller.BlurController;
import he1027_lab5.controller.InvertColorController;
import he1027_lab5.model.ImageProcessingModel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class BlurView extends VBox {

    private final ImageProcessingModel model;
    private ImageView rawImageView, processedImageView;
    private Button invertButton, showOriginalButton, applyButton;
    private Text statusText;
    private Slider windowSlider;

    public BlurView(ImageProcessingModel model) {
        this.model = model;
        BlurController controller = new BlurController(model, this);
        initView();
        addEventHandlers(controller);
        updateFromModel();
    }

    private void initView() {
        HBox mainView = new HBox();
        VBox controls = new VBox();
        controls.setAlignment(Pos.CENTER);
        controls.setMinHeight(400);
        controls.setMinWidth(200);
        controls.setSpacing(20);

        windowSlider = new Slider(0,41,3);
        windowSlider.setShowTickLabels(true);
//        windowSlider.setMinWidth(200);
        windowSlider.setShowTickMarks(true);
        windowSlider.setPrefWidth(200);


        invertButton = new Button("Blur");
        showOriginalButton = new Button("Show original (hold)");
        applyButton = new Button("Apply");


        controls.getChildren().addAll(windowSlider, invertButton, showOriginalButton, applyButton);


        rawImageView = new ImageView();
        rawImageView.setFitHeight(400);
        rawImageView.setFitWidth(400);
        rawImageView.setPreserveRatio(true);


        processedImageView = new ImageView();
        processedImageView.setFitHeight(400);
        processedImageView.setFitWidth(400);
        processedImageView.setPreserveRatio(true);

        statusText = new Text("");

        StackPane images = new StackPane(rawImageView, processedImageView);
        images.setAlignment(Pos.TOP_CENTER);
        mainView.getChildren().addAll(controls, images);
        this.getChildren().addAll(mainView, statusText);
        showOriginalImage();
    }

    private void addEventHandlers(BlurController controller) {
        invertButton.setOnAction(a -> controller.handleBlur((int) windowSlider.getValue()));
        showOriginalButton.setOnMousePressed(a-> showOriginalImage());
        showOriginalButton.setOnMouseReleased(a-> showProcessedImage());
        applyButton.setOnAction(a->controller.handleApply());
    }

    public void updateFromModel() {
        this.rawImageView.setImage(model.getProcessedImage());
        this.processedImageView.setImage(model.getPreviewImage());
    }

    public void showOriginalImage() {
        rawImageView.setVisible(true);
        processedImageView.setVisible(false);
    }

    public void showProcessedImage() {
        rawImageView.setVisible(false);
        processedImageView.setVisible(true);
    }

    public void updateStatusText(String info) {
        statusText.setText(info);
    }
}
