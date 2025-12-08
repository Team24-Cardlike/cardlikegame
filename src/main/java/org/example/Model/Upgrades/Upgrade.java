package org.example.Model.Upgrades;

import org.example.Model.Card;
import org.example.Model.Combo;
import org.example.Model.User;

public abstract class Upgrade {
    String name;
    String desc;
    int cost;
    String category;

    Upgrade(String name, String desc, int cost, String category){
        this.name = name;
        this.desc = desc;
        this.cost = cost;
        this.category = category;
    }

    public String getName(){
        return this.name;
    }
    public String getDesc(){
        return this.desc;
    }
    public int getCost(){
        return this.cost;
    }
    // Called when the augment is purchased (for permanent stat changes)
    public void onBuy(User user){}

    // Called before evaluating a combo (for dynamic effects)
    public void beforeCombo(User user, Card[] hand) {}

    // Called after evaluating a combo (for reaction effects)
    public void afterCombo(User user, Combo combo) {}

    // Optional — for per-turn or per-battle effects
    public void onTurnStart(User user) {}
    public void onTurnEnd(User user) {}
}
