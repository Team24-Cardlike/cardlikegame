package org.example.Model.Upgrades;

import org.example.Model.Round;

public abstract class Upgrade {
    String name;
    String desc;
    int cost;
    String category;

    /***
     *
     * @param name String name of the upgrade
     * @param desc String description of the upgrade
     * @param cost int cost of the upgrade
     * @param category String name of the category (Sustain, Damage, Economy)
     */
    Upgrade(String name, String desc, int cost, String category){
        this.name = name;
        this.desc = desc;
        this.cost = cost;
        this.category = category;
    }

    public String getName() {
        return this.name;
    }

    public int getCost() {
        return this.cost;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getCategory() {
        return this.category;
    }

    //public abstract T getNum();

    public abstract boolean checkCondition(Round round);

    public abstract void onTriggered(Round round);
}
