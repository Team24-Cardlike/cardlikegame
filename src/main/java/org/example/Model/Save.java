package org.example.Model;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Saves data and loads it in
 */
public class Save {
    
    private final static String fileName = "savedGameData.ser";  
    private static final Gson GSON = new Gson();
    private GameData gameData;
    
    public Save(GameData gameData) {
        this.gameData = gameData;
    }
    
    /**
     * Runs when Save button is pressed
     * Saves data using Gson
     */
    public void saveGame() {  

        gameData.updateData(); // Updates gameData with the newest data from the model

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
    public boolean loadGame() {
        try (FileReader reader = new FileReader(fileName)) {

            GameData loadedData = GSON.fromJson(reader, GameData.class);
            if (loadedData != null) loadedData.setGameManager(gameData.getGameManager());                                                            
            setLoadedData(loadedData);
            return true;

        } catch (FileNotFoundException e) {            
            System.out.println("No save file found. Start a new game.");
            return false;
        } catch (IOException e) {
            System.err.println("Error reading or processing save file: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Loads saved data into the game
     */
    private void setLoadedData(GameData loadedData) {        
        try {
            loadedData.initializeData();                   
        } catch (NullPointerException e) {
            System.out.println("No previous data stored, you may have forgotten to save.");
        }

    }
}
