package org.example.Model.Upgrades;

public abstract class Upgrade<T extends Number> {
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

    public abstract T getNum();
}
