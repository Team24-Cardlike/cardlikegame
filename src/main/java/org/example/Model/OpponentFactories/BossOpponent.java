package org.example.Model.OpponentFactories;

public class BossOpponent implements OpponentInterface {
    private final String name;
    public int damage;
    public int turns;
    public String image;

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
