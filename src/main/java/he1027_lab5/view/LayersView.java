package he1027_lab5.view;

import he1027_lab5.controller.LayersViewController;
import he1027_lab5.model.ImageProcessingModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.List;

public class LayersView extends VBox {

    private ObservableList<ImageProcessingModel.ImageProcessingInformation> layers;

    public LayersView(ImageProcessingModel model) {
        LayersViewController controller = new LayersViewController(model, this);
        model.addPropertyChangeListener(controller);
        init(controller);
    }

    private void init(LayersViewController controller) {
        TableView<ImageProcessingModel.ImageProcessingInformation> table = new TableView<>();
        layers = FXCollections.observableArrayList();

        TableColumn<ImageProcessingModel.ImageProcessingInformation, String> nameCol = new TableColumn<>("Process");

        table.getColumns().add(nameCol);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        Button removeLayer = new Button("Remove");
        removeLayer.setOnAction(event -> {
            controller.onRemoveLayer(table.getSelectionModel().getSelectedItem());
        });

        table.setItems(layers);
        this.getChildren().addAll(table, removeLayer);
    }

    public void refresh(List<ImageProcessingModel.ImageProcessingInformation> layers) {
        this.layers.clear();
        this.layers.addAll(layers);
    }
}
