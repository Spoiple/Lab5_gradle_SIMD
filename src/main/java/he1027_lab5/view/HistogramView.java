package he1027_lab5.view;


import he1027_lab5.controller.HistogramController;
import he1027_lab5.model.ImageProcessingModel;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class HistogramView extends VBox {

    private final ImageProcessingModel model;
    private LineChart<Number,Number> histogram;
    private CheckBox redButton, greenButton, blueButton;
    private ImageView imageView;
    private XYChart.Series<Number, Number> redChannel, greenChannel, blueChannel;
    private Text statusText;


    public HistogramView(ImageProcessingModel model) {
        this.model = model;
        HistogramController controller = new HistogramController(model, this);
        initView();
        addEventHandlers();
        controller.handleUpdate();
        redButton.setSelected(true);
        greenButton.setSelected(true);
        blueButton.setSelected(true);
    }

    private void initView() {
        HBox mainView = new HBox();
        VBox controls = new VBox();
        controls.setAlignment(Pos.CENTER);
        controls.setMinHeight(400);
        controls.setMinWidth(400);
        controls.setMaxWidth(500);
        controls.setSpacing(20);

        HBox radioButtons = new HBox();
        radioButtons.setSpacing(10);
        radioButtons.setAlignment(Pos.CENTER);
        radioButtons.setPrefWidth(controls.getWidth());
        redButton = new CheckBox("Red");
        greenButton = new CheckBox("Green");
        blueButton = new CheckBox("Blue");

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Color value");
        yAxis.setLabel("Frequency");
        xAxis.setTickUnit(15);
        xAxis.setUpperBound(255);
        xAxis.setAutoRanging(false);

        histogram = new LineChart<>(xAxis, yAxis);
        histogram.setCreateSymbols(false);
        histogram.setAnimated(false);

        radioButtons.getChildren().addAll(redButton, greenButton, blueButton);
        controls.getChildren().addAll(new Label("Histogram"), histogram, radioButtons);

        statusText = new Text("");

        redChannel = new XYChart.Series<>();
        redChannel.setName("RED");

        greenChannel = new XYChart.Series<>();
        greenChannel.setName("GREEN");
        blueChannel = new XYChart.Series<>();
        blueChannel.setName("BLUE");

        imageView = new ImageView();
        mainView.getChildren().addAll(controls, imageView);
        imageView.setFitWidth(400);
        imageView.setPreserveRatio(true);

        this.getChildren().addAll(mainView, statusText);
    }

    private void addEventHandlers() {
        redButton.selectedProperty().addListener(observable -> {
            if (redButton.isSelected()) {
                histogram.getData().add(redChannel);
//                histogram.lookup("CHART_COLOR").setStyle("-fx-stroke: RED");
                redChannel.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: RED");
            }
            else histogram.getData().remove(redChannel);
        });
        greenButton.selectedProperty().addListener(observable -> {
            if (greenButton.isSelected()) {
                histogram.getData().add(greenChannel);
                greenChannel.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: GREEN");
            }
            else histogram.getData().remove(greenChannel);
        });
        blueButton.selectedProperty().addListener(observable -> {
            if (blueButton.isSelected()) {
                histogram.getData().add(blueChannel);
                blueChannel.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: BLUE");
            }
            else histogram.getData().remove(blueChannel);
        });
    }

    public void updateFromModel() {
        redChannel.getData().clear();
        greenChannel.getData().clear();
        blueChannel.getData().clear();
        int i = 0;
        for (int value : model.getRedHistogram()) {
            redChannel.getData().add(new XYChart.Data<>(i++, value));
        }
        i = 0;
        for (int value : model.getGreenHistogram()) {
            greenChannel.getData().add(new XYChart.Data<>(i++, value));
        }
        i = 0;
        for (int value : model.getBlueHistogram()) {
            blueChannel.getData().add(new XYChart.Data<>(i++, value));
        }
        imageView.setImage(model.getProcessedImage());
    }

    public void updateStatusText(String info) {
        statusText.setText(info);
    }
}
