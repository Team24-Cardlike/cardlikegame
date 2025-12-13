package org.example.Model;


/**
 * Serializable class that takes the latest data from the model
 * and sets data that's been loaded in 
 */
public class GameData {
    private transient GameManager gameManager; // Should not be serialized, we don't need everything from gameManager
    private int health;
    // TODO: Add new attributes here

    public GameData(GameManager gameManager) {        
        this.gameManager = gameManager;        
    }

    void updateData() {        
        this.health = gameManager.getUser().getHealth();
        // TODO: Add more getters here!
    }

    int getHealth() {
        return health;
    }
    void setHealth() {        
        gameManager.getUser().setHealth(health);
    }

    GameManager getGameManager() {
        return gameManager;
    }
    void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }
}