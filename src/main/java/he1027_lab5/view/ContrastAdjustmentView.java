package he1027_lab5.view;

import he1027_lab5.controller.ContrastAdjustmentController;
import he1027_lab5.model.ImageProcessingModel;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ContrastAdjustmentView extends VBox {

    private final ImageProcessingModel model;
    private ImageView imageView;
    private Slider windowSlider, levelSlider;
    private Button resetButton, applyButton;
    private Text statusText;
    private ComboBox<Integer> noOfThreadsComboBox;

    public ContrastAdjustmentView(ImageProcessingModel model) {
        this.model = model;
        initView();
        addEventHandlers(new ContrastAdjustmentController(this, model));
        updateFromModel();
    }

    private void initView() {

        HBox mainView = new HBox();
        Label headerLabel = new Label("Adjust contrast");

        windowSlider = new Slider(0,255,255);
        windowSlider.setShowTickLabels(true);
        windowSlider.setMinWidth(200);
        windowSlider.setShowTickMarks(true);
        windowSlider.setPrefWidth(200);
        Label windowSliderLabel = new Label("Window");
        windowSliderLabel.setLabelFor(windowSlider);


        levelSlider = new Slider(0,255, 0);
        levelSlider.setShowTickLabels(true);
        levelSlider.setMinWidth(200);
        levelSlider.setShowTickMarks(true);
        levelSlider.setPrefWidth(200);
        Label levelSliderLabel = new Label("Level");
        levelSliderLabel.setLabelFor(levelSlider);

        resetButton = new Button("Reset");
        applyButton = new Button("Apply");
        Label multiThreadLabel = new Label("Multithreading");
        noOfThreadsComboBox = new ComboBox<>();
        noOfThreadsComboBox.setPromptText("Extra threads");
        noOfThreadsComboBox.getItems().add(0);
        noOfThreadsComboBox.getItems().add(1);
        noOfThreadsComboBox.getItems().add(2);
        noOfThreadsComboBox.getItems().add(4);
        VBox multithreading = new VBox(multiThreadLabel, noOfThreadsComboBox);

        VBox window = new VBox(windowSliderLabel, windowSlider);
        VBox level = new VBox(levelSliderLabel, levelSlider);
        VBox controls = new VBox(headerLabel, window, level, resetButton, applyButton, multithreading);
        controls.setAlignment(Pos.TOP_CENTER);
        controls.setMinHeight(400);
        controls.setSpacing(20);

        imageView = new ImageView();
        imageView.setFitHeight(400);
        imageView.setFitWidth(400);
        imageView.setPreserveRatio(true);


        statusText = new Text("");

        mainView.getChildren().addAll(controls, imageView);
        this.getChildren().addAll(mainView, statusText);
    }

    private void addEventHandlers(ContrastAdjustmentController controller) {
        EventHandler<Event> contrastChangeHandler = Event -> controller.handleSliderAdjustment();
        windowSlider.setOnMouseDragged(contrastChangeHandler);
        windowSlider.setOnMouseClicked(contrastChangeHandler);
        windowSlider.setOnKeyPressed(contrastChangeHandler);
        levelSlider.setOnMouseDragged(contrastChangeHandler);
        levelSlider.setOnMouseClicked(contrastChangeHandler);
        levelSlider.setOnKeyPressed(contrastChangeHandler);
        resetButton.setOnMouseClicked(a -> controller.handleReset());
        applyButton.setOnMouseClicked(a -> controller.handleApply());
        noOfThreadsComboBox.setOnAction(a -> controller.handleMultiThreadSettings());
    }

    public void updateFromModel() {
        this.imageView.setImage(model.getPreviewImage());
    }

    public int getWindowValue() {
        return (int) windowSlider.getValue();
    }

    public int getLevelValue() {
        return (int) levelSlider.getValue();
    }

    public void resetValues() {
        windowSlider.setValue(255);
        levelSlider.setValue(0);
    }

    public int getNoOfThreads() {
        return noOfThreadsComboBox.getValue();
    }

    public void updateStatusText(String info) {
        statusText.setText(info);
    }
}
