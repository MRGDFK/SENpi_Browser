/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package SEN_pi_Browser;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;

public class LoginController implements Initializable{

    @FXML
    private Button btndone;

    @FXML
    private TextField txtUName;

    @FXML
    private TextField txtUPass;
    @FXML
    private Hyperlink create;
    
    @FXML
    private Hyperlink login_acc;

    @FXML
    private Button signup_btn;

    @FXML
    private TextField su_email;

    @FXML
    private PasswordField su_pass;

    @FXML
    private TextField su_username;

      @FXML
    private AnchorPane login_form;
      
          @FXML
    private AnchorPane signup_form;

    // Database tools
    private Connection con;
    private PreparedStatement st;
    private ResultSet res;

    public Connection connectDb() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/browser", "root", "");
            return con;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    void login(ActionEvent event) {
        con = connectDb();
        try {
            String sql = "SELECT * FROM logintable WHERE username=? and password = ?";
            st = con.prepareStatement(sql);
            st.setString(1, txtUName.getText());
            st.setString(2, txtUPass.getText());
            res = st.executeQuery();

            if (res.next()) {

                JOptionPane.showMessageDialog(null, "Successful Login!");
                btndone.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("Wellcome.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                
                stage.setScene(scene);
                stage.setTitle("KAWAII Browser");
                stage.show();
                
            } else {
                JOptionPane.showMessageDialog(null, "Wrong UserName or Password");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void signup(ActionEvent event)
    {
        con = connectDb();
        
        try{
            String sql = "INSERT INTO logintable VALUES (?,?,?)";
            
            st = con.prepareStatement(sql);
            st.setString(1, su_email.getText());
            st.setString(2,su_username.getText());
            st.setString(3,su_pass.getText());
            st.execute();
            
            JOptionPane.showMessageDialog(null,"Successfully Created new Account!");
        }catch (Exception e){
            e.printStackTrace();
        }
        
        
    }
    public void changeForm(ActionEvent event){
        if(event.getSource()== login_acc){
            
            signup_form.setVisible(false);
            login_form.setVisible(true);
        }else if(event.getSource() == create)
        {
            signup_form.setVisible(true);
            login_form.setVisible(false);
        }
    }
    @FXML
    private void textfield(javafx.scene.input.MouseEvent event) {
        if (event.getSource() == txtUName) {
            txtUName.setStyle("-fx-background-color:#fff;"
                    + "-fx-border-width:3px;");
            txtUPass.setStyle("-fx-background-color:#eef3ff;"
                    + "-fx-border-width:1px;");
        } else if (event.getSource() == txtUPass) {
            txtUName.setStyle("-fx-background-color:#eef3ff;"
                    + "-fx-border-width:1px;");
            txtUPass.setStyle("-fx-background-color:#fff;"
                    + "-fx-border-width:3px;");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtUName.setStyle("-fx-background-color:#fff;"
                + "-fx-border-width:3px;");

    }

}
