package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.webview.WebViewController;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        WebViewController webViewController = new WebViewController();
        stage.setScene(new Scene(webViewController.tabPane));
        webViewController.initialize();
        stage.show();
    }
}