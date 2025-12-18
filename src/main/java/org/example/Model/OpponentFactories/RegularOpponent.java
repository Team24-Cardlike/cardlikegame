package org.example.Model.OpponentFactories;

public abstract class RegularOpponent extends Opponent{
    public RegularOpponent(String name, int maxHealth, int damage){
        this.name = name;
        this.maxHealth = maxHealth;
        this.damage = damage;
        setHealth(getMaxHealth());
        setImage();
    }
}