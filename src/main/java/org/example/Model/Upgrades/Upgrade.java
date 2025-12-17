package org.example.Model.Upgrades;

import org.example.Model.Round;
import org.example.Model.User;

public abstract class Upgrade {
    final String name;
    final String desc;
    int cost;
    final String category;
    final String pic;
    final int idNum;

    /***
     *
     * @param name String name of the upgrade
     * @param desc String description of the upgrade
     * @param cost int cost of the upgrade
     * @param category String name of the category (Sustain, Damage, Economy)
     */
    Upgrade(String name, String desc, int cost, String category, String pic, int id){
        this.name = name;
        this.desc = desc;
        this.cost = cost;
        this.category = category;
        this.pic = "assets/images/upgradePics/"+pic+".png";
        this.idNum = id;
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

    public String getCategory() {
        return this.category;
    }
    //public abstract T getNum();

    public abstract boolean checkCondition(Round round);

    public abstract void onTriggered(Round round);
}
