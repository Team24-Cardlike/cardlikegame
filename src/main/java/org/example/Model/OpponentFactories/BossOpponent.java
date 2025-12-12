package org.example.Model.OpponentFactories;

public class BossOpponent implements OpponentInterface {
    private final String name;
    private final int maxHealth;
    public int damage;
    public int turns;
    public String image;
    private int health;

    public BossOpponent(int startHealth, int damage, int turns, String name){
        this.maxHealth = startHealth;
        this.damage = damage;
        this.turns = turns;
        this.health = maxHealth;
        this.name = name;
        this.image = name+".png";
    }

    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public String getTexture() {
        return "";
    }
}
