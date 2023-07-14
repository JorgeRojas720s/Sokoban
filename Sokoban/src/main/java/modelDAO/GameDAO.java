/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Game;
import modelDTO.GameDTO;

/**
 *
 * @author jitor
 */
public class GameDAO {
    
    
    private Connection connection;

    public GameDAO() {
        // Initialize the database connection
        try {
            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            String username = "JRojas";
            String password = "Palomo_123";
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public void saveGame(GameDTO gameDTO) {
        String sql = "INSERT INTO TBL_GAMES (GAM_ID, GAM_PLAYERNAME, GAM_GAME, GAM_LEVEL,"
                + "GAM_STEPS) VALUES (SEC_GAMES.NEXTVAL, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setString(1, gameDTO.getGamPlayername());
            statement.setString(2, gameDTO.getGamGame());
            statement.setString(3, gameDTO.getGamLevel());
            statement.setString(4, gameDTO.getGamSteps());
 
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public GameDTO getGameById(String id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM TBL_GAMES WHERE GAM_ID = ?");
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                GameDTO gameDTO = new GameDTO();
                gameDTO.setGamId(Short.valueOf(resultSet.getString("GAM_ID")));
                gameDTO.setGamPlayername(resultSet.getString("GAM_PLAYERNAME"));
                gameDTO.setGamGame(resultSet.getString("GAM_GAME"));
                gameDTO.setGamLevel(resultSet.getString("GAM_LEVEL"));
                gameDTO.setGamSteps(resultSet.getString("GAM_STEPS"));
                
                return gameDTO;
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
      
    public void updateGame(GameDTO gameDTO) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE TBL_GAMES SET GAM_PLAYERNAME = ?, "
            + "GAM_GAME = ?, GAM_LEVEL = ?, GAM_STEPS = ? WHERE GAM_ID = ?");
            statement.setString(1, gameDTO.getGamPlayername());
            statement.setString(2, gameDTO.getGamGame());
            statement.setString(3, gameDTO.getGamLevel());
            statement.setString(4, gameDTO.getGamSteps());
            statement.setString(5, gameDTO.getGamId().toString());
    
        
            statement.executeUpdate();
        
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteGame(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM TBL_GAMES WHERE GAM_ID = ?");
            statement.setInt(1, id);
        
            statement.executeUpdate();
        
            statement.close();
        
     } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public List<Game> getGames() {
        List<Game> listGames = new ArrayList<>();
        
        try {
            String query = "SELECT * FROM TBL_GAMES ORDER BY GAM_ID";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                 Short iD = resultSet.getShort("GAM_ID");
                String name = resultSet.getString("GAM_PLAYERNAME");
                Short level = resultSet.getShort("GAM_LEVEL");
                Short steps = resultSet.getShort("GAM_STEPS");

                Game game = new Game(iD,name, level,steps);
                listGames.add(game);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return listGames;
    }
    
}
