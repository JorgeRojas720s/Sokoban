/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.sokoban;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.Game;
import modelDAO.GameDAO;
import modelDTO.GameDTO;
/**
 * FXML Controller class
 *
 * @author jitor
 */
public class Gamelvl1 implements Initializable {
    
    String name = PlayerController.instance.getName();
    String playerMatrix = PlayerController.instance.getMatrix();
    String playerSteps = PlayerController.instance.getSteps();
    Short id = PlayerController.instance.getId();
    int option = PlayerController.instance.getOpt();
    
    
    Game game;
    
    GameDAO gameDAO;

    private Label[][] labels;
    
    private char[][] previousMatrix = new char[11][19];
    private char[][] startLevel = new char[11][19];
    private char[][] level1 = {
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#','#', '#', '#', '#', '#', '#', '#', '#', '#'},
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#','#', '#', '#', '#', '#', '#', '#', '#', '#'},
            {'#', '#', ' ', ' ', '#', '#', ' ', ' ', '#', '#','#', '#', ' ', ' ', ' ', ' ', ' ', '+', '#'},
            {'#', '#', ' ', ' ', '#', '#', ' ', ' ', '#', '#','#', '#', ' ', '#', '#', ' ', 'O', ' ', '#'},
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#','#', '#', ' ', '#', '#', ' ', ' ', '#', '#'},
            {'#', ' ', '#', '#', '#', '#', '#', '#', ' ', '#','#', '#', '+', '#', '#', '#', ' ', 'P', '#'},
            {'#', '#', ' ', '#', '#', '#', '#', ' ', '#', '#','#', '#', ' ', '#', '#', ' ', ' ', '#', '#'},
            {'#', '#', '#', ' ', '#', '#', ' ', '#', '#', '#','#', ' ', ' ', '#', '#', 'O', ' ', '#', '#'},
            {'#', '#', '#', '#', ' ', ' ', '#', '#', '#', '#','#', ' ', ' ', ' ', ' ', ' ', ' ', '#', '#'},
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#','#', ' ', ' ', ' ', '#', '#', '#', '#', '#'},
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#','#', '#', '#', '#', '#', '#', '#', '#', '#'},
    };

    private int playerRow;
    private int playerColumn;
    private int steps = 35;
    private int counter = 0;
    private boolean gameOver;
    
    private Image imageWalls;
    private Image imageBook;
    private Image imageFinnFrente;
    private Image imageGoal;
    private Image imageFinnInGoal;
    private Image ImageBookInGoal;
    private Timeline timeline;
    
    @FXML
    private GridPane gridPane;
    @FXML
    private AnchorPane paneLevelCompleted;
    @FXML
    private Button btnNext;
    @FXML
    private Button btnOneStepBack;
    @FXML
    private Button btnBackToMenu;
    @FXML
    private Button btnRestart;
    @FXML
    private Label labSteps;
    @FXML
    private Button btnSave;
    @FXML
    private Label labPlayerName;
    @FXML
    private AnchorPane paneRestartLevel;
    @FXML
    private Button btnRestartLevel;
    @FXML
    private Label labNumberLevel;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        saveStartLevel();

        loadGame();
 
        labPlayerName.setText(name);
        initializeImages();
        btnOneStepBack.setDisable(true);

        labSteps.setText(String.valueOf(steps));
        paneLevelCompleted.setVisible(false);
        paneRestartLevel.setVisible(false);
        gridPane.setFocusTraversable(true);
        

        updateMap();
    }    

    
    private void initializeLabels() {
        labels = new Label[11][19];
        
        for (int row = 0; row < 11; row++) {
            for (int column = 0; column < 19; column++) {
                Label label = new Label();
                label.setPrefWidth(71);
                label.setPrefHeight(59);
                labels[row][column] = label;
            }
        }
    }
    
    private void initializeImages(){
        
        imageWalls = new Image(getClass().getResource("/muros.png").toExternalForm());
        imageBook = new Image(getClass().getResource("/libro.jpg").toExternalForm());
        imageFinnFrente = new Image(getClass().getResource("/Finn.png").toExternalForm());
        imageGoal = new Image(getClass().getResource("/meta.png").toExternalForm());
        imageFinnInGoal = new Image(getClass().getResource("/FinnInGoal.jpg").toExternalForm());
        ImageBookInGoal = new Image(getClass().getResource("/BookInGoal.jpg").toExternalForm());  
    }
    
    private void showAlert(String message) {
       
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Locked");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }  
     
    private void saveStartLevel(){
         
         for(int row = 0; row < 11; row++){
             for(int column = 0; column < 19; column ++){
                 startLevel[row][column] = level1[row][column];   
            }      
        }    
    }
    
    private void savePreviousMove(){
        for (int row = 0; row < 11; row++) {
            for (int column = 0; column < 19; column++) {
                previousMatrix[row][column] = level1[row][column];
            }
        }
    }

    private void updateMap(){
        initializeLabels();
        gridPane.getChildren().clear();
        
        for(int row = 0; row < 11; row++){
            for(int column = 0; column < 19;column++){
     
                Label label = labels[row][column];
                
                setImageToWalls(label,row,column);
                setImageToPlayer(label,row,column);
                setImageToBoxes(label,row,column);
                setImageToGoal(label,row,column);
                setImageToBookInGoal(label,row,column);
                setImageToPlayerInGoal(label,row,column);
                
                gridPane.add(label, column, row);
            }
        }
        gameLocked();
        finishedLevel();   
    }

    private void setImageToWalls(Label label, int row, int column) {
        if (level1[row][column] == '#') {
            label.setGraphic(new ImageView(imageWalls));
        }
    }

    private void setImageToBoxes(Label label, int row, int column) {
        if (level1[row][column] == 'O') {
            label.setGraphic(new ImageView(imageBook));
        }
    }

    private void setImageToPlayer(Label label, int row, int column) {
        if (level1[row][column] == 'P') {
            label.setGraphic(new ImageView(imageFinnFrente));
        }
    }

    private void setImageToGoal(Label label, int row, int column) {
        if (level1[row][column] == '+') {
            label.setGraphic(new ImageView(imageGoal));
        }
    }

    private void setImageToBookInGoal(Label label, int row, int column) {
        if (level1[row][column] == 'M') {
            label.setGraphic(new ImageView(ImageBookInGoal));
        }
    }

    private void setImageToPlayerInGoal(Label label, int row, int column) {
        if (level1[row][column] == 'F') {
            label.setGraphic(new ImageView(imageFinnInGoal));
        }
    }

    private void searchPlayer(){
        
        for (int row = 0; row < 11; row++) {
            for (int column = 0; column < 19; column++) {
                System.out.print(level1[row][column] + ",");
                if (level1[row][column] == 'P' || level1[row][column] == 'F') {    
                    playerRow = row;
                    playerColumn = column;
                    break;
                }    
            }       
            System.out.println(" ");
        }
    }

    @FXML
    private void movePlayer(KeyEvent event) {
        
        if(!gameOver){
            KeyCode keyPressed = event.getCode();
            
            movesAreOver();
                  
            searchPlayer();
        
        switch (keyPressed) {
            case UP:
                System.out.println("Arriba");
                counter ++;
                logicMovePlayer(playerRow -1, playerColumn, -1,0);
                updateMap();
                break;
            case DOWN:
                System.out.println("Abajo");
                counter ++;
                logicMovePlayer(playerRow +1, playerColumn,1,0);
                updateMap();
                break;
            case LEFT:
                System.out.println("Izqierd");
                counter ++;
                logicMovePlayer(playerRow, playerColumn -1,0,-1);
                updateMap();
                break;
            case RIGHT:
                System.out.println("Derecha");
                counter ++;
                logicMovePlayer(playerRow , playerColumn +1, 0,1);
                updateMap();
                break;
            default: 
                break;
            }              
        }
    }
    
    private void movesAreOver(){
        
        if(steps == 1){
            btnSave.setDisable(true);
            paneRestartLevel.setVisible(true);
            btnRestart.setDisable(true);
            gameOver = true;
        }
    }
    
    public void finishedLevel(){
        
        if((level1[2][17] == 'M') && (level1[5][12] == 'M')){    
            btnSave.setDisable(true);
            btnRestart.setDisable(true);
            System.out.println("Profe si ve esto me devuelve el punto de la Tarea :) jajajaj");
            paneLevelCompleted.setVisible(true);  
            gameOver = true;
        }
    }
    
    public void logicMovePlayer(int newRow, int newColumn, int aux1, int aux2){
        
        playerLeavingTheGoal(newRow,newColumn);
        playerMove(newRow,newColumn);
        checkIfBoxIsPushed(newRow,newColumn,aux1,aux2); 
        playerEnteringTheGoal(newRow,newColumn); 
        counterSteps(newRow, newColumn);
    }
    
    private void playerLeavingTheGoal(int newRow, int newColumn){
        
        if(level1[newRow][newColumn] == ' ' && level1[playerRow][playerColumn] == 'F'){
   
            savePreviousMove();
            level1[newRow][newColumn] = 'P';
            level1[playerRow][playerColumn] = '+';     
        }
    }
    
    public void playerMove(int newRow, int newColumn){
        
        if(level1[newRow][newColumn] == ' '){ 
            
            savePreviousMove();    
            btnOneStepBack.setDisable(false);
            
            level1[newRow][newColumn] = 'P';
            level1[playerRow][playerColumn] = ' '; 
        } 
    }
    
    public void playerEnteringTheGoal(int newRow, int newColumn){
        
        if((level1[newRow][newColumn] == '+') && (level1[playerRow][playerColumn] == 'F')){
            savePreviousMove();
            btnOneStepBack.setDisable(false);
            level1[newRow][newColumn] = 'F';
            level1[playerRow][playerColumn] = '+'; 
        }
        else if(level1[newRow][newColumn] == '+'){
            
            savePreviousMove();
            btnOneStepBack.setDisable(false);
            level1[newRow][newColumn] = 'F';
            level1[playerRow][playerColumn] = ' ';    
        }
    }
        
    public void checkIfBoxIsPushed(int newRow, int newColumn,int aux1,int aux2){
        
        if((level1[newRow][newColumn] == 'O' || level1[newRow][newColumn] == 'M') && level1[newRow + aux1][newColumn + aux2] != '#' 
            && level1[newRow + aux1][newColumn + aux2] != 'O' && level1[newRow + aux1][newColumn + aux2] != 'M'){
            
            savePreviousMove();
            
            btnOneStepBack.setDisable(false);
            
            pushOutGoalBox(newRow,newColumn,aux1,aux2);
            
            pushBox(newRow,newColumn,aux1,aux2);

            pushBoxToTheGoal(newRow,newColumn,aux1,aux2);
            
        }
    }
    
    private void pushOutGoalBox(int newRow, int newColumn, int aux1, int aux2){

        if(level1[newRow + aux1][newColumn + aux2] == '+' && level1[newRow][newColumn] == 'M' &&  level1[playerRow][playerColumn] == 'F'){
                 
            savePreviousMove();
            level1[newRow + aux1][newColumn + aux2] = 'M';
            level1[newRow][newColumn] = 'F';
            level1[playerRow][playerColumn] = '+';
        }else if(level1[newRow + aux1][newColumn + aux2] == ' ' && level1[newRow][newColumn] == 'M' &&  level1[playerRow][playerColumn] == 'F'){  
            
            savePreviousMove();
            level1[newRow + aux1][newColumn + aux2] = 'O';
            level1[newRow][newColumn] = 'F';
            level1[playerRow][playerColumn] = '+';          
        }else if(level1[newRow + aux1][newColumn + aux2] == '+' && level1[newRow][newColumn] == 'M'){
            
            savePreviousMove();
            level1[newRow + aux1][newColumn + aux2] = 'M';
            level1[newRow][newColumn] = 'F';
            level1[playerRow][playerColumn] = ' '; 
        }else if(level1[newRow][newColumn] == 'M'){   
            
            savePreviousMove();
            level1[newRow + aux1][newColumn + aux2] = 'O';
            level1[newRow][newColumn] = 'F';
            level1[playerRow][playerColumn] = ' ';
        }
    }
    
    private void pushBox(int newRow, int newColumn, int aux1, int aux2){
        
        if(level1[newRow + aux1][newColumn + aux2] == ' ' && level1[playerRow][playerColumn] == 'F'){
            
            savePreviousMove();
            level1[newRow + aux1][newColumn + aux2] = 'O';
            level1[newRow][newColumn] = 'P';
            level1[playerRow][playerColumn] = '+';
        }else if(level1[newRow + aux1][newColumn + aux2] == ' '){
            
            savePreviousMove();
            level1[newRow + aux1][newColumn + aux2] = 'O';
            level1[newRow][newColumn] = 'P';
            level1[playerRow][playerColumn] = ' ';  
        }      
            
    }
    
    private void pushBoxToTheGoal(int newRow, int newColumn, int aux1, int aux2){
        
        if(level1[newRow + aux1][newColumn + aux2] == '+'){
                
            savePreviousMove();
            level1[newRow + aux1][newColumn + aux2] = 'M';
            level1[newRow][newColumn] = 'P';
            level1[playerRow][playerColumn] = ' ';       
        }
    }
    
    private void counterSteps(int newRow, int newColumn){
        
        if(level1[newRow][newColumn] != '#' &&  level1[newRow][newColumn] != 'O' &&  level1[newRow][newColumn] != 'M'){
            steps--;
            labSteps.setText(String.valueOf(steps));
        }
    }
    
    private void restartLevel(){
        
        for (int row = 0; row < 11; row++) {
            for (int column = 0; column < 19; column++) {
                level1[row][column] = startLevel[row][column];
            }
        }
        updateMap();  
    }
    
    private void gameLocked(){
        
        if(level1[2][12] == 'O' || level1[6][15] == 'O' || level1[8][11] == 'O' || 
        level1[9][12] == 'O' || level1[8][16] == 'O' || level1[3][17] == 'O'){
            
            showAlert("Oops, restart the level or take a step back");
        }      
    }
    
    public static String matrixToString(char[][] matriz) {
        
        StringBuilder sb = new StringBuilder();

        for (int row = 0; row < 11; row++) {
            for (int column = 0; column < 19; column++) {
                sb.append(matriz[row][column]);

            }
        }
        return sb.toString();
    }
    
    public static char[][] stringToMatrix(String str, int rows, int columns) {
        char[][] matriz = new char[rows][columns];
        int index = 0;

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                matriz[row][column] = str.charAt(index++);
            }
        }

        return matriz;
    }

    private void saveGame(){
        
        game = new Game();
  
        game.setGamPlayername(labPlayerName.getText());
        
        String matrixlevel1 = matrixToString(level1);
     
        game.setGamGame(matrixlevel1);
        
        game.setGamLevel(Short.valueOf(labNumberLevel.getText()));
        
        game.setGamSteps(Short.valueOf(labSteps.getText()));
        
        GameDTO gameDTO = new GameDTO(game);
        
        gameDAO = new GameDAO();
        
        gameDAO.saveGame(gameDTO);
        
        gameDAO.closeConnection();
        
        System.out.println("Se guardo el juego");
    }
    
    public void updateGame(){
        
        String idPlayer = id.toString();

        String playerName = labPlayerName.getText();
        
        String matrixLevel1 = matrixToString(level1);
        
        String playerGame = matrixLevel1;
        String playerLevel = labNumberLevel.getText();
        String stepsPlayer = labSteps.getText();

        GameDTO  game1DTO = new GameDTO();
        
        game1DTO.setGamId(Short.valueOf(idPlayer)); 
        game1DTO.setGamPlayername(playerName);
        game1DTO.setGamGame(playerGame);
        game1DTO.setGamLevel(playerLevel);
        game1DTO.setGamSteps(stepsPlayer);
        
        gameDAO = new GameDAO();
        
        gameDAO.updateGame(game1DTO);
        
        gameDAO.closeConnection();
        
        System.out.println("Se actualizo la partida");
    }
    
    public void loadGame(){

        if(option == 2){
            labPlayerName.setText(name);
            level1 = stringToMatrix(playerMatrix,11,19);
            steps = Integer.parseInt(playerSteps);  
        }
    }
    
    @FXML
    private void clicklOneStepBack(ActionEvent event) {

        btnOneStepBack.setDisable(true);
                
        System.out.println("pa tras");
        for (int row = 0; row < 11; row++) {
            for (int column = 0; column < 19; column++) {
                level1[row][column] = previousMatrix[row][column];
            }
        }
        updateMap();
    
        steps++;
        labSteps.setText(String.valueOf(steps));
    }

    @FXML
    private void clickRestart(ActionEvent event) {
        
        paneLevelCompleted.setVisible(false);   
        
        steps = 35;
        labSteps.setText(String.valueOf(steps));
        
        restartLevel();   
    }
    
    @FXML
    private void clickBackToMenu(ActionEvent event) throws IOException {
        App.setRoot("menu"); 
    }
 
    @FXML
    private void clickNextLevel(ActionEvent event) throws IOException {
        
        App.setRoot("gamelvl2");
    }

    @FXML
    private void clickSaveGame(ActionEvent event) {
 
        if(id != null){
            updateGame();   
        }else{
            saveGame();
        }
    }

    @FXML
    private void clcikRestartLevel(ActionEvent event) {
        paneRestartLevel.setVisible(false);
        btnOneStepBack.setDisable(true);
        btnSave.setDisable(false);
        btnRestart.setDisable(false);
        steps = 35;
        labSteps.setText(String.valueOf(steps));
        restartLevel();
        gameOver = false;
    }
}
