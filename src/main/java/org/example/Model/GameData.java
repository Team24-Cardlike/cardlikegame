package org.example.Model;

import java.util.ArrayList;
import java.util.Set;

/**
 * Serializable class that takes the latest data from the model
 * and sets data that's been loaded in 
 */
public class GameData {
    private transient GameManager gameManager; // Should not be serialized, we don't need everything from gameManager
    // private int health;
    // TODO: Add new attributes here
    private Set<String> completedLvls;
    private Set<String> availableLvls;
    private int oppIdx;

    public GameData(GameManager gameManager) {        
        this.gameManager = gameManager;        
    }

    void updateData() {        
        // this.health = gameManager.getUser().getHealth(); // For testing
        // TODO: Add more getters here!
        this.completedLvls = gameManager.getCompletedLvls();
        this.availableLvls = gameManager.getAvailableLvls();
        this.oppIdx = gameManager.getOppIdx();        

    }


    // int getHealth() {
    //     return health;
    // }
    // void setHealth() {        
    //     gameManager.getUser().setHealth(health);
    // }

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

    public void setOppIdx() {
        gameManager.gameMap.setOppIdx(oppIdx);
    }
}