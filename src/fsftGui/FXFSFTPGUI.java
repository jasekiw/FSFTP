package fsftGui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.shape.Box;

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
        scene = new Scene(pane, 1600, 900);
        stage.setTitle("FSFTP");
        stage.setScene(scene);
        stage.show();
        Button button = new Button("Click Me!");
        pane.getChildren().add(button);
        Box myBox = new Box(100, 200, 100);
        pane.getChildren().add(myBox);
    }


}
