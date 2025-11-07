import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Deck {
    ArrayList<Card> cards = new ArrayList<>();
    ArrayList<String> suits = new ArrayList<>(Arrays.asList("sun", "moon", "lightning", "tree"));
    ArrayList<Integer> ranks = new ArrayList<>(Arrays.asList(2, 3, 4, 5 ,6 ,7 ,8, 9, 10, 11, 12, 13, 14));
    int cardAmount = cards.size();
    Deck(){
        generateDeck();
    }
    public ArrayList<Card> generateDeck(){
        for(String suit : suits) {
            for (int rank : ranks) {
                this.cards.add(new Card(suit, rank, suit+rank));
            }
        }

        return this.cards;
    }
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

    /*public static void main (String[]args){
        Deck deck = new Deck();
        for (Card c : deck.shuffle()) {
            System.out.println(c.suit + " " + c.rank + ": " + deck.shuffle().size());
        }
    }*/
}
