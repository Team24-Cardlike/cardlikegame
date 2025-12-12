package org.example.Model.Upgrades;

import org.example.Model.Round;

public abstract class Upgrade {
    String name;
    String desc;
    int cost;
    String category;
    String pic;

    /***
     *
     * @param name String name of the upgrade
     * @param desc String description of the upgrade
     * @param cost int cost of the upgrade
     * @param category String name of the category (Sustain, Damage, Economy)
     */
    Upgrade(String name, String desc, int cost, String category, String pic){
        this.name = name;
        this.desc = desc;
        this.cost = cost;
        this.category = category;
        this.pic = "assets/images/"+pic+".png";
    }

    public String getName(){
        return this.name;
    }
    public String getDesc(){
        return this.desc;
    }
    public String getPic(){
        return this.pic;
    }
    public int getCost(){
        return this.cost;
    }
    // Called when the augment is purchased (for permanent stat changes)
    public void onBuy(User user){}

    // Called before evaluating a combo (for dynamic effects)
    public void beforeCombo(User user, Card[] hand) {}

    public String getCategory() {
        return this.category;
    }

    //public abstract T getNum();

    public abstract boolean checkCondition(Round round);

    public abstract void onTriggered(Round round);
}
