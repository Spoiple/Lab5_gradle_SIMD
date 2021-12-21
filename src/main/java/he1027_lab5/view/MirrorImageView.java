package he1027_lab5.view;

import he1027_lab5.controller.MirrorImageController;
import he1027_lab5.model.ImageProcessingModel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MirrorImageView extends VBox {

    private final ImageProcessingModel model;
    private ImageView originalImageView, previewImageView;
    private CheckBox vertical, horizontal;
    private Button showOriginalButton, applyButton;
    private Text statusText;

    public MirrorImageView(ImageProcessingModel model) {
        this.model = model;
        MirrorImageController controller = new MirrorImageController(model, this);
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

        HBox checkBoxes = new HBox();
        checkBoxes.setAlignment(Pos.CENTER);
        checkBoxes.setSpacing(10);
        vertical = new CheckBox("Vertical");
        horizontal = new CheckBox("Horizontal");
        showOriginalButton = new Button("Show original (hold)");
        applyButton = new Button("Apply");
        checkBoxes.getChildren().addAll(vertical, horizontal);
        controls.getChildren().addAll(checkBoxes, showOriginalButton, applyButton);

        originalImageView = new ImageView();
        originalImageView.setFitHeight(400);
        originalImageView.setFitWidth(400);
        originalImageView.setPreserveRatio(true);

        previewImageView = new ImageView();
        previewImageView.setFitHeight(400);
        previewImageView.setFitWidth(400);
        previewImageView.setPreserveRatio(true);

        StackPane images = new StackPane(originalImageView, previewImageView);
        images.setAlignment(Pos.TOP_CENTER);
        statusText = new Text("");
        mainView.getChildren().addAll(controls, images);
        this.getChildren().addAll(mainView, statusText);
    }

    public void updateStatusText(String info) {
        statusText.setText(info);
    }

    public void showOriginalImage() {
        originalImageView.setVisible(true);
        previewImageView.setVisible(false);
    }

    public void showPreviewImage() {
        originalImageView.setVisible(false);
        previewImageView.setVisible(true);
    }

    private void addEventHandlers(MirrorImageController controller) {
        vertical.setOnAction(a -> controller.handleMirror());
        horizontal.setOnAction(a -> controller.handleMirror());
        showOriginalButton.setOnMousePressed(a -> showOriginalImage());
        showOriginalButton.setOnMouseReleased(a -> showPreviewImage());
        applyButton.setOnAction(a -> controller.handleApply());
    }

    public boolean isVerticalChecked() {
        return vertical.isSelected();
    }

    public boolean isHorizontalChecked() {
        return horizontal.isSelected();
    }

    public void updateFromModel() {
        this.originalImageView.setImage(model.getProcessedImage());
        this.previewImageView.setImage(model.getPreviewImage());
    }

}
