/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SEN_pi_Browser;
 
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
 

/**
 *
 * @author Team SEN_pi
 */
public class SEN_pi_Browser extends Application {
    
    
    Parent login;    
    @Override
    public void start(Stage stage) throws IOException {                
        login = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(login);
        stage.setScene(scene);
        stage.setTitle("KAWAII Browser");
        stage.show();
                
    }
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
