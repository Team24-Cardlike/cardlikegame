package org.example.Model.OpponentFactories;

public class RegularFactory extends OpponentFactory{
    private final String name;
    private final int maxHealth;
    public int damage;
    public String image;
    private int health;

    public RegularFactory(int startHealth, int damage, String name){
        this.maxHealth = startHealth;
        this.damage = damage;
        this.health = maxHealth;
        this.name = name;
        this.image = name+".png";
    }

    @Override
    public OpponentInterface Create(String name) {
        return new RegularOpponent(maxHealth, damage, name);
    }
}
