package org.example.Model;


import java.util.ArrayList;
import java.util.List;

public class RoundObsMethods {

    //List with observers who is observing round state
    final private ArrayList<RoundObserver> observers = new ArrayList<>();

    Round round;
    public RoundObsMethods(Round g) {this.round = g;}

    //Add observer to observer's list
    public void addObserver(RoundObserver observer) {
        observers.add(observer);
    }
    //Remove observer from observer's list
    public void removeObserver(RoundObserver observer) {
        observers.remove(observer);
    }



    // Hand changed sending updated hand to view in a list of strings
    public void notifyHandChanged(ArrayList<Card> hand) {
        for (RoundObserver o : observers) {
            o.onHandChanged(handToString(hand));}
    }

    // Selected cards changed, update view in list of strings
    public void notifySelected(ArrayList<Card> selectedCards) {
        for (RoundObserver o : observers) {
            o.onSelectedCard(handToString(selectedCards));
        }
    }

    public void notifyUnselected(ArrayList<Card> selectedCards) {
        for (RoundObserver o : observers) {
            o.onUnselectedCard(handToString(selectedCards));
        }
    }

    //Notify current best combo in string-message
    public void notifyBestCombo(String bestCombo) {
        for (RoundObserver o : observers) {
            o.onBestComboChanged(bestCombo);
        }
    }

    //Notify health changed
    public void notifyHealthChanged(float playerHealth,float opponentHealth) {
        for (RoundObserver o : observers) {
            o.onHealthChanged(playerHealth,opponentHealth);
        }
    }

    // Notifys if a round has ended
    public void notifyGameEnded(String message, int dmgToOp, int dmgToUser) {
        for (RoundObserver o : observers) {
            o.onGameEnded( message, dmgToOp, dmgToUser);
        }
    }


    // Notify player turn changed
    public void notifyPlayerTurn(boolean playerTurn) {
        for (RoundObserver o : observers) {
            o.onPlayerTurn(playerTurn);
        }
    }


    // Converts a list of cards to a list of string, where each element is the cardname
    public ArrayList<String> handToString(ArrayList<Card> hand) {
        ArrayList<String> handToString = new ArrayList<>();
        for (Card c : hand ){

            handToString.add(c.pic);}
        return handToString;
    }

    public void notifyOpponentAttacked(int damage){//String name, String attackName
        for (RoundObserver o : observers) {
            o.onOpponentAttack(damage);
        }
    }


    // Notify new round
    public void notifyNewRound() {
        for (RoundObserver o : observers) {
            o.onRoundInit();
        }

    }
}
