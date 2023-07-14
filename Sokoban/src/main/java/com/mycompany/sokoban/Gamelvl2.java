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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.D;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.LEFT;
import static javafx.scene.input.KeyCode.RIGHT;
import static javafx.scene.input.KeyCode.S;
import static javafx.scene.input.KeyCode.UP;
import static javafx.scene.input.KeyCode.W;
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
public class Gamelvl2 implements Initializable {
    
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
    
    private char[][] level2 = {
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#','#', '#', '#', '#', '#', '#', '#', '#', '#'},
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#','#', '#', '#', '#', '#', '#', '#', '#', '#'},
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#','#', '#', '#', '#', '#', '#', '#', '#', '#'},
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#','#', '#', '#', '#', '#', '#', '#', '#', '#'},
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', 'P','#', ' ', '#', '#', '#', '#', '#', '#', '#'},
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', 'O','+', ' ', '#', '#', '#', '#', '#', '#', '#'},
            {'#', '#', '#', '#', '#', '#', '#', ' ', 'O', ' ',' ', ' ', '#', '#', '#', '#', '#', '#', '#'},
            {'#', '#', '#', '#', '#', '#', '#', ' ', '+', ' ','O', '+', '#', '#', '#', '#', '#', '#', '#'},
            {'#', '#', '#', '#', '#', '#', '#', ' ', ' ', ' ',' ', ' ', '#', '#', '#', '#', '#', '#', '#'},
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#','#', '#', '#', '#', '#', '#', '#', '#', '#'},
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#','#', '#', '#', '#', '#', '#', '#', '#', '#'},
    };

    private int playerRow;
    private int playerColumn;
    int steps = 32;
    int counter = 0;
     boolean gameOver;
    
    private Image imageWalls;
    private Image imageBook;
    private Image imageFinnFrente;
    private Image imageGoal;
    private Image imageFinnInGoal;
    private Image ImageBookInGoal;
    
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
                 startLevel[row][column] = level2[row][column];   
            }      
        }    
    }   
    
       private void savePreviousMove(){
        for (int row = 0; row < 11; row++) {
            for (int column = 0; column < 19; column++) {
                previousMatrix[row][column] = level2[row][column];
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
        if (level2[row][column] == '#') {
            label.setGraphic(new ImageView(imageWalls));
        }
    }

    private void setImageToBoxes(Label label, int row, int column) {
        if (level2[row][column] == 'O') {
            label.setGraphic(new ImageView(imageBook));
        }
    }

    private void setImageToPlayer(Label label, int row, int column) {
        if (level2[row][column] == 'P') {
            label.setGraphic(new ImageView(imageFinnFrente));
        }
    }

    private void setImageToGoal(Label label, int row, int column) {
        if (level2[row][column] == '+') {
            label.setGraphic(new ImageView(imageGoal));
        }
    }

    private void setImageToBookInGoal(Label label, int row, int column) {
        if (level2[row][column] == 'M') {
            label.setGraphic(new ImageView(ImageBookInGoal));
        }
    }

    private void setImageToPlayerInGoal(Label label, int row, int column) {
        if (level2[row][column] == 'F') {
            label.setGraphic(new ImageView(imageFinnInGoal));
        }
    }

    private void searchPlayer(){
        
        for (int row = 0; row < 11; row++) {
            for (int column = 0; column < 19; column++) {
                System.out.print(level2[row][column] + ",");
                if (level2[row][column] == 'P' || level2[row][column] == 'F') {    
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
    
    public void  finishedLevel(){
        
        if((level2[7][8] == 'M') && (level2[5][10] == 'M') && (level2[7][11] == 'M')){    
            System.out.println("Terminoooooooooooooooo");
            btnSave.setDisable(true);
            btnRestart.setDisable(true);
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
        
        if(level2[newRow][newColumn] == ' ' && level2[playerRow][playerColumn] == 'F'){
   
            savePreviousMove();
            level2[newRow][newColumn] = 'P';
            level2[playerRow][playerColumn] = '+';     
        }
    }
    
    public void playerMove(int newRow, int newColumn){
        
        if(level2[newRow][newColumn] == ' '){ 
            
            savePreviousMove();
            btnOneStepBack.setDisable(false);
            
            level2[newRow][newColumn] = 'P';
            level2[playerRow][playerColumn] = ' '; 
        } 
    }
    
    public void playerEnteringTheGoal(int newRow, int newColumn){
        
        if((level2[newRow][newColumn] == '+') && (level2[playerRow][playerColumn] == 'F')){
            
            savePreviousMove();
            btnOneStepBack.setDisable(false);
            level2[newRow][newColumn] = 'F';
            level2[playerRow][playerColumn] = '+'; 
        }
        else if(level2[newRow][newColumn] == '+'){ 
            
            savePreviousMove();
            btnOneStepBack.setDisable(false);
            level2[newRow][newColumn] = 'F';
            level2[playerRow][playerColumn] = ' ';    
        }
    }
        
    public void checkIfBoxIsPushed(int newRow, int newColumn,int aux1,int aux2){
        
        if((level2[newRow][newColumn] == 'O' || level2[newRow][newColumn] == 'M') && level2[newRow + aux1][newColumn + aux2] != '#' 
            && level2[newRow + aux1][newColumn + aux2] != 'O' && level2[newRow + aux1][newColumn + aux2] != 'M'){
            
            savePreviousMove();
            
            btnOneStepBack.setDisable(false);
            
            pushOutGoalBox(newRow,newColumn,aux1,aux2);
            
            pushBox(newRow,newColumn,aux1,aux2);

            pushBoxToTheGoal(newRow,newColumn,aux1,aux2);
   
        }
    }
    
    private void pushOutGoalBox(int newRow, int newColumn, int aux1, int aux2){

        if(level2[newRow + aux1][newColumn + aux2] == '+' && level2[newRow][newColumn] == 'M' &&  level2[playerRow][playerColumn] == 'F'){
              
            savePreviousMove();
            level2[newRow + aux1][newColumn + aux2] = 'M';
            level2[newRow][newColumn] = 'F';
            level2[playerRow][playerColumn] = '+';
        }else if(level2[newRow + aux1][newColumn + aux2] == ' ' && level2[newRow][newColumn] == 'M' &&  level2[playerRow][playerColumn] == 'F'){  
            
            savePreviousMove();
            level2[newRow + aux1][newColumn + aux2] = 'O';
            level2[newRow][newColumn] = 'F';
            level2[playerRow][playerColumn] = '+';          
        }else if(level2[newRow + aux1][newColumn + aux2] == '+' && level2[newRow][newColumn] == 'M'){
            
            savePreviousMove();
            level2[newRow + aux1][newColumn + aux2] = 'M';
            level2[newRow][newColumn] = 'F';
            level2[playerRow][playerColumn] = ' '; 
        }else if(level2[newRow][newColumn] == 'M'){   
            
            savePreviousMove();
            level2[newRow + aux1][newColumn + aux2] = 'O';
            level2[newRow][newColumn] = 'F';
            level2[playerRow][playerColumn] = ' ';
        }
    }
    
    private void pushBox(int newRow, int newColumn, int aux1, int aux2){
        
        if(level2[newRow + aux1][newColumn + aux2] == ' ' && level2[playerRow][playerColumn] == 'F'){
             
            savePreviousMove();
            level2[newRow + aux1][newColumn + aux2] = 'O';
            level2[newRow][newColumn] = 'P';
            level2[playerRow][playerColumn] = '+';
        }else if(level2[newRow + aux1][newColumn + aux2] == ' '){
            
            savePreviousMove();
            level2[newRow + aux1][newColumn + aux2] = 'O';
            level2[newRow][newColumn] = 'P';
            level2[playerRow][playerColumn] = ' ';  
        }      
            
    }
    
    private void pushBoxToTheGoal(int newRow, int newColumn, int aux1, int aux2){
        
        if(level2[newRow + aux1][newColumn + aux2] == '+'){
            
            savePreviousMove();
            level2[newRow + aux1][newColumn + aux2] = 'M';
            level2[newRow][newColumn] = 'P';
            level2[playerRow][playerColumn] = ' ';       
        }
    }
    
    public void counterSteps(int newRow, int newColumn){
        
        if(level2[newRow][newColumn] != '#' &&  level2[newRow][newColumn] != 'O' &&  level2[newRow][newColumn] != 'M'){
            steps--;
            labSteps.setText(String.valueOf(steps));
        }
    }
    
    private void restartLevel(){
        
        for (int row = 0; row < 11; row++) {
            for (int column = 0; column < 19; column++) {
                level2[row][column] = startLevel[row][column];
            }
        }
        updateMap();  
    }
    
    private void gameLocked(){
        
        if(level2[8][9] == 'O' || level2[8][10] == 'O' || level2[6][7] == 'O' || level2[9][10] == 'O' || 
        level2[4][9] == 'O' || level2[6][11] == 'O' || level2[7][7] == 'O'){
            
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
        
        String matrixlevel1 = matrixToString(level2);
     
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
        
        String matrixLevel2 = matrixToString(level2);
        
        String playerGame = matrixLevel2;
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

        if(option == 3){
            labPlayerName.setText(name);
            level2 = stringToMatrix(playerMatrix,11,19);
            steps = Integer.parseInt(playerSteps);  
        }
    }
    
    @FXML
    private void clicklOneStepBack(ActionEvent event) {

        btnOneStepBack.setDisable(true);
                
        System.out.println("pa tras");
        for (int row = 0; row < 11; row++) {
            for (int column = 0; column < 19; column++) {
                level2[row][column] = previousMatrix[row][column];
            }
        }
        updateMap();
    
        steps++;
        labSteps.setText(String.valueOf(steps));
    }

    @FXML
    private void clickRestart(ActionEvent event) {
        
        paneLevelCompleted.setVisible(false);   
        
        steps = 32;
        labSteps.setText(String.valueOf(steps));
        
        restartLevel();
    }
    @FXML
    private void clickNextLevel(ActionEvent event) throws IOException {
        App.setRoot("gamelvl3");
    }

    @FXML
    private void clickBackToMenu(ActionEvent event) throws IOException {
        App.setRoot("menu"); 
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
        steps = 32;
        labSteps.setText(String.valueOf(steps));
        restartLevel();
        gameOver = false;
        
    }
}
