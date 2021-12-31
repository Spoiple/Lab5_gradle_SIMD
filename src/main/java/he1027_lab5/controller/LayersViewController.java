package he1027_lab5.controller;

import he1027_lab5.model.ImageProcessingModel;
import he1027_lab5.view.LayersView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class LayersViewController implements PropertyChangeListener {
    private final ImageProcessingModel model;
    private final LayersView view;

    public LayersViewController(ImageProcessingModel model, LayersView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("HEARD YOU");
        view.refresh((List<ImageProcessingModel.ImageProcessingInformation>) evt.getSource());
    }

    public void onRemoveLayer(ImageProcessingModel.ImageProcessingInformation selectedItem) {
        model.removeLayer(selectedItem);
        view.refresh(model.getLayers());
    }
}
