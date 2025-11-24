package org.example.Model;

// import java.util.ArrayList;

public class Opponent extends Player {
    public int damage;
    public int turns;

    public Opponent(int startHealth, int damage, int turns, String s){
        this.maxHealth = startHealth;
        this.damage = damage;
        this.turns = turns;
        this.health = maxHealth;
    }

    @Override
    public int getDamage() {
        return this.damage;
    }
}
