import java.util.ArrayList;

public class User extends Player {
    ArrayList<Card> hand;
    int gold;
    User(int startHealth){
        int cardsPerHand = 10;
        this.maxHealth = startHealth;
        this.hand = new ArrayList<>(cardsPerHand);;
        this.health = maxHealth;
        this.gold = 0;
    }
    /*void attack(ArrayList<Card> cardsPlayed, Player victim){
        int damage = getDamage(cardsPlayed);

    }*/
    void drawCard(){
        //TODO: IMPLEMENT
    }
    @Override
    int getDamage(Object context) {
        if(context instanceof ArrayList<?>){
            ArrayList<Card> cardsPlayed = (ArrayList<Card>) context;
            Card hiCard = cardsPlayed.getFirst();
            Card loCard = cardsPlayed.getFirst();
            for(Card card : cardsPlayed){
                //TODO: Calculate damage of cards
            }


        }
        //TODO: FINISH FUNCTION
        int damage = 0;
        return damage;
    }


    void playCards(ArrayList<Card> cardsPlayed){
        for(Card card : cardsPlayed){
            this.hand.remove(card);
        }
        //TODO: FINISH FUNCTION
        getDamage(cardsPlayed);
        drawCard();
    }
}
