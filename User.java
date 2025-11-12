import java.util.ArrayList;
import java.util.Stack;

public class User extends Player {
    
    ArrayList<Card> hand;
    int gold;
    int shield = 0;
    int cardsPerHand = 10;


    User(int startHealth){        
        this.maxHealth = startHealth;
        this.hand = new ArrayList<>(cardsPerHand);
        this.health = maxHealth;
        this.gold = 0;
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
    int getDamage(Object context) {
        int damage = 0;
        if(context instanceof ArrayList<?>){
            ArrayList<Card> cardsPlayed = (ArrayList<Card>) context;
            Card hiCard = cardsPlayed.getFirst();
            Card loCard = cardsPlayed.getFirst();
            for(Card card : cardsPlayed){
                // TODO: Calculate damage of cards
                // TODO: We need to decide on what the suits mean
                damage += card.rank;
            }


        }
        //TODO: FINISH FUNCTION
        return damage;
    }

    void takeDamage(Object context, int damage) {
        this.health -= damage;
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
        getDamage(cardsPlayed);        
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }
}
