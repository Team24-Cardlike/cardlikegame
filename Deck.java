import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

public class Deck {

    ArrayList<Card> cards = new ArrayList<>();
    Stack<Card> deck = new Stack<>();
    ArrayList<String> suits = new ArrayList<>(Arrays.asList("sun", "moon", "lightning", "tree"));
    ArrayList<Integer> ranks = new ArrayList<>(Arrays.asList(2, 3, 4, 5 ,6 ,7 ,8, 9, 10, 11, 12, 13, 14));

    Deck(){
        generateDeck();
    }
    public void generateDeck(){
        for(String suit : suits) {
            for (int rank : ranks) {
                this.cards.add(new Card(suit, rank));
            }
        }        
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

    void createInGameDeck(){
        ArrayList<Card> shuffled = shuffle();
        Stack<Card> inGameDeck = new Stack<>();
        inGameDeck.addAll(shuffled);        
        this.deck = inGameDeck;            
    }

    Stack<Card> getInGameDeck() {        
        return this.deck;
    }

    /*public static void main (String[]args){
        Deck deck = new Deck();
        for (Card c : deck.shuffle()) {
            System.out.println(c.suit + " " + c.rank + ": " + deck.shuffle().size());
        }
    }*/
}
