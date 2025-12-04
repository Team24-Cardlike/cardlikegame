package org.example.Model;



import java.util.*;

public class Round {
    RoundObsMethods o = new RoundObsMethods(this);

    User user;
    public Opponent opponent;
    Deck deck = new Deck();
    Stack<Card> gameDeck;

    public int totalDamageToOpponent = 0;
    public  int totalDamageToPlayer = 0;
    public boolean playerTurn = true;
    public boolean roundFinished = false;

    public float userHealth = 1;
    public float opponentHealth = 1;

    public String currentBestCombo;

    public Round(User user, Opponent opponent, RoundObserver ob ){
        this.user = user;
        this.opponent = opponent;
        this.deck.createInGameDeck();
        this.gameDeck = this.deck.getInGameDeck();
        o.addObserver(ob);



    }

    public Round(Opponent opponent , RoundObserver ob){
        this.user = new User(1000);
        this.opponent = opponent;
        this.deck.createInGameDeck();
        this.gameDeck = this.deck.getInGameDeck();
        user.drawCards(deck.getInGameDeck(), user.cardsPerHand);
        o.addObserver(ob);


    }

    // Check states
    public void roundUppdate() {
        if (gameDeck.size() + user.hand.size() <= deck.cards.size()) deck.refill(user.hand);

        if (playerTurn) {
            // Wait for player to make turn
            currentBestCombo = bestCombo(user.getSelectedCards());
            o.notifyBestCombo(currentBestCombo);
        }
        else {
            opponentTurn();

        }
        //round ends
        if ( user.health <= 0 || checkDeadOpponent()) {
            roundFinished = true;
            if(opponentHealth < userHealth) {
            o.notifyGameEnded("Victory");}
            else {
                o.notifyGameEnded("GameOver");
            }
        }

    }


    /**
     *  <b>Does the following:</b>
     * <ul>
     *   <li>user.playCards()</li>
     *   <li>this.damage()</li>
     *   <li>user.drawCards()</li>
     * </ul>
     * @param //playedCards cards played from the front end
     */
    public void playCards(){
        int damage = user.playCards();
        this.opponent.takeDamage(damage);
        opponentHealth = opponent.getHealthRatio();
        totalDamageToOpponent = totalDamageToOpponent + damage;
        while (user.hand.size() < user.cardsPerHand) user.hand.add(gameDeck.pop());


        System.out.println("Din motståndare tog "+damage+" skada! "+ this.opponent.getHealth(opponent)+ " kvar");
        playerTurn = false;

        o.notifyHealthChanged(userHealth,opponentHealth); // Notify observer of health changed
        o.notifyPlayerTurn(playerTurn); // Notify observer of changed player turn
        o.notifySelectedChanged(); // Notify observer of reset selected
        o.notifyHandChanged(); // Notify observer of new hand
    }



    private void opponentTurn() {
        int oppDamage = opponent.getDamage();
        user.takeDamage(oppDamage);
        userHealth = user.getHealthRatio();
        totalDamageToPlayer += oppDamage;

        System.out.println("Du tog " + opponent.getDamage() + " skada! Du har " + this.user.health + " hp kvar");
        playerTurn = true;

        o.notifyHealthChanged(userHealth,opponentHealth);// Notify observer of health changed
        o.notifyPlayerTurn(playerTurn); // notify player turn changed
    }

    void damage(Player defender, Player attacker){
        defender.takeDamage(attacker.getDamage());
    }


    public boolean checkDeadOpponent(){
        return opponent.health <= 0;
    }

    public String bestCombo(ArrayList<Card> cards){
        user.setSelectedCards(cards);
        if (user.getComboPlayedCards() == null ) {return "";}
        return user.getComboPlayedCards().name;
    }

    public void discard(ArrayList<Integer> indices){
        removeSelectedCards();
        user.drawCards(deck.getInGameDeck(),10 - user.getHand().size());
        o.notifySelectedChanged();
        o.notifyHandChanged();
    }

    public void removeSelectedCards() {
        user.setSelectedCards(new ArrayList<>());
        o.notifySelectedChanged();
    }


    // Removing selected card from hand and adding it to selected cards
    public void addSelectedCards(int index) {
        ArrayList<Card> tempHand = new ArrayList<>(user.getHand());
        Card c = tempHand.get(index);
        user.removeCardFromHand(index); // Remove form hand
        user.addSelectedCard(c); // Added to selected cards
        //Notify hand changed
        o.notifyHandChanged();
        o.notifySelectedChanged();
    }

    //Removing card from selected and returning it back to the hand.
    public void unselectCard(int index) {
        ArrayList<Card> temp = user.getSelectedCards();
        user.hand.add(temp.get(index));
        temp.remove(index);
        user.setSelectedCards(temp);

        o.notifySelectedChanged();
        o.notifyHandChanged();
    }

    // Round ended
    public void endRound() {
        this.roundFinished = true;
    }

    public User getUser(){return user;}


    public ArrayList<Card> getSelectedCardsAsCards(ArrayList<Integer> cards){
            ArrayList<Card> hand = user.getHand();
            ArrayList<Card> temp = new ArrayList<>();
            for(int i : cards) {
                temp.add(hand.get(i));
            }
            return temp;
    }



    public int getNumberOfSelected(ArrayList<Boolean> cardsBool){
        int i = 0;
        for(Boolean bool : cardsBool){
            if(bool)i++;
        }
        return i;
    }


    public void init() {
        o.notifyHandChanged();
        o.notifySelectedChanged();
        o.notifyBestCombo(currentBestCombo);
        o.notifyHealthChanged(userHealth, opponentHealth);
        o.notifyPlayerTurn(playerTurn);
    }

}

