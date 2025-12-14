package org.example.Model.OpponentFactories;

public class RegularOpponent implements OpponentInterface{
    private final String name;
    private final int maxHealth;
    public int damage;
    public String image;
    private int health;

    public RegularOpponent(int startHealth, int damage, String name){
        this.maxHealth = startHealth;
        this.damage = damage;
        this.health = maxHealth;
        this.name = name;
        this.image = name+".png";
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public String getTexture() {
        return "assets/images/"+name+".png";
    }
}
