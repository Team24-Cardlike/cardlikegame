package org.example.Model.OpponentFactories;

public abstract class BossOpponent extends Opponent {
    int turns;
    public BossOpponent(String name, int maxHealth, int damage, int turns){
        this.name = name;
        this.maxHealth = maxHealth;
        this.damage = damage;
        this.turns = turns;
        setHealth(getMaxHealth());
        setImage();
    }
    public int getTurns(){
        return this.turns;
    }
}