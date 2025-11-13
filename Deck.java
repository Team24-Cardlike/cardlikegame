import java.util.*;

public class Deck {

    ArrayList<Card> cards = new ArrayList<>();
    Stack<Card> gameDeck = new Stack<>();
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
        this.gameDeck = inGameDeck;            
    }

    Stack<Card> getInGameDeck() {        
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

    void refill(ArrayList<Card> hand) {
        Random random = new Random();
        for (Card card : cards) {
            if (!hand.contains(card) && !gameDeck.contains(card)) {
                gameDeck.insertElementAt(card, random.nextInt(gameDeck.size()));
            }            
        }
    }
}
