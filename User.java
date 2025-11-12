import java.util.ArrayList;
import java.util.Stack;

public class User extends Player {
    
    ArrayList<Card> hand;
    int gold;
    int shield = 0;
    int cardsPerHand = 10;
    ArrayList<Card> selectedCards;


    User(int startHealth){        
        this.maxHealth = startHealth;
        this.hand = new ArrayList<>(cardsPerHand);
        this.health = maxHealth;
        this.gold = 0;
        this.selectedCards=new ArrayList<>();
    }
    /*void attack(ArrayList<Card> cardsPlayed, Player victim){
        int damage = getDamage(cardsPlayed);

    }*/

    void drawCards(Stack<Card> deck, int amount){
        //TODO: IMPLEMENT
        // ArrayList<Card> drawnCards = new ArrayList<>();
        
        for (int i = 0; i < amount; i++) {
            this.hand.add(deck.pop());
        }
    }
    @Override
    int getDamage() {
        int damage=0;
        Card hiCard = this.selectedCards.getFirst();
        Card loCard = this.selectedCards.getFirst();
        for(Card card : this.selectedCards){
            // TODO: Calculate damage of cards
            // TODO: We need to decide on what the suits mean
            damage += card.rank;
        }
        return damage;
    }
    /**
     * Removes selected cards from hand 
     * @param cardsPlayed Cards that you selected
     */
    void playCards(ArrayList<Card> cardsPlayed){
        for(Card card : cardsPlayed){
            this.hand.remove(card);                                    
        }
        // TODO: FINISH FUNCTION
        getDamage();
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }
}
