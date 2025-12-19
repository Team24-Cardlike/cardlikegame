package org.example.Model.Upgrades;

import org.example.Model.Card;
import org.example.Model.Round;

import java.util.ArrayList;

public class SuitDamageAmp extends Upgrade{
    int extraDamage;
    String ampedSuit;
    SuitDamageAmp(String name, String ampedSuit, int extraDamage, int cost, String pic, int id){
        super(name, "For every " + ampedSuit + "-card in your played cards, do " + extraDamage + " additional damage to your opponent.", cost, "Economy", pic, id);
        this.extraDamage = extraDamage;
        this.ampedSuit = ampedSuit;
    }
    @Override
    public boolean checkCondition(Round round) {
        boolean contains = false;
        ArrayList<Card> cards = round.getUser().getSelectedCards();
        for(Card card : cards){
            if(card.suit == this.ampedSuit)
                contains=true;
        }
        return contains;
    }

    @Override
    public void onTriggered(Round round) {
        ArrayList<Card> cards = round.getUser().getSelectedCards();
        for(Card card : cards){
            if(card.suit == this.ampedSuit)
                round.getUser().damage += extraDamage;
        }
    }
}
