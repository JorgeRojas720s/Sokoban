/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.sokoban;

import java.io.IOException;
import static java.lang.System.exit;
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
public class MenuController implements Initializable {

    @FXML
    private Button btnNewGame;
    @FXML
    private Button btnCredits;
    @FXML
    private Button btnExit;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        
    }    
    
  
    @FXML
    private void switchToPlayerScene(ActionEvent event) throws IOException {
        App.setRoot("player");    
    }


    @FXML
    private void exitGame(ActionEvent event) {
        exit(0);
    }

    @FXML
    private void switchToCreditsScene(ActionEvent event) throws IOException {
        App.setRoot("credits");  
    }



}
