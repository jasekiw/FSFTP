package fsftGui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by Jason on 1/1/2016.
 */
public class FXFSFTPGUI extends Application {

    private Scene scene;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        scene = new Scene(pane, 500, 400);
        stage.setTitle("FSFTP");
        stage.setScene(scene);
        stage.show();

    }
}
