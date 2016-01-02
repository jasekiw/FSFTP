package fsftGui;

import fsftp.FSFTP;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.TextArea;
import java.util.EventListener;


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
        fsftp.FSFTP ftp = new FSFTP();
        //all our panes here
        VBox pane = new VBox();
        Pane menuBar = new Pane();
        Pane toolBar = new Pane();
        Pane connectBar = new Pane();

        scene = new Scene(pane, 1600, 900);

        //set up pane colors
       connectBar.setBackground(new Background(new BackgroundFill(Color.grayRgb(235), CornerRadii.EMPTY, Insets.EMPTY)));
        menuBar.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));
        toolBar.setBackground(new Background(new BackgroundFill(Color.CORAL, CornerRadii.EMPTY, Insets.EMPTY)));

        //Set up our stage
        stage.setTitle("FSFTP");
        stage.setScene(scene);
        stage.show();



        //this makes our panes width always equal to the window width
        menuBar.prefWidthProperty().bind(pane.widthProperty());
      /* we can remove these once we put stuff inside of the panes */   menuBar.setPrefHeight(40);

       toolBar.prefWidthProperty().bind(pane.widthProperty());
       toolBar.setPrefHeight(40);

        connectBar.prefWidthProperty().bind(pane.widthProperty());


        //we are adding all our panes to the parent called "pane"
        pane.getChildren().add(menuBar);
        pane.getChildren().add(toolBar);
        pane.getChildren().add(connectBar);
        HBox menu = new HBox();
        connectBar.getChildren().add(menu);
        menu.setSpacing(10);
        menu.setPadding(new Insets(20));
        menu.setAlignment(Pos.CENTER);


        //labels
        Text host = new Text();
        host.setText("Host:");
        host.setFont(Font.font(20));
        menu.getChildren().add(host);

       TextField hostText = new TextField();
        hostText.setFont(Font.font(15));
        menu.getChildren().add(hostText);

        Text userName = new Text();
        userName.setText("UserName:");
        userName.setFont(Font.font(20));
        menu.getChildren().add(userName);

        TextField userNameText = new TextField();
        userNameText.setFont(Font.font(15));
        menu.getChildren().add(userNameText);


        Text passWord = new Text();
        passWord.setText("Password:");
        passWord.setFont(Font.font(20));
        menu.getChildren().add(passWord);

        TextField passwordText = new TextField();
        passwordText.setFont(Font.font(15));
        menu.getChildren().add(passwordText);

        Text port = new Text();
        port.setText("port:");
        port.setFont(Font.font(20));
        menu.getChildren().add(port);


        TextField portText = new TextField();
        portText.setFont(Font.font(15));
        menu.getChildren().add(portText);

        Button button = new Button("Connect");
        menu.getChildren().add(button);
        button.setOnAction( e -> {
           ftp.setServer(hostText.getText(), userNameText.getText(), passwordText.getText(), portText.getText());
            ftp.connect();

            if(ftp.isConnected()){
                JOptionPane.showMessageDialog(null,"connected!");

            }


        });




    }






}
