package he1027_lab5;

import he1027_lab5.model.ImageProcessingModel;
import he1027_lab5.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {

        // System.out.println(Integer.toBinaryString(matrix[x][y])); // skriver ut binärt tal.
//        Image image = new Image(this.getClass().getResource("MVC2.PNG").toString());
        MainView mainView = new MainView(new ImageProcessingModel());
//        mainView.setMinWidth(600);
        Scene scene = new Scene(mainView, 800, 550);
        stage.setOnCloseRequest(event -> ThreadPool.shutDown());
        stage.setMinWidth(800);
        stage.setMinHeight(550);
        stage.setTitle("PhotoEditor 2021!");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}