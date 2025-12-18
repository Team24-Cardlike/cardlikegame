package org.example.Model;

import java.util.ArrayList;
import java.util.Set;

import org.example.Model.Upgrades.Upgrade;

/**
 * Serializable class that saves the latest data from the model
 * and initializes data when the game is started 
 */
public class GameData {
    private transient GameManager gameManager; // Should not be serialized, we don't need everything from gameManager
    
    // These attributes get saved when saveGame() in Save.java
    // gets called
    private Set<String> completedLvls;
    private Set<String> availableLvls;    
    private int gold;
    private ArrayList<Integer> upgradeIds;
    private String difficulity;

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
        this.gold = gameManager.getUser().getGold();   
        this.difficulity = gameManager.getDifficulty(); 

        upgradeIds = new ArrayList<Integer>();
        ArrayList<Upgrade> upgrades = gameManager.getUser().getUpgrades();
        for (Upgrade upgrade : upgrades) {            
            upgradeIds.add(upgrade.getIdNum());
        }

    }

    void setDifficulty() {
        gameManager.setDifficulty(difficulity);
    }

    void setGold() {        
        gameManager.getUser().setGold(gold);
    }

    void setCompletedLvls() {        
        gameManager.setCompletedLvls(completedLvls);
    }

    void setAvailableLvls() {
        gameManager.setAvailableLvls(availableLvls);
    }

    void setUpgrades() {
        // gameManager.getUser().setUpgrades(upgradeIds);
        gameManager.setUpgrades(upgradeIds);
    }

    
    void initializeData() {
        this.setCompletedLvls();
        this.setAvailableLvls();
        this.setGold();
        this.setUpgrades();
        this.setDifficulty();
    }    

    GameManager getGameManager() {
        return gameManager;
    }

    void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

}