package org.example.Model;

import java.util.ArrayList;
import java.util.Set;

/**
 * Serializable class that saves the latest data from the model
 * and initializes data when the game is started 
 */
public class GameData {
    private transient GameManager gameManager; // Should not be serialized, we don't need everything from gameManager

    // TODO: Add new attributes here (e.g. money)
    // These attributes get saved when saveGame() in Save.java
    // gets called
    private Set<String> completedLvls;
    private Set<String> availableLvls;    

    public GameData(GameManager gameManager) {        
        this.gameManager = gameManager;        
    }


    /**
     * Retrieves data from gameManager and 
     * saves it here as an attribute
     */
    void updateData() {                
        // TODO: Add more getters here!
        this.completedLvls = gameManager.getCompletedLvls();
        this.availableLvls = gameManager.getAvailableLvls();        

    }

    void setCompletedLvls() {        
        gameManager.gameMap.setCompletedLvls(completedLvls);
    }

    void setAvailableLvls() {
        gameManager.gameMap.setAvailableLvls(availableLvls);
    }

    GameManager getGameManager() {
        return gameManager;
    }
    void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }
    
}