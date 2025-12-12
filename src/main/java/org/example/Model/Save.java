package org.example.Model;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Saves data 
 */
public class Save {
    
    private final static String fileName = "savedGameData.ser";  
    private static final Gson GSON = new Gson();
    private GameManager gameManager;
    
    public Save(GameManager gameManager) {
        this.gameManager = gameManager; // TODO: IS THIS DTO PATTERN?
    }
    
    /**
     * Runs when Save button is pressed
     */
    public void saveGame() {  
        try (FileWriter writer = new FileWriter(fileName)) {
            // GSON.toJson(gameManager.getUser().getHealth(), writer);
            GSON.toJson(gameManager.getUser().getHealth(), writer);
            System.out.println("Game saved successfully to " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }             
        
        }
    public void loadGame() {
        try (FileReader reader = new FileReader(fileName)) {
                    
            int loadedData = GSON.fromJson(reader, Integer.class);
                        
            gameManager.getUser().setHealth(loadedData);                                    
            // gameManager.getInventory().rebuildFromIds(loadedData.getInventoryItemIds());            
            System.out.println("Game loaded successfully. Health: " + loadedData);            

        } catch (FileNotFoundException e) {            
            System.out.println("No save file found. Starting a new game.");    
        } catch (IOException e) {
            System.err.println("Error reading or processing save file: " + e.getMessage());
            e.printStackTrace();            
        }
    }

}
