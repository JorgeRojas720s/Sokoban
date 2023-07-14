/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelDTO;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import model.Game;

/**
 *
 * @author jitor
 */
public class GameDTO {
    
    public SimpleStringProperty gamId;
    public SimpleStringProperty gamPlayername;
    public SimpleStringProperty gamGame;
    public SimpleStringProperty gamLevel;
    public SimpleStringProperty gamSteps;
    
    
    public GameDTO(){
      
        this.gamId = new SimpleStringProperty();
        this.gamPlayername = new SimpleStringProperty();
        this.gamGame = new  SimpleStringProperty();
        this.gamLevel = new SimpleStringProperty();
        this.gamSteps = new SimpleStringProperty();
  
    }

    public GameDTO(Game game) {
        this();
     
        this.gamPlayername.set(game.getGamPlayername());
        this.gamGame.set(game.getGamGame());
        this.gamLevel.set(game.getGamLevel().toString());
        this.gamSteps.set(game.getGamSteps().toString());
    }
 
    public Short getGamId() {
        if(gamId.get()!=null && !gamId.get().isEmpty())
            return Short.valueOf(gamId.get());
        else
            return null;
    }

    public String getGamPlayername() {
        return gamPlayername.get();
    }

    public String getGamGame() {
        return gamGame.get();
    }

    public String getGamLevel() {
        return gamLevel.get();
    }

    public String getGamSteps() {
        return gamSteps.get();
    }
    
    

    public void setGamId(Short gamId) {
        this.gamId.set(gamId.toString());
    }

    public void setGamPlayername(String gamPlayername) {
        this.gamPlayername.set(gamPlayername);
    }

    public void setGamGame(String gamGame) {
        this.gamGame.set(gamGame);
    }

    public void setGamLevel(String gamLevel) {
        this.gamLevel.set(gamLevel);
    }

    public void setGamSteps(String gamSteps) {
        this.gamSteps.set(gamSteps);
    }
    
     @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((gamId == null) ? 0 : gamId.get().hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        GameDTO other = (GameDTO) obj;
        if (gamId == null) {
            if (other.gamId != null)
                return false;
        } else if (!gamId.get().equals(other.gamId.get()))
            return false;
        return true;
    }
 
    @Override
    public String toString() {
        return "GameDTO [gamId=" + gamId.get() + ", gamPlayername=" + gamPlayername.get() + ", gamGame=" + gamGame.get()
                + ", gamLevel=" + gamLevel.get() + ", gamSteps=" + gamSteps.get() + "]";
    }

    
}
