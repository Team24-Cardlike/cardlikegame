package org.example.Model;

// import java.util.ArrayList;

public abstract class Player {
    public int maxHealth;
    private int health;
    abstract int getDamage();

    int getHealth(){
        return health;
    }

    void setHealth(int health) {
        this.health = health;
    }

    void takeDamage(int damage){
        this.health-=damage;
    }

    public float getHealthRatio(){
        return (float) health/maxHealth;
    }

}
