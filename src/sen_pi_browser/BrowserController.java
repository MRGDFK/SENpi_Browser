/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SEN_pi_Browser;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author TEAM SEN_pi
 */
public class BrowserController implements Initializable {

    @FXML
    BorderPane browserBP;
    @FXML
    WebView browserWV;
    @FXML
    ImageView stopReloadIV;
    @FXML
    TextField addressBarTF;
    @FXML
    ProgressIndicator progressPI;
    @FXML
    Label statusL;
    //new tools here
    @FXML
    private WebView webView;
    @FXML
    private WebEngine engine;

    private WebHistory history;

    private double webZoom;
    // TabPane root;

    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        browserWV.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                statusL.setText("loading... " + browserWV.getEngine().getLocation());
                stopReloadIV.setImage(new Image(getClass().getResourceAsStream("/images/stoploading.png")));
                progressPI.setVisible(true);
                if (newValue == Worker.State.SUCCEEDED) {
                    addressBarTF.setText(browserWV.getEngine().getLocation());
                    statusL.setText("loaded");
                    progressPI.setVisible(false);
                    stopReloadIV.setImage(new Image(getClass().getResourceAsStream("/images/reload.png")));
                    if (browserBP.getParent() != null) {
                        TabPane tp = (TabPane) browserBP.getParent().getParent();
                        for (Tab tab : tp.getTabs()) {
                            if (tab.getContent() == browserBP) {
                                tab.setText(browserWV.getEngine().getTitle());
                                break;
                            }
                        }
                    }
                }

            }

        });
    }

    @FXML
    private void browserBackButtonAction(ActionEvent event) {
        if (browserWV.getEngine().getHistory().getCurrentIndex() <= 0) {
            return;
        }
        browserWV.getEngine().getHistory().go(-1);
    }

    @FXML
    private void browserForwardButtonAction(ActionEvent event) {
        if ((browserWV.getEngine().getHistory().getCurrentIndex() + 1) >= browserWV.getEngine().getHistory().getEntries().size()) {
            return;
        }
        browserWV.getEngine().getHistory().go(1);
    }

    @FXML
    private void browserGoButtonAction(ActionEvent event) {
        String url = addressBarTF.getText().trim();
        if (url.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No url provided");
            return;
        }
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        browserWV.getEngine().load(url);
        //browserStopReloadIV.setImage(new Image(getClass().getResourceAsStream("/images/stoploading.png")));

    }

    @FXML
    private void browserStopReloadButtonAction(ActionEvent event) {
        if (browserWV.getEngine().getLoadWorker().isRunning()) {
            browserWV.getEngine().getLoadWorker().cancel();
            statusL.setText("loaded");
            progressPI.setVisible(false);
            stopReloadIV.setImage(new Image(getClass().getResourceAsStream("/images/reload.png")));
        } else {
            browserWV.getEngine().reload();
            stopReloadIV.setImage(new Image(getClass().getResourceAsStream("/images/stoploading.png")));
        }

    }

    @FXML
    private void browserHomeButtonAction(ActionEvent event) {
        browserWV.getEngine().loadContent("<html><title>New Tab</title></html>");
        addressBarTF.setText("");
    }

    //new code from here
    @FXML
    void refreshPage(ActionEvent event) {
        browserWV.getEngine().reload();
    }

    @FXML
    void zoomIn(ActionEvent event) {
        webZoom += 0.25;
        webView.setZoom(webZoom);
    }

    @FXML
    void zoomout(ActionEvent event) {
        webZoom -= 0.25;
        webView.setZoom(webZoom);
    }

    @FXML
    void dHistory(ActionEvent event) {
          history = browserWV.getEngine().getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();
        
        for(WebHistory.Entry entry : entries){
            //System.out.println(entry);
            System.out.println(entry.getUrl()+" "+entry.getLastVisitedDate());
    }
}
}
