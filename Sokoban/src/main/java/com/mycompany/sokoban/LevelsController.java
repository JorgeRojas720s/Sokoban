/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.sokoban;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author jitor
 */
public class LevelsController implements Initializable {

    @FXML
    private Button btnLevel2;
    @FXML
    private Button btnLevel5;
    @FXML
    private Button btnLevel1;
    @FXML
    private Button btnLevel4;
    @FXML
    private Button btnLevel3;
    @FXML
    private Button btnLevel6;
    @FXML
    private Button btnBack;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clickLevel2(ActionEvent event) throws IOException {
        App.setRoot("gamelvl2");
    }

    @FXML
    private void clcikLevel5(ActionEvent event) throws IOException {
         App.setRoot("gamelvl5");
    }

    @FXML
    private void clcikLevel1(ActionEvent event) throws IOException {
         App.setRoot("gamelvl1");
    }

    @FXML
    private void clickLevel4(ActionEvent event) throws IOException {
         App.setRoot("gamelvl4");
    }

    @FXML
    private void clickLevel3(ActionEvent event) throws IOException {
         App.setRoot("gamelvl3");
    }

    @FXML
    private void clickLevel6(ActionEvent event) throws IOException {
        App.setRoot("gamelvl6");
    }

    @FXML
    private void switchToPlayersScene(ActionEvent event) throws IOException {
        App.setRoot("player");
    }
    
}
