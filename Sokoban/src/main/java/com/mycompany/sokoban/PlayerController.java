/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.sokoban;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Game;
import modelDAO.GameDAO;

/**
 * FXML Controller class
 *
 * @author jitor
 */
public class PlayerController implements Initializable {

    GameDAO gameDAO = new GameDAO();
    
    public int opt;
    public Short id;
    public String steps;
    public String name;
    public String level;
    public String matrix;

    @FXML
    private Button btnBack;
    @FXML
    private TextField txtPlayerName;
    
    public static PlayerController instance;
    
    @FXML
    private Button btnLevels;
    @FXML
    private Button btnNewGame;
    @FXML
    private Button btnContinue;
    @FXML
    private TableColumn<Game, String> colmName;
    @FXML
    private TableColumn<Game, String> colmLevel;
    @FXML
    private TableColumn<Game, String> colmSteps;
    @FXML
    private TableView<Game> tbvPlayers;
    @FXML
    private Button btnDelete;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        instance = this;
        
        btnContinue.setDisable(true);
        btnDelete.setDisable(true);
        
        fillTable();
        
        updateTableGames();
    }    
    
    public void fillTable(){

        colmName.setCellValueFactory(new PropertyValueFactory<>("gamPlayername"));
        colmLevel.setCellValueFactory(new PropertyValueFactory<>("gamLevel"));
        colmSteps.setCellValueFactory(new PropertyValueFactory<>("gamSteps"));

    }
    
    private void showAlert(String message) {
       
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }  

    public int getOpt() {
        return opt;
    }

    public Short getId(){
        return id;  
    }
    
    public String getName(){
        
        return txtPlayerName.getText();  
    }

    public String getSteps() {
        return steps;
    }
    
    public String getLevel() {
        return level;
    }

    public String getMatrix() {
        return matrix;
    }

    @FXML
    private void switchToMenuScene(ActionEvent event) throws IOException {
        App.setRoot("menu");
    }

    @FXML
    private void switchToSceneLevels(ActionEvent event) throws IOException {
        
        txtPlayerName.setText("Administrator");
        
        App.setRoot("levels");
    }

    @FXML
    private void clickNewGame(ActionEvent event) throws IOException {

        String aux = txtPlayerName.getText();
        
        if(aux.isEmpty()){
            showAlert("Please insert a name");    
        }else{
            opt = 1;
            id = null;
            App.setRoot("gamelvl1"); 
        }
    }

    @FXML
    private void clickContinue(ActionEvent event) throws IOException {
        
         String aux = txtPlayerName.getText();
         
        if(aux.isEmpty()){
            showAlert("Please select a player");    
        }else{
            loadLevel1();
            loadLevel2();
            loadLevel3();
            loadLevel4();
            loadLevel5();
            loadLevel6();
         }
    }
    
    private void loadLevel1() throws IOException{
        int aux = Integer.parseInt(level);
        if(aux == 1){
            opt = 2;
            App.setRoot("gamelvl1");
        } 
    }
    
    private void loadLevel2() throws IOException{
        int aux = Integer.parseInt(level);
        if(aux == 2){
            opt = 3;
            App.setRoot("gamelvl2");
        }
    }
    
    private void loadLevel3() throws IOException{
        int aux = Integer.parseInt(level);
        if(aux == 3){
            opt = 4;
            App.setRoot("gamelvl3");
        }
    }
      
    private void loadLevel4() throws IOException{
        int aux = Integer.parseInt(level);
        if(aux == 4){
            opt = 5;
            App.setRoot("gamelvl4");
        }
    }
        
    private void loadLevel5() throws IOException{
        int aux = Integer.parseInt(level);
        if(aux == 5){
            opt = 6;
            App.setRoot("gamelvl5");
        }          
    }
    
    private void loadLevel6() throws IOException{
        int aux = Integer.parseInt(level);
        if(aux == 6){
            opt = 7;
            App.setRoot("gamelvl6");
        }          
    }
    
    private void deleteGame(){
  
        gameDAO.deleteGame(id);  
        txtPlayerName.setText(null);
    }
    
    private void updateTableGames() {
        
    GameDAO gameDAO = new GameDAO();
    
    List<Game> listGames = gameDAO.getGames();
    gameDAO.closeConnection();
    
    ObservableList<Game> gamesObservableList = FXCollections.observableArrayList(listGames);
    
    tbvPlayers.setItems(gamesObservableList);
}

    @FXML
    private void getPlayerGame(MouseEvent event) {
        
        int index = tbvPlayers.getSelectionModel().getFocusedIndex();
        Game temp = tbvPlayers.getItems().get(index);
        String name1 = gameDAO.getGameById(temp.getGamId().toString()).getGamPlayername();
        Short idPlayer = gameDAO.getGameById(temp.getGamId().toString()).getGamId();
        String matrixPlayer = gameDAO.getGameById(temp.getGamId().toString()).getGamGame();
        String levelPlayer = gameDAO.getGameById(temp.getGamId().toString()).getGamLevel();
        String stepsPlayer = gameDAO.getGameById(temp.getGamId().toString()).getGamSteps();

        id = idPlayer;
        txtPlayerName.setText(String.valueOf(name1));
        matrix = matrixPlayer;
        level = levelPlayer;
        steps = stepsPlayer;
        
        btnContinue.setDisable(false);
        btnDelete.setDisable(false);
    }

    @FXML
    private void clcikDelete(ActionEvent event) {
        
        btnContinue.setDisable(true);
        btnDelete.setDisable(true);
        deleteGame();
        updateTableGames();
    }

}
