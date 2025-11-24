package org.example.Model;

import java.util.*;

public class Deck {

    ArrayList<Card> cards = new ArrayList<>();
    Stack<Card> gameDeck = new Stack<>();
    public final ArrayList<String> suits = new ArrayList<>(Arrays.asList("sun", "moon", "water", "tree"));
    public final ArrayList<Integer> ranks = new ArrayList<>(Arrays.asList(2, 3, 4, 5 ,6 ,7 ,8, 9, 10, 11, 12, 13, 14));

    public Deck(){
        generateDeck();
    }

    public void generateDeck(){
        for(String suit : this.suits) {
            for (int rank : this.ranks) {
                this.cards.add(new Card(suit, rank));
            }
        }
    }

    public void generateTEMPORARYdeck(){
        int i = 0;
        int j = 0;
        int x = 0;
        while(x<4){
            this.cards.add(new Card("lightning", 3));
            x++;
        }
        while(i<4){
            this.cards.add(new Card("moon", 3));
            i++;
        }
        while(j<4){
            this.cards.add(new Card("sun", 3));
            j++;
        }
    }

    public ArrayList<Card> getCards(){
        return this.cards;
    }

    // Doesn't change this.cards, returns shuffled version
    public ArrayList<Card> shuffle(){
        
        ArrayList<Card> temp = new ArrayList<>(this.cards);
        ArrayList<Card> shuffledCards = new ArrayList<>(Arrays.asList(new Card[this.cards.size()]));
        Random r = new Random();
        for(int i = 0; i<this.cards.size(); i++){
            int rand = r.nextInt(temp.size());
            shuffledCards.set(i, temp.get(rand));
            temp.remove(rand);
        }        
        return shuffledCards;
    }

    public void createInGameDeck(){
        ArrayList<Card> shuffled = shuffle();
        Stack<Card> inGameDeck = new Stack<>();
        inGameDeck.addAll(shuffled);        
        this.gameDeck = inGameDeck;            
    }

    public Stack<Card> getInGameDeck() {
        return this.gameDeck;
    }


    /**
     *
     * @param name in the format "suit of rank", ex. "10 of sun"
     * @return the card with the name put in, if null card does not exist
     */
    Card getCard(String name){
        for(Card card : this.cards){
            if(Objects.equals(card.name, name))
                return card;
        }
        return null;
    }

    /**
     * Refills deck so that it's never empty
     * @param hand
     */
    void refill(ArrayList<Card> hand) {
        Random random = new Random();
        for (Card card : cards) {
            if (!hand.contains(card) && !gameDeck.contains(card)) {
                gameDeck.insertElementAt(card, random.nextInt(gameDeck.size()));
            }            
        }
    }
}
