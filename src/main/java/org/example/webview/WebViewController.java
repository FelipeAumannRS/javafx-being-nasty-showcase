package org.example.webview;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

public class WebViewController {

    @FXML
    private WebView webView;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab tab;

    @FXML
    public void initialize() {
        final WebEngine webEngine = webView.getEngine();

        webEngine.load("https://bindingofisaacrebirth.fandom.com/wiki/Items");

        webEngine.getLoadWorker().stateProperty().addListener((a, b, c) -> {
            if (c.equals(Worker.State.SCHEDULED))
                attachTabWatchdog(webEngine, tab, Duration.seconds(5));
        });
    }

    private void attachTabWatchdog(WebEngine engine, Tab tab, Duration timeout) {
        new Thread(() -> {
            try {
                Thread.sleep((long) timeout.toMillis());

                System.out.println("Running engine state on JavaFX thread");
                Platform.runLater(() -> {
                    try {
                        if (engine.getLoadWorker().getState() == Worker.State.RUNNING) {
                            System.out.println("Removing frozen tab: " + engine.getLocation());
                            tab.getTabPane().getTabs().remove(tab);
                        } else
                            System.out.println("State changed, operation aborted");
                    } catch (Exception ex) {
                        System.out.println("Error executing webView crash watch dog");
                    }
                });
            } catch (Exception ex) {
                System.out.println("Error in watch dog thread");
            }
        }).start();
    }
}
