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
    private GameData gameData;
    
    public Save(GameData gameData) {
        this.gameData = gameData; // TODO: IS THIS DTO PATTERN?
    }
    
    /**
     * Runs when Save button is pressed
     * Saves data using GSON
     */
    public void saveGame() {  

        gameData.updateData(); // Updates gameData the newest data from the model

        try (FileWriter writer = new FileWriter(fileName)) {            
            GSON.toJson(gameData, writer);
            System.out.println("Game saved successfully to " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }                     
    }


    /**
     * Retrieves saved data
     */
    public void loadGame() {
        try (FileReader reader = new FileReader(fileName)) {
                    
            GameData loadedData = GSON.fromJson(reader, GameData.class);
            if (loadedData != null) loadedData.setGameManager(gameData.getGameManager());
                                    
            // System.out.println("Game loaded successfully. Health: " + loadedData.getHealth());
            
            setLoadedData(loadedData);

        } catch (FileNotFoundException e) {            
            System.out.println("No save file found. Starting a new game.");    
        } catch (IOException e) {
            System.err.println("Error reading or processing save file: " + e.getMessage());
            e.printStackTrace();            
        }
    }


    /**
     * Loads saved data into the game
     */
    private void setLoadedData(GameData loadedData) {
        // loadedData.setHealth();
        // TODO: Add new setters here
        try {            
            loadedData.setCompletedLvls();
            loadedData.setAvailableLvls();
            // loadedData.getGameManager().gameMap.updateMap();
            loadedData.setOppIdx();
        } catch (NullPointerException e) {
            System.out.println("Funkar ej med ladda in completedlvls");
        }

    }
}
