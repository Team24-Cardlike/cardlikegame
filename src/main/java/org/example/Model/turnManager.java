package org.example.Model;

public class turnManager {
    boolean currentPlayer;


    public turnManager(boolean first) {
        this.currentPlayer = first;
    }
    public boolean getCurrentPlayer(){
        return currentPlayer;
    }
    public void swapTurn(){
        currentPlayer = !currentPlayer;
    }
}